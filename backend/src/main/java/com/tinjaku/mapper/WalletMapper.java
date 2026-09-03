package com.tinjaku.mapper;

import org.springframework.stereotype.Component;

import com.tinjaku.dto.response.WalletResponse;
import com.tinjaku.model.Wallet;

@Component
public class WalletMapper {
    
    public WalletResponse toMapResponse(Wallet wallet){
        return new WalletResponse(wallet.getWalletId(),
                                  wallet.getBalance(),
                                  wallet.getCreatedAt(),
                                  wallet.getUpdatedAt());
    }
}