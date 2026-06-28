package com.tinjaku.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tinjaku.model.Pesanan;
public interface PesananRepository extends JpaRepository<Pesanan, Long>{
    
}
