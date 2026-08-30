package com.tinjaku.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.tinjaku.model.OfferPesanan;
import com.tinjaku.model.StatusOfferPesanan;

import jakarta.persistence.LockModeType;

public interface OfferPesananRepository extends JpaRepository<OfferPesanan, Long>{
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<OfferPesanan> findByPesananIdAndMitraMitraId(Long pesananId, Long mitraId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<OfferPesanan> findByIdWithLock(Long offerId);

    List<OfferPesanan> findByStatusOfferPesananAndExpiresAtLessThan(StatusOfferPesanan status, LocalDateTime time);

    List<Long> findMitraMitraIdByPesananId(Long pesananId);
}