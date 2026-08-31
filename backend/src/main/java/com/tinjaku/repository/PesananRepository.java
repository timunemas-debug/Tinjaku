package com.tinjaku.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import com.tinjaku.model.Pesanan;
import com.tinjaku.model.StatusPesanan;

import jakarta.persistence.LockModeType;
public interface PesananRepository extends JpaRepository<Pesanan, Long>{
    List<Pesanan> findPesananByStatus(StatusPesanan status);
    Long countByMitraMitraId(Long mitraId);
    Long countByMitraMitraIdAndStatus(Long mitraId, StatusPesanan status);
    List<Pesanan> findByUserUserIdAndStatus(Long userId, StatusPesanan status);
    List<Pesanan> findByMitraMitraIdAndStatus(Long mitraId, StatusPesanan status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Pesanan p WHERE p.id = :pesananId")
    Optional<Pesanan> findByIdWithLock(@Param("pesananId")Long pesananId);
}