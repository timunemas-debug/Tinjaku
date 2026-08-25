package com.tinjaku.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tinjaku.model.OfferPesanan;

public interface OfferPesananRepository extends JpaRepository<OfferPesanan, Long>{
    
    Optional <OfferPesanan> findByPesananIdAndMitraMitraId(Long pesananId, Long mitraId);
}