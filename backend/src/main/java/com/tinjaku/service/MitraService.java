package com.tinjaku.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.tinjaku.dto.request.MitraRequest;
import com.tinjaku.dto.request.OnlineRequest;
import com.tinjaku.dto.request.UpdateLocationMitraRequest;
import com.tinjaku.dto.request.UpdateMitraProfileRequest;
import com.tinjaku.dto.response.DashboardResponse;
import com.tinjaku.dto.response.MitraResponse;
import com.tinjaku.dto.response.OnlineResponse;
import com.tinjaku.dto.response.PesananResponse;
import com.tinjaku.dto.response.UpdateLocationMitraResponse;
import com.tinjaku.dto.response.UpdateMitraProfileResponse;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.mapper.MitraMapper;
import com.tinjaku.mapper.PesananMapper;
import com.tinjaku.mapper.UpdateMitraProfileMapper;
import com.tinjaku.model.Kota;
import com.tinjaku.model.Mitra;
import com.tinjaku.model.StatusPesanan;
import com.tinjaku.repository.MitraRepository;
import com.tinjaku.repository.PesananRepository;
import com.tinjaku.repository.RatingRepository;
import com.tinjaku.repository.projection.RatingSummary;
import com.tinjaku.security.CustomMitraDetails;
import com.tinjaku.security.SecurityService;
import com.tinjaku.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class MitraService {
    private final MitraRepository mitraRepository;
    private final PesananRepository pesananRepository;
    private final RatingRepository ratingRepository;
    private final PesananMapper pesananMapper;
    private final MitraMapper mitraMapper;
    private final UpdateMitraProfileMapper updateMitraProfileMapper;
    private final SecurityService securityService;

    public MitraService(MitraRepository mitraRepository, PesananRepository pesananRepository, RatingRepository ratingRepository ,PesananMapper pesananMapper, MitraMapper mitraMapper, UpdateMitraProfileMapper updateMitraProfileMapper, SecurityService securityService){
        this.mitraRepository = mitraRepository;
        this.pesananRepository = pesananRepository;
        this.ratingRepository = ratingRepository;
        this.pesananMapper = pesananMapper;
        this.mitraMapper = mitraMapper;
        this.updateMitraProfileMapper = updateMitraProfileMapper;
        this.securityService = securityService;
    }

    public MitraResponse tambahMitra(MitraRequest request){

        if(mitraRepository.existsByNamaMitraIgnoreCase(request.getNamaMitra())){
            throw new BadRequestException("Mitra sudah terdaftar!");
        }

        if(mitraRepository.existsByEmailIgnoreCase(request.getEmail())){
            throw new BadRequestException("Email sudah terdaftar!");
        }

        Mitra mitra = mitraMapper.toEntity(request);

        Mitra savedMitra = mitraRepository.save(mitra);
        
        return mitraMapper.toResponse(savedMitra, null, null);
    }

    public List<MitraResponse> getAllMitra(){

        //MENGAMBIL SEMUA MITRA YANG ADA DI DATABASE
        List<Mitra> mitraList = mitraRepository.findAll();

        //MENGAMBIL SEMUA ID MITRA YANG TADI DIAMBIL DARI DATABASE
        List<Long> mitraIds = mitraList.stream()
                .map(Mitra::getMitraId)
                .toList();

        //JIKA MITRA IDS ITU KOSONG AKAN MENGHASILKAN -> {} ITU DARI MAP.OF() KALO TIDAK DIA AKAN MENGAMBIL SEMUA MITRA YANG MEMILIKI RATING.
        Map<Long, RatingSummary> ratingMap = mitraIds.isEmpty() ? Map.of() : ratingRepository.getRatingSummaryByMitraIds(mitraIds)
                        .stream()
                        .collect(Collectors.toMap(RatingSummary::getMitraId, r -> r));

        return mitraList.stream()
                .map(mitra -> {
                    RatingSummary summary = ratingMap.get(mitra.getMitraId());
                    //VALIDATION SUMMARY JIKA SUMMARY TIDAK KOSONG DAN AVGRATING TIDAK KOSONG MAKA AKAN LANJUT KE SUMMARY GETAVGRATING JIKA KOSONG MAKA AKAN KE 0.0
                    double avgRating = summary != null && summary.getAvgRating() != null ? summary.getAvgRating() : 0.0;
                    //VALIDATION SUMMARY JIKA SUMMARY TIDAK KOSONG DAN AVGRATING TIDAK KOSONG MAKA AKAN LANJUT KE SUMMARY GETTOTALRATING JIKA KOSONG MAKA AKAN KE 0.0
                    long totalRating = summary != null && summary.getTotalRating() != null ? summary.getTotalRating() : 0;
                    return mitraMapper.toResponse(mitra, avgRating, totalRating);
                })
                .toList();
    }

    public List<MitraResponse> getMitraByKota(Kota kota){

        List<Mitra> mitraList = mitraRepository.findByAlamatList_Kota(kota);

        List<Long> mitraIds = mitraList.stream()
                .map(Mitra::getMitraId)
                .toList();

        Map<Long, RatingSummary> ratingMap = mitraIds.isEmpty() ? Map.of() : ratingRepository.getRatingSummaryByMitraIds(mitraIds)
                        .stream()
                        .collect(Collectors.toMap(RatingSummary::getMitraId, r -> r));

        return mitraList.stream()
                .map(mitra -> {
                    RatingSummary summary = ratingMap.get(mitra.getMitraId());
                    double avgRating = summary != null && summary.getAvgRating() != null ? summary.getAvgRating() : 0.0;
                    long totalRating = summary != null && summary.getTotalRating() != null ? summary.getTotalRating() : 0;
                    return mitraMapper.toResponse(mitra, avgRating, totalRating);
                })
                .toList();
    }

    public Mitra getMitraById(Long id){

        return mitraRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFound("Mitra tidak ditemukan!"));
    }

    public MitraResponse getMitraResponseById(Long id){
        Mitra mitra = getMitraById(id);

        Double ratingMitra = ratingRepository.getAvargeRating(id);
        Long totalRating = ratingRepository.getTotalRating(id);

        return mitraMapper.toResponse(mitra, ratingMitra != null ? ratingMitra : 0.0, totalRating != null ? totalRating : 0);
    }

    public void deleteMitraById(Long mitraId){

        if(!mitraRepository.existsById(mitraId)){
            throw new ResourceNotFound("Mitra tidak ditemukan!");
        }
        mitraRepository.deleteById(mitraId);
    }

    public List<PesananResponse> getPesananMitra(Long mitraId){
        Mitra mitra = getMitraById(mitraId);
                    
        return mitra.getPesananList()
                .stream()
                .map(pesananMapper::mapToResponse)
                .toList();
    }

    public OnlineResponse updateStatusOnline(Long mitraId, OnlineRequest request){

        Mitra mitra = getMitraById(mitraId);

        CustomMitraDetails currenDetails = securityService.getCurrentMitra();

        if (!currenDetails.getMitra().getMitraId().equals(mitraId)) {
            throw new BadRequestException("Bukan milik mitra tersebut!");
        }

        mitra.setStatusOnOff(request.getStatusOnOff());

        Mitra savedMitra = mitraRepository.save(mitra);

        return mitraMapper.toOnlineResponse(savedMitra);
    }

    public UpdateMitraProfileResponse updateProfile(UpdateMitraProfileRequest request){

        Long mitraId = securityService.getCurrentMitraId();

        Mitra mitra = getMitraById(mitraId);

        if(!mitra.getEmail().equalsIgnoreCase(request.getEmail())
            && mitraRepository.existsByEmailIgnoreCase(request.getEmail())){
                throw new BadRequestException("Email sudah terdaftar!");
            }

        if (!mitra.getNamaMitra().equalsIgnoreCase(request.getNamaMitra())
            && mitraRepository.existsByNamaMitraIgnoreCase(request.getNamaMitra())) {
            throw new BadRequestException("Nama sudah terdaftar");
        }

        mitra.setNamaMitra(request.getNamaMitra());
        mitra.setEmail(request.getEmail());

        mitraRepository.save(mitra);

        return updateMitraProfileMapper.mapToResponse(mitra);
    }

    public DashboardResponse getDashboard(){

        CustomMitraDetails currentDetails = securityService.getCurrentMitra();

        Long mitraId = currentDetails.getMitra().getMitraId();

        if (!currentDetails.getMitra().getMitraId().equals(mitraId)) {
            throw new BadRequestException("Bukan milik mitra");
        }
        
        Long totalPesanan = pesananRepository.countByMitraMitraId(mitraId);
        Long pesananMenunggu = pesananRepository.countByMitraMitraIdAndStatus(mitraId, StatusPesanan.MENUNGGU);
        Long pesananDiTerima = pesananRepository.countByMitraMitraIdAndStatus(mitraId, StatusPesanan.DITERIMA);
        Long pesananDiKerjakan = pesananRepository.countByMitraMitraIdAndStatus(mitraId, StatusPesanan.DIKERJAKAN);
        Long pesananSelesai = pesananRepository.countByMitraMitraIdAndStatus(mitraId, StatusPesanan.SELESAI);

        return new DashboardResponse(totalPesanan,
                                     pesananMenunggu,
                                     pesananDiTerima,
                                     pesananDiKerjakan,
                                     pesananSelesai);
    }

    public UpdateLocationMitraResponse updateLocationMitra(UpdateLocationMitraRequest request){

        Long mitraId = securityService.getCurrentMitraId();

        Mitra mitra = getMitraById(mitraId);

        mitra.setLatitude(request.getLatitude());
        mitra.setLongitude(request.getLongitude());

        mitraRepository.save(mitra);

        return new UpdateLocationMitraResponse("Location berhasil di update!");
    }
}