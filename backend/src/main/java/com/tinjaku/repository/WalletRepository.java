package com.tinjaku.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tinjaku.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long>{
    
}