package com.tinjaku.service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tinjaku.exception.BadRequestException;
import com.tinjaku.dto.request.PesananRequest;
import com.tinjaku.dto.response.PesananHistoryResponse;
import com.tinjaku.dto.response.PesananResponse;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.mapper.PesananHistoryMapper;
import com.tinjaku.mapper.PesananMapper;
import com.tinjaku.model.*;
import com.tinjaku.repository.MitraRepository;
import com.tinjaku.repository.PesananHistoryRepository;
import com.tinjaku.repository.PesananRepository;
import com.tinjaku.repository.RatingRepository;
import com.tinjaku.repository.projection.RatingSummary;
import com.tinjaku.security.SecurityService;

import jakarta.transaction.Transactional;

@Service
public class PesananService {
    private final UserService userService;
    private final MitraService mitraService;
    private final PesananRepository pesananRepository;
    private final PesananMapper pesananMapper;
    private final AlamatService alamatService;
    private final RatingService ratingService;
    private final SecurityService securityService;
    private final NotificationService notificationService;
    private final PesananHistoryRepository pesananHistoryRepository;
    private final PesananHistoryMapper pesananHistoryMapper;
    private final MitraRepository mitraRepository;
    private final RatingRepository ratingRepository;
    private final MitraMatchingService mitraMatchingService;
    private final OfferPesananService offerPesananService;
    
    private static final BigDecimal DISKON_PENGGUNA_BARU = BigDecimal.valueOf(25_000);
    
    public PesananService(UserService userService, MitraService mitraService,
                          PesananRepository pesananRepository, PesananMapper pesananMapper,
                          AlamatService alamatService, RatingService ratingService,
                          SecurityService securityService, NotificationService notificationService,
                          PesananHistoryRepository pesananHistoryRepository, PesananHistoryMapper pesananHistoryMapper,
                          MitraRepository mitraRepository, RatingRepository ratingRepository,
                          MitraMatchingService mitraMatchingService, OfferPesananService offerPesananService){

        this.userService = userService;
        this.mitraService = mitraService;
        this.pesananRepository = pesananRepository;
        this.pesananMapper = pesananMapper;
        this.alamatService = alamatService;
        this.ratingService = ratingService;
        this.securityService = securityService;
        this.notificationService = notificationService;
        this.pesananHistoryRepository = pesananHistoryRepository;
        this.pesananHistoryMapper = pesananHistoryMapper;
        this.mitraRepository = mitraRepository;
        this.ratingRepository = ratingRepository;
        this.mitraMatchingService = mitraMatchingService;
        this.offerPesananService = offerPesananService;
    }
    
    public Mitra findMitraTerdekat(User user, List<Mitra> mitras){
    
        Mitra nearestMitra = null;
        double nearestDistance = Double.MAX_VALUE;
    
        for(Mitra mitra : mitras){
    
            double distance = mitraMatchingService.calculateDistance(user.getPickupLat(), user.getPickupLong(), mitra.getLatitude(), mitra.getLongitude());
    
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestMitra = mitra;
            }
        }
            
        return nearestMitra;

        }

        private Mitra getNextMitra(Pesanan pesanan, List<Mitra> eligibMitras){

            if (eligibMitras.isEmpty()) {
                return null;
            }

            return findMitraTerdekat(pesanan.getUser(), eligibMitras);
        }
    
    public List<PesananResponse> getAllPesanan(){
        return pesananRepository.findAll()
                .stream()
                .map(pesananMapper::mapToResponse)
                .toList();
    }

    public Pesanan getPesananEntityById(Long id){
        return pesananRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFound("Pesanan tidak ditemukan!"));
    }

    public PesananResponse getPesananById(Long id){
        return pesananMapper.mapToResponse(getPesananEntityById(id));
    }

    public List<PesananResponse> getPesananByStatus(StatusPesanan status){
        List<Pesanan> pesananList = pesananRepository.findPesananByStatus(status);

        return pesananList.stream()
                .map(pesananMapper::mapToResponse)
                .toList();
    }

    public long hitungTotalPesanan(){
        return pesananRepository.count();
    }

    public BigDecimal hitungTotalHarga(Long userId, Pesanan pesanan){

        BigDecimal biayaAdmin = pesanan.getHargaJasa()
                                .multiply(BigDecimal.valueOf(0.05))
                                .setScale(0, RoundingMode.HALF_UP);

        BigDecimal diskon = hitungDiskon(userId, pesanan);


        BigDecimal total = pesanan.getHargaJasa()
                            .add(biayaAdmin)
                            .subtract(diskon);
        
        pesanan.setBiayaAdmin(biayaAdmin);

        return total;
    }

    public BigDecimal hitungDiskon(Long userId, Pesanan pesanan){
        
        LocalDateTime sekarang = LocalDateTime.now();

        User user = userService.getUserById(userId);

        if(!user.getCreatedAt().plusDays(7).isAfter(sekarang)){
            return BigDecimal.ZERO;
        }

        return DISKON_PENGGUNA_BARU;
    }

    public void hapusPesananService(Long id){
        if(!pesananRepository.existsById(id)){
            throw new ResourceNotFound("Pesanan tidak ditemukan!");
        }
        
        pesananRepository.deleteById(id);
    }

    public boolean userMasihPunyaPesananAktif(User user){

        return user.getPesananList().stream()
               .anyMatch(u -> u.getStatus() != StatusPesanan.SELESAI);
    }

    @Transactional
    public void ajukanHarga(Long pesananId, BigDecimal hargaJasa){

        Long mitraId = securityService.getCurrentMitraId();

        Pesanan pesanan = getPesananEntityById(pesananId);

        if (pesanan.getStatus() != StatusPesanan.DITERIMA) {
            throw new BadRequestException("Pesanan harus di terima dahulu!");
        }

        if (pesanan.getMitra() == null) {
            throw new BadRequestException("Pesanan belum memiliki mitra!");
        }

        if (!pesanan.getMitra().getMitraId().equals(mitraId)) {
            throw new BadRequestException("Pesanan bukan milik mitra!");
        }

        pesanan.setHargaJasa(hargaJasa);

        BigDecimal total = hitungTotalHarga(pesanan.getUser().getUserId(), pesanan);
        pesanan.setTotalHarga(total);

        pesanan.setStatus(StatusPesanan.MENUNGGU_PEMBAYARAN);

        pesananRepository.save(pesanan);
    }

    @Transactional
    public Pesanan createPesanan(PesananRequest request){

        Long userId = securityService.getCurrentUserId();
        User user = userService.getUserById(userId);
        Alamat alamat = alamatService.getAlamatById(request.getAlamatId());
        
        if (user.getPickupLat() == null || user.getPickupLong() == null) {
            throw new BadRequestException("Lokasi user belum tersedia!");
        }

        if(user.getStatusOnOff() != StatusOnOff.ONLINE){
            throw new BadRequestException("User sedang offline!");
        }

        if(!alamat.getUser().getUserId().equals(user.getUserId())){
            throw new BadRequestException("Alamat bukan milik user!");
        }

        if(userMasihPunyaPesananAktif(user)){
            throw new BadRequestException("Masih ada pesanan yang menunggu!");
        }

        Pesanan pesanan = new Pesanan();
        
        pesanan.setNamaPenerima(request.getNamaPenerima());
        pesanan.setAlamatLengkap(alamat.getJalan());
        pesanan.setKelurahan(alamat.getKelurahan());
        pesanan.setKecamatan(alamat.getKecamatan());
        pesanan.setKota(alamat.getKota());
        pesanan.setProvinsi(alamat.getProvinsi());
        pesanan.setKeluhan(request.getKeluhan());
        pesanan.setUser(user);

        pesanan.setMitra(null);
        pesanan.setStatus(StatusPesanan.MENUNGGU);

        saveHistory(pesanan);
        
        Pesanan savedPesanan = pesananRepository.save(pesanan);

        offerPesananService.sendOfferToNextMitra(savedPesanan);

        notificationService.sendNotification(userId, "Pesanan berhasil dibuat!");
        
        return savedPesanan;
    }

    private void saveHistory(Pesanan pesanan){

        PesananHistory history = new PesananHistory();
        history.setStatus(pesanan.getStatus());
        history.setWaktuPerubahan(LocalDateTime.now());
        history.setPesanan(pesanan);

        pesananHistoryRepository.save(history);
    }

    @Transactional
    public Pesanan terimaPesanan(Long pesananId){

        Long mitraId = securityService.getCurrentMitraId();

        Pesanan pesanan = getPesananEntityById(pesananId);
        
        if(pesanan.getStatus() != StatusPesanan.MENUNGGU){
            throw new BadRequestException("Pesanan tidak bisa diterima!");
        }
        
        Mitra mitra = mitraService.getMitraById(mitraId);

        if(mitra.getStatusOnOff() != StatusOnOff.ONLINE){
            throw new BadRequestException("Anda sedang offline!");
        }
        
        Double rating = ratingService.getAverageRating(mitraId);

        if (rating == null) {
            throw new BadRequestException("Mitra tidak memiliki rating!");
        }

        if(rating <= 1.5){
            throw new BadRequestException("Rating Anda dibawah 1.5");
        }

        if (mitra.getLatitude() == null || mitra.getLongitude() == null) {
            throw new BadRequestException("Lokasi mitra belum tersedia!");
        }
        
        User user = pesanan.getUser();

        if (user.getPickupLat() == null || user.getPickupLong() == null) {
            throw new BadRequestException("User belum memiliki titik lokasi!");
        }
        
        double distance = mitraMatchingService.calculateDistance(user.getPickupLat(), user.getPickupLong(), mitra.getLatitude(), mitra.getLongitude());

        if (distance > 10) {
            throw new BadRequestException("Jarak anda terlalu jauh!");
        }

        pesanan.setMitra(mitra);
        pesanan.setStatus(StatusPesanan.DITERIMA);

        Pesanan savedPesanan = pesananRepository.save(pesanan);

        saveHistory(savedPesanan);

        notificationService.sendNotification(savedPesanan.getUser().getUserId(), "Pesanan diterima!, Pesanan kamu telah diterima oleh mitra");

        return savedPesanan;
    }

    @Transactional
    public Pesanan selesaiPesanan(Long pesananId){

        Long mitraId =securityService.getCurrentMitraId();

        Pesanan pesanan = getPesananEntityById(pesananId);

        if (pesanan.getMitra() == null) {
            throw new BadRequestException("Pesanan belum memiliki mitra!");
        }

        if (!pesanan.getMitra().getMitraId().equals(mitraId)) {
            throw new BadRequestException("Pesanan bukan milik mitra!");
        }

        if(pesanan.getStatus() != StatusPesanan.DALAM_PERJALANAN){
            throw new BadRequestException("Pesanan tidak bisa diselesaikan!");
        }

        pesanan.setStatus(StatusPesanan.SELESAI);
        pesanan.setCompletedAt(LocalDateTime.now());

        saveHistory(pesanan);

        return pesananRepository.save(pesanan);
    }

    @Transactional
    public Pesanan tolakPesanan(Long pesananId){

        Long mitraId = securityService.getCurrentMitraId();

        Pesanan pesanan = getPesananEntityById(pesananId);

        if (pesanan.getMitra() == null) {
            throw new BadRequestException("Pesanan belum memiliki mitra!");
        }

        if (!pesanan.getMitra().getMitraId().equals(mitraId)) {
            throw new BadRequestException("Pesanan bukan milik mitra!");
        }

        if(pesanan.getStatus() != StatusPesanan.MENUNGGU){
            throw new BadRequestException("Pesanan tidak dapat ditolak!");
        }

        pesanan.setStatus(StatusPesanan.DITOLAK);
        
        saveHistory(pesanan);

        return pesananRepository.save(pesanan);
    }

    @Transactional
    public Pesanan dalamPerjalanan(Long pesananId){

        Long mitraId = securityService.getCurrentMitraId();

        Pesanan pesanan = getPesananEntityById(pesananId);

        if (pesanan.getMitra() == null) {
            throw new BadRequestException("Pesanan belum memiliki mitra!");
        }

        if (!pesanan.getMitra().getMitraId().equals(mitraId)) {
            throw new BadRequestException("Pesanan bukan milik mitra!");
        }

        if(pesanan.getStatus() != StatusPesanan.DITERIMA){
            throw new BadRequestException("Status pesanan tidak valid!");
        }

        pesanan.setStatus(StatusPesanan.DALAM_PERJALANAN);

        saveHistory(pesanan);

        return pesananRepository.save(pesanan);
    }

    public List<PesananResponse> getRiwayatUser(){

        Long userId = securityService.getCurrentUserId();

        return pesananRepository.findByUserUserIdAndStatus(userId, StatusPesanan.SELESAI)
                .stream()
                .map(pesananMapper::mapToResponse)
                .toList();
    }

    public List<PesananResponse> getRiwayatMitra(){

        Long mitraId = securityService.getCurrentMitraId();

        return pesananRepository.findByMitraMitraIdAndStatus(mitraId, StatusPesanan.SELESAI)
                .stream()
                .map(pesananMapper::mapToResponse)
                .toList();
    }

    public List<PesananHistoryResponse> getHistoryPesanan(Long pesananId){

        Pesanan pesanan = getPesananEntityById(pesananId);

        String role = securityService.getCurrentRole();

        if (role.equals("ROLE_USER")) {
            
            Long userId = securityService.getCurrentUserId();

            if (!pesanan.getUser().getUserId().equals(userId)) {
                throw new BadRequestException("Bukan milik user!");
            }
        } else if (role.equals("ROLE_MITRA")) {
            
            Long mitraId = securityService.getCurrentMitraId();

            if (pesanan.getMitra() == null || !pesanan.getMitra().getMitraId().equals(mitraId)) {
                throw new BadRequestException("Bukan milik mitra!");
            }
        }else if (role.equals("ROLE_ADMIN")) {
            
        }else{

            throw new BadRequestException("Role tidak memiliki akses!");
        }

        List<PesananHistory> historyList = pesananHistoryRepository.findByPesananId(pesananId);

        return historyList.stream()
                .map(pesananHistoryMapper::mapToResponse)
                .toList();
    }

}