package com.tinjaku.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tinjaku.dto.response.WalletTransactionResponse;
import com.tinjaku.exception.BadRequestException;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.mapper.WalletTransactionMapper;
import com.tinjaku.model.Pesanan;
import com.tinjaku.model.Wallet;
import com.tinjaku.model.WalletReferenceType;
import com.tinjaku.model.WalletTransaction;
import com.tinjaku.model.WalletTransactionType;
import com.tinjaku.repository.WalletRepository;
import com.tinjaku.repository.WalletTransactionRepository;
import com.tinjaku.security.SecurityService;

@Service
public class WalletTransactionService {
    private final WalletTransactionRepository walletTransactionRepository;
    private final WalletTransactionMapper walletTransactionMapper;
    private final WalletRepository walletRepository;
    private final SecurityService securityService;

    public WalletTransactionService(WalletTransactionRepository walletTransactionRepository, WalletTransactionMapper walletTransactionMapper, WalletRepository walletRepository, SecurityService securityService){
        this.walletTransactionRepository = walletTransactionRepository;
        this.walletTransactionMapper = walletTransactionMapper;
        this.walletRepository = walletRepository;
        this.securityService = securityService;
    }

    @Transactional
    public WalletTransactionResponse addCredit(Pesanan pesanan){

        Long mitraId = securityService.getCurrentMitraId();
        
        Wallet wallet = walletRepository.findByMitraMitraId(mitraId)
        .orElseThrow(() -> new ResourceNotFound("Wallet mitra tidak ditemukan!"));
        
        Wallet walletWithLock = walletRepository.findByWalletIdWithLock(wallet.getWalletId())
        .orElseThrow(() -> new ResourceNotFound("Wallet tidak ditemukan!"));
        
        if (walletTransactionRepository.existsByReferenceTypeAndReferenceIdAndType(WalletReferenceType.PESANAN, pesanan.getId(), WalletTransactionType.CREDIT)) {
            throw new BadRequestException("Pesanan sudah pernah dikreditkan ke wallet!");
        }

        if (!walletWithLock.getMitra().getMitraId().equals(mitraId)) {
            throw new BadRequestException("Wallet bukan milik mitra!");
        }

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
        
        try {
            return walletTransactionMapper.toMapResponse(walletTransactionRepository.saveAndFlush(walletTransaction));
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Pesanan sudah pernah dikreditkan ke wallet!");
        }
    }

    @Transactional
    public WalletTransactionResponse addDebit(Long walletId, Long withdrawalId, BigDecimal amountDebit){

        Wallet walletWithLock = walletRepository.findByWalletIdWithLock(walletId)
                .orElseThrow(() -> new ResourceNotFound("Wallet tidak ditemukan!"));

        BigDecimal balanceBefore = walletWithLock.getBalance();
        
        if (amountDebit.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Saldo yang ingin anda tarik tidak valid!");
        }

        if (balanceBefore.compareTo(amountDebit) < 0 ) {
            throw new BadRequestException("Saldo anda tidak mencukupi!");
        }

        BigDecimal balanceAfter = balanceBefore.subtract(amountDebit);

        walletWithLock.setBalance(balanceAfter);

        WalletTransaction walletTransaction = new WalletTransaction();
        walletTransaction.setWallet(walletWithLock);
        walletTransaction.setType(WalletTransactionType.DEBIT);
        walletTransaction.setAmount(amountDebit);
        walletTransaction.setBalanceBefore(balanceBefore);
        walletTransaction.setBalanceAfter(balanceAfter);
        walletTransaction.setReferenceType(WalletReferenceType.WITHDRAWAL);
        walletTransaction.setReferenceId(withdrawalId);
        walletTransaction.setDescription("Berhasil melakukan withdrawal sebesar: " + amountDebit);
        walletTransaction.setCreatedAt(LocalDateTime.now());


        try {
            return walletTransactionMapper.toMapResponse(walletTransactionRepository.saveAndFlush(walletTransaction));
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Transaksi wallet sudah pernah dilakukan!");
        }
    }
}