package com.tinjaku.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tinjaku.model.PesananHistory;

public interface PesananHistoryRepository extends JpaRepository<PesananHistory, Long>{
    
    List<PesananHistory> findByPesananId(Long id);
}