package com.tinjaku.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

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

    public WalletResponse createdWallet(Mitra mitra){
        
        Wallet mitraWallet = getWalletByMitraId(mitra.getMitraId());

        if (mitraWallet != null) {
            throw new BadRequestException("Mitra sudah memiliki wallet!");
        }

        Wallet wallet = new Wallet();
        wallet.setMitra(mitra);
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setCreatedAt(LocalDateTime.now());
        wallet.setUpdatedAt(LocalDateTime.now());

        return walletMapper.toMapResponse(walletRepository.save(wallet));
    }

    public Wallet getWalletByMitraId(Long mitraId){
        return walletRepository.findByMitraMitraId(mitraId)
            .orElseThrow(() -> new ResourceNotFound("Wallet mitra tidak ditemukan!"));
    }
}