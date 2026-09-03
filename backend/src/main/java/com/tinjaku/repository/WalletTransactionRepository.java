package com.tinjaku.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tinjaku.model.WalletReferenceType;
import com.tinjaku.model.WalletTransaction;
import com.tinjaku.model.WalletTransactionType;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long>{
    
    boolean existsByReferenceTypeAndReferenceIdAndType(WalletReferenceType referenceType, Long referenceId, WalletTransactionType transactionType);
}