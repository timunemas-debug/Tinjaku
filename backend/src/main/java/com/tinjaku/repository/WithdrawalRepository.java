package com.tinjaku.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.tinjaku.model.StatusWithdrawal;
import com.tinjaku.model.Withdrawal;

import jakarta.persistence.LockModeType;

public interface WithdrawalRepository extends JpaRepository <Withdrawal, Long>{
    
    Optional<Withdrawal> findByWalletWalletId(Long walletId);
    List<Withdrawal> findByWalletMitraMitraId(Long mitraId);

    List<Withdrawal> findByStatus(StatusWithdrawal status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT w FROM Withdrawal w WHERE w.withdrawalId = :withdrawalId")
    Optional<Withdrawal> findByWithdrawalIdWithLock(Long withdrawalId);
}