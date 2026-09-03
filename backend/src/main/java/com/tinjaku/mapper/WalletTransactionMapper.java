package com.tinjaku.mapper;

import org.springframework.stereotype.Component;

import com.tinjaku.dto.response.WalletTransactionResponse;
import com.tinjaku.model.WalletTransaction;

@Component
public class WalletTransactionMapper {
    
    public WalletTransactionResponse toMapResponse(WalletTransaction walletTransaction){
        return new WalletTransactionResponse(walletTransaction.getWalletTransactionId(),
                                             walletTransaction.getType(),
                                             walletTransaction.getAmount(),
                                             walletTransaction.getBalanceBefore(),
                                             walletTransaction.getBalanceAfter(),
                                             walletTransaction.getReferenceType(),
                                             walletTransaction.getReferenceId(),
                                             walletTransaction.getDescription(),
                                             walletTransaction.getCreatedAt());
    }
}