package com.tinjaku.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.tinjaku.model.WalletReferenceType;
import com.tinjaku.model.WalletTransactionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransaction {

    private Long walletTransactionId;
    private WalletTransactionType type;
    private BigDecimal amount;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
    private WalletReferenceType referenceType;
    private Long referenceId;
    private String description;
    private LocalDateTime createdAt;
}