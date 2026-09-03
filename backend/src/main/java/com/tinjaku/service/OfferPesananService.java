package com.tinjaku.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tinjaku.exception.BadRequestException;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.model.Mitra;
import com.tinjaku.model.OfferPesanan;
import com.tinjaku.model.Pesanan;
import com.tinjaku.model.PesananHistory;
import com.tinjaku.model.StatusOfferPesanan;
import com.tinjaku.model.StatusOnOff;
import com.tinjaku.model.StatusPesanan;
import com.tinjaku.repository.OfferPesananRepository;
import com.tinjaku.repository.PesananHistoryRepository;
import com.tinjaku.repository.PesananRepository;
import com.tinjaku.security.CustomMitraDetails;
import com.tinjaku.security.SecurityService;

import jakarta.transaction.Transactional;

@Service
public class OfferPesananService {
    
    private final OfferPesananRepository offerPesananRepository;
    private final SecurityService securityService;
    private final MitraMatchingService mitraMatchingService;
    private final NotificationService notificationService;
    private final PesananHistoryRepository pesananHistoryRepository;
    private final PesananRepository pesananRepository;

    public OfferPesananService(OfferPesananRepository offerPesananRepository, SecurityService securityService, MitraMatchingService mitraMatchingService, NotificationService notificationService, PesananHistoryRepository pesananHistoryRepository, PesananRepository pesananRepository){
        this.offerPesananRepository = offerPesananRepository;
        this.securityService = securityService;
        this.mitraMatchingService = mitraMatchingService;
        this.notificationService = notificationService;
        this.pesananHistoryRepository = pesananHistoryRepository;
        this.pesananRepository = pesananRepository;
    }

    private void saveHistory(Pesanan pesanan){

        PesananHistory history = new PesananHistory();
        history.setStatus(pesanan.getStatus());
        history.setWaktuPerubahan(LocalDateTime.now());
        history.setPesanan(pesanan);

        pesananHistoryRepository.save(history);
    }

    public OfferPesanan createOffer(Pesanan pesanan, Mitra mitra){

        if (pesanan.getStatus() != StatusPesanan.MENUNGGU) {
            throw new BadRequestException("Pesanan sudah tidak menunggu!");
        }

        if (mitra.getStatusOnOff() != StatusOnOff.ONLINE) {
            throw new BadRequestException("Mitra sedang offline!");
        }

        OfferPesanan offer = new OfferPesanan();
        
        offer.setPesanan(pesanan);
        offer.setMitra(mitra);
        offer.setStatusOfferPesanan(StatusOfferPesanan.MENUNGGU);
        offer.setOfferedAt(LocalDateTime.now());
        offer.setExpiresAt(offer.getOfferedAt().plusSeconds(10));
        
        OfferPesanan savedOfferPesanan = offerPesananRepository.save(offer);

        notificationService.sendNotificationMitra(mitra.getMitraId(), "Ada pesanan baru yang ditawarkan kepada anda!");
        return savedOfferPesanan;
    }

    @Transactional
    public void acceptOffer(Long offerId){

        OfferPesanan offerPesanan = offerPesananRepository.findByIdWithLock(offerId)
                .orElseThrow(() -> new ResourceNotFound("Offer pesanan tidak ditemukan!"));

        CustomMitraDetails currentDetails = securityService.getCurrentMitra();
        Long mitraId = currentDetails.getMitraId();

        LocalDateTime now = LocalDateTime.now();

        Pesanan pesanan = offerPesanan.getPesanan();

        if (offerPesanan.getMitra().getMitraId() != mitraId) {
            throw new BadRequestException("Offer bukan milik mitra!");
        }

        if (!now.isBefore(offerPesanan.getExpiresAt())) {
            throw new BadRequestException("Offer pesanan sudah lebih dari waktu accept!");
        }

        if (offerPesanan.getStatusOfferPesanan() != StatusOfferPesanan.MENUNGGU) {
            throw new BadRequestException("Offer sudah tidak menunggu");
        }

        if (pesanan.getStatus() != StatusPesanan.MENUNGGU) {
            throw new BadRequestException("Pesanan sudah tidak menunggu");
        }

        offerPesanan.setStatusOfferPesanan(StatusOfferPesanan.DITERIMA);

        pesanan.setMitra(currentDetails.getMitra());
        pesanan.setStatus(StatusPesanan.MENUNGGU_PEMBAYARAN);
        
        offerPesananRepository.save(offerPesanan);

        saveHistory(pesanan);

        notificationService.sendNotification(pesanan.getUser().getUserId(), "Pesanan anda sudah diterima!");
    }

    @Transactional
    public void rejectOffer(Long offerId){

        OfferPesanan offerPesanan = offerPesananRepository.findByIdWithLock(offerId)
                .orElseThrow(() -> new ResourceNotFound("Offer pesanan tidak ditemukan!"));

        CustomMitraDetails currentDetails = securityService.getCurrentMitra();
        Long mitraId = currentDetails.getMitraId();
        
        Pesanan pesanan = offerPesanan.getPesanan();

        if (!offerPesanan.getMitra().getMitraId().equals(mitraId)) {
            throw new BadRequestException("Offer pesanan bukan milik mitra!");
        }

        if (offerPesanan.getStatusOfferPesanan() != StatusOfferPesanan.MENUNGGU) {
            throw new BadRequestException("Offer sudah tidak dapat ditolak!");
        }

        offerPesanan.setStatusOfferPesanan(StatusOfferPesanan.DITOLAK);

        offerPesananRepository.save(offerPesanan);

        sendOfferToNextMitra(pesanan.getId());
    }

    @Transactional
    public void sendOfferToNextMitra(Long pesananId){

        Optional<Pesanan> pesananOptional = pesananRepository.findByIdWithLock(pesananId);

        if (pesananOptional.isEmpty()) {
            throw new ResourceNotFound("Pesanan tidak ditemukan!");
        }

        Pesanan pesanan = pesananOptional.get();

        List<Mitra> eligibleMitra = mitraMatchingService.getEligibleMitra(pesanan);

        List<Long> sudahOfferedMitraIds = offerPesananRepository.findMitraMitraIdByPesananId(pesanan.getId());

        if (sudahOfferedMitraIds.size() >= 4) {
            pesanan.setStatus(StatusPesanan.GAGAL);
            saveHistory(pesanan);
            notificationService.sendNotification(pesanan.getUser().getUserId(), "Pesanan anda gagal!");
            return;
        }
        
        for(Mitra mitra : eligibleMitra){
            
            if (sudahOfferedMitraIds.contains(mitra.getMitraId())) {
                continue;
            }
            
            createOffer(pesanan, mitra);
            return;
        }

        pesanan.setStatus(StatusPesanan.GAGAL);

        saveHistory(pesanan);

        notificationService.sendNotification(pesanan.getUser().getUserId(), "Pesanan anda dibatalkan!, Pesanan gagal mendapatkan mitra.");
    }

    @Transactional
    public Pesanan expireOffer(Long offerId){

        OfferPesanan offerPesanan = offerPesananRepository.findByIdWithLock(offerId)
                .orElseThrow(() -> new ResourceNotFound("Offer pesanan tidak ditemukan!"));

        if (offerPesanan.getStatusOfferPesanan() != StatusOfferPesanan.MENUNGGU) {
            return null;
        }

        LocalDateTime now = LocalDateTime.now();

        if (offerPesanan.getExpiresAt().isAfter(now)) {
            return null;
        }
        
        offerPesanan.setStatusOfferPesanan(StatusOfferPesanan.EXPIRED);
        
        offerPesananRepository.save(offerPesanan);

        return offerPesanan.getPesanan();
    }

    @Scheduled(fixedRate = 1000)
    public void checkExpiredOffers(){

        LocalDateTime sekarang = LocalDateTime.now();

        List<OfferPesanan> expiredOffers = offerPesananRepository.findByStatusOfferPesananAndExpiresAtLessThan(StatusOfferPesanan.MENUNGGU, sekarang);

        for(OfferPesanan offer : expiredOffers){

            Pesanan pesanan = expireOffer(offer.getId());

            if (pesanan != null) {
                sendOfferToNextMitra(pesanan.getId());
            }
        }
    }
}