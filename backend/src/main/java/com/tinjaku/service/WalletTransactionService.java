package com.tinjaku.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tinjaku.dto.response.WalletTransactionResponse;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.mapper.WalletTransactionMapper;
import com.tinjaku.model.Pesanan;
import com.tinjaku.model.Wallet;
import com.tinjaku.model.WalletReferenceType;
import com.tinjaku.model.WalletTransaction;
import com.tinjaku.model.WalletTransactionType;
import com.tinjaku.repository.WalletRepository;
import com.tinjaku.repository.WalletTransactionRepository;

@Service
public class WalletTransactionService {
    private final WalletTransactionRepository walletTransactionRepository;
    private final WalletTransactionMapper walletTransactionMapper;
    private final WalletRepository walletRepository;

    public WalletTransactionService(WalletTransactionRepository walletTransactionRepository, WalletTransactionMapper walletTransactionMapper, WalletRepository walletRepository){
        this.walletTransactionRepository = walletTransactionRepository;
        this.walletTransactionMapper = walletTransactionMapper;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public WalletTransactionResponse addCredit(Long mitraId, Pesanan pesanan){
        
        Wallet wallet = walletRepository.findByMitraMitraId(mitraId)
                .orElseThrow(() -> new ResourceNotFound("Wallet mitra tidak ditemukan!"));

        Wallet walletWithLock = walletRepository.findByWalletIdWithLock(wallet.getWalletId())
                .orElseThrow(() -> new ResourceNotFound("Wallet tidak ditemukan!"));

        BigDecimal balanceBefore = walletWithLock.getBalance();
        BigDecimal amountCredit = pesanan.getHargaJasa();
        BigDecimal balanceAfter = balanceBefore.add(amountCredit);
            
        WalletTransaction walletTransaction = new WalletTransaction();
        walletTransaction.setWallet(walletWithLock);
        walletTransaction.setAmount(amountCredit);
        walletTransaction.setType(WalletTransactionType.CREDIT);
        walletTransaction.setBalanceBefore(balanceBefore);
        walletTransaction.setBalanceAfter(balanceAfter);
        walletTransaction.setReferenceType(WalletReferenceType.PESANAN);
        walletWithLock.setBalance(balanceAfter);
        walletTransaction.setReferenceId(pesanan.getId());
        walletTransaction.setDescription("Pendapatan dari pesanan #" + pesanan.getId());
        walletTransaction.setCreatedAt(LocalDateTime.now());
        
        return walletTransactionMapper.toMapResponse(walletTransactionRepository.save(walletTransaction));
    }
}