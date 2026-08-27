package com.tinjaku.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tinjaku.model.OfferPesanan;
import com.tinjaku.model.StatusOfferPesanan;

public interface OfferPesananRepository extends JpaRepository<OfferPesanan, Long>{
    
    Optional <OfferPesanan> findByPesananIdAndMitraMitraId(Long pesananId, Long mitraId);

    List<OfferPesanan> findByStatusOfferPesananAndExpiresAtLessThan(StatusOfferPesanan status, LocalDateTime time);

    List<Long> findMitraMitraIdByPesananId(Long pesananId);
}