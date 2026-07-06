package com.tinjaku.repository;

import com.tinjaku.model.Mitra;
import com.tinjaku.model.Kota;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MitraRepository extends JpaRepository<Mitra, Long> {
    boolean existsByNamaMitraIgnoreCase(String namaMitra);
    boolean existsById(Long mitraId);

    List<Mitra> findByKota(Kota kota);
}