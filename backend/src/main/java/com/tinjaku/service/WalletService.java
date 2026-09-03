package com.tinjaku.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tinjaku.dto.response.WalletResponse;
import com.tinjaku.exception.BadRequestException;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.mapper.WalletMapper;
import com.tinjaku.model.Mitra;
import com.tinjaku.model.Wallet;
import com.tinjaku.repository.WalletRepository;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;

    public WalletService(WalletRepository walletRepository, WalletMapper walletMapper){
        this.walletRepository = walletRepository;
        this.walletMapper = walletMapper;
    }

    @Transactional
    public WalletResponse createWallet(Mitra mitra){

        if (mitra == null || mitra.getMitraId() == null) {
            throw new BadRequestException("Mitra tidak valid!");
        }
        
        if (walletRepository.existsByMitraMitraId(mitra.getMitraId())) {
            throw new BadRequestException("Mitra sudah memiliki wallet!");
        }

        Wallet wallet = new Wallet();
        wallet.setMitra(mitra);
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setCreatedAt(LocalDateTime.now());
        wallet.setUpdatedAt(LocalDateTime.now());

        try {
            return walletMapper.toMapResponse(walletRepository.save(wallet));
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Mitra sudah memiliki wallet!");
        }
    }

    public Wallet getWalletByMitraId(Long mitraId){
        return walletRepository.findByMitraMitraId(mitraId)
            .orElseThrow(() -> new ResourceNotFound("Wallet mitra tidak ditemukan!"));
    }
}