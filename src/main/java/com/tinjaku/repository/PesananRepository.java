package com.tinjaku.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.tinjaku.model.Pesanan;
import com.tinjaku.model.StatusPesanan;
public interface PesananRepository extends JpaRepository<Pesanan, Long>{
    List<Pesanan> findPesananByStatus(StatusPesanan status);
    Long countByMitraMitraId(Long mitraId);
    Long countByMitraMitraIdAndStatus(Long mitraId, StatusPesanan status);
}