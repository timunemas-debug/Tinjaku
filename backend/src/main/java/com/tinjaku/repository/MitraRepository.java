package com.tinjaku.repository;

import com.tinjaku.model.Mitra;
import com.tinjaku.model.StatusOnOff;
import com.tinjaku.model.Kota;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MitraRepository extends JpaRepository<Mitra, Long> {
    boolean existsByNamaMitraIgnoreCase(String namaMitra);
    boolean existsById(Long mitraId);
    boolean existsByEmailIgnoreCase(String email);

    List<Mitra> findByAlamatList_Kota(Kota kota);
    List<Mitra> findByAlamatList_KotaAndAlamatList_Kecamatan(Kota kota, String kecamatan);
    List<Mitra> findByStatusOnOff(StatusOnOff statusOnOff);
    Optional<Mitra> findByEmailIgnoreCase(String email);
}