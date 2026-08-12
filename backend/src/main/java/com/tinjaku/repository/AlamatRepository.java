package com.tinjaku.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tinjaku.model.Alamat;

public interface AlamatRepository extends JpaRepository<Alamat, Long>{
    
    List<Alamat> findByUserUserId(Long userId);
}