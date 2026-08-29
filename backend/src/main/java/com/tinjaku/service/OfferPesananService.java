package com.tinjaku.service;

import java.time.LocalDateTime;
import java.util.List;

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

    public OfferPesananService(OfferPesananRepository offerPesananRepository, SecurityService securityService, MitraMatchingService mitraMatchingService, NotificationService notificationService, PesananHistoryRepository pesananHistoryRepository){
        this.offerPesananRepository = offerPesananRepository;
        this.securityService = securityService;
        this.mitraMatchingService = mitraMatchingService;
        this.notificationService = notificationService;
        this.pesananHistoryRepository = pesananHistoryRepository;
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
    public void acceptOffer(Pesanan pesanan){

        CustomMitraDetails currentDetails = securityService.getCurrentMitra();
        Long mitraId = currentDetails.getMitraId();

        OfferPesanan offerPesanan = offerPesananRepository.findByPesananIdAndMitraMitraId(pesanan.getId(), mitraId)
                .orElseThrow(() -> new ResourceNotFound("Offer pesanan tidak ditemukan!"));

        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(offerPesanan.getExpiresAt())) {
            offerPesanan.setStatusOfferPesanan(StatusOfferPesanan.EXPIRED);
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
    public void rejectOffer(Pesanan pesanan){

        CustomMitraDetails currentDetails = securityService.getCurrentMitra();
        Long mitraId = currentDetails.getMitraId();
        
        OfferPesanan offerPesanan = offerPesananRepository.findByPesananIdAndMitraMitraId(pesanan.getId(), mitraId)
        .orElseThrow(() -> new ResourceNotFound("Offer pesanan tidak ditemukan!"));
        
        if (!offerPesanan.getMitra().getMitraId().equals(mitraId)) {
            throw new BadRequestException("Offer pesanan bukan milik mitra!");
        }

        if (offerPesanan.getStatusOfferPesanan() != StatusOfferPesanan.MENUNGGU) {
            throw new BadRequestException("Offer sudah tidak dapat ditolak!");
        }

        offerPesanan.setStatusOfferPesanan(StatusOfferPesanan.DITOLAK);

        offerPesananRepository.save(offerPesanan);

        sendOfferToNextMitra(pesanan);
    }

    public void sendOfferToNextMitra(Pesanan pesanan){

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
    public void expireOffer(OfferPesanan offerPesanan){

        if (offerPesanan == null) {
            throw new ResourceNotFound("Offer pesanan tidak ditemukan!");
        }

        if (offerPesanan.getStatusOfferPesanan() != StatusOfferPesanan.MENUNGGU) {
            throw new BadRequestException("Offer pesanan tidak dapat di proses!");
        }

        LocalDateTime now = LocalDateTime.now();

        if (offerPesanan.getExpiresAt().isAfter(now)) {
            throw new BadRequestException("Offer pesanan belum expired!");
        }

        offerPesanan.setStatusOfferPesanan(StatusOfferPesanan.EXPIRED);
        
        offerPesananRepository.save(offerPesanan);

        sendOfferToNextMitra(offerPesanan.getPesanan());
    }

    @Scheduled(fixedRate = 1000)
    public void checkExpiredOffers(){

        LocalDateTime sekarang = LocalDateTime.now();

        List<OfferPesanan> expiredOffers = offerPesananRepository.findByStatusOfferPesananAndExpiresAtLessThan(StatusOfferPesanan.MENUNGGU, sekarang);

        for(OfferPesanan offer : expiredOffers){
            expireOffer(offer);
        }
    }
}