package com.tinjaku.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tinjaku.model.WalletTransaction;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long>{
    
}