package com.tinjaku.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTransaction extends JpaRepository<WalletTransaction, Long>{
    
}