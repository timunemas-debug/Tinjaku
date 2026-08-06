package com.tinjaku.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.tinjaku.model.PaymentMehod;
import com.tinjaku.model.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    
    private Long paymentId;
    private PaymentStatus status;
    private BigDecimal amount;
    private PaymentMehod paymentMehod;
    private String transactionId;
    private String paymentUrl;
    private LocalDateTime paidAt;
    private LocalDateTime expiredAt;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}