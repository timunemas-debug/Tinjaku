package com.tinjaku.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tinjaku.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long>{
    
    Optional<Wallet> findByMitraMitraId(Long mitraId);

    boolean existsByMitraMitraId(Long mitraId);
}