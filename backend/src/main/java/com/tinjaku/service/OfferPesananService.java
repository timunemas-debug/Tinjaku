package com.tinjaku.service;

import java.time.LocalDateTime;
import java.util.List;

import com.tinjaku.exception.BadRequestException;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.model.Mitra;
import com.tinjaku.model.OfferPesanan;
import com.tinjaku.model.Pesanan;
import com.tinjaku.model.StatusOfferPesanan;
import com.tinjaku.model.StatusPesanan;
import com.tinjaku.repository.OfferPesananRepository;
import com.tinjaku.security.CustomMitraDetails;
import com.tinjaku.security.SecurityService;

public class OfferPesananService {
    
    private final OfferPesananRepository offerPesananRepository;
    private final SecurityService securityService;

    public OfferPesananService(OfferPesananRepository offerPesananRepository, SecurityService securityService){
        this.offerPesananRepository = offerPesananRepository;
        this.securityService = securityService;
    }

    public OfferPesanan createOffer(Pesanan pesanan, Mitra mitra){
        
        OfferPesanan offer = new OfferPesanan();
        
        offer.setPesanan(pesanan);
        offer.setMitra(mitra);
        offer.setStatusOfferPesanan(StatusOfferPesanan.MENUNGGU);
        offer.setOfferedAt(LocalDateTime.now());
        offer.setExpiresAt(LocalDateTime.now().plusSeconds(10));

        return offerPesananRepository.save(offer);
    }

    public void acceptOffer(Pesanan pesanan){

        CustomMitraDetails currentDetails = securityService.getCurrentMitra();
        Long mitraId = currentDetails.getMitraId();

        OfferPesanan offerPesanan = offerPesananRepository.findByPesananIdAndMitraMitraId(pesanan.getId(), mitraId)
                .orElseThrow(() -> new ResourceNotFound("Offer pesanan tidak ditemukan!"));

        if (offerPesanan.getStatusOfferPesanan() != StatusOfferPesanan.MENUNGGU) {
            throw new BadRequestException("Offer sudah tidak menunggu");
        }

        if (!offerPesanan.getMitra().equals(currentDetails.getMitra())) {
            throw new BadRequestException("Offer pesanan bukan milik mitra tersebut!");
        }

        if (pesanan.getStatus() != StatusPesanan.MENUNGGU) {
            throw new BadRequestException("Pesanan sudah tidak menunggu");
        }

        offerPesanan.setStatusOfferPesanan(StatusOfferPesanan.DITERIMA);
        pesanan.setMitra(currentDetails.getMitra());
        pesanan.setStatus(StatusPesanan.DITERIMA);

        OfferPesanan savedOfferPesanan = offerPesananRepository.save(offerPesanan);

        //TINGGAL SEND NOTIFICATION KEPADA USER BAHWA PESANAN SUDAH DITERIMA OLEH MITRA
    }

    public void rejectOffer(Pesanan pesanan){

        CustomMitraDetails currentDetails = securityService.getCurrentMitra();
        Long mitraId = currentDetails.getMitraId();

        if (!currentDetails.getMitra().getMitraId().equals(mitraId)) {
            throw new BadRequestException("Bukan milik mitra!");
        }

        OfferPesanan offerPesanan = createOffer(pesanan, currentDetails.getMitra());

        if (offerPesanan.getStatusOfferPesanan() == StatusOfferPesanan.MENUNGGU) {
            throw new BadRequestException("Offer pesanan masih menunggu");
        }

        if (!offerPesanan.getMitra().equals(currentDetails.getMitra())) {
            throw new BadRequestException("Offer pesanan bukan milik mitra tersebut!");
        }

        offerPesanan.setStatusOfferPesanan(StatusOfferPesanan.DITOLAK);

        offerPesananRepository.save(offerPesanan);

        //NEXT MITRA SELANJUTNYA!
    }


}