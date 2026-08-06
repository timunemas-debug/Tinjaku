package com.tinjaku.dto.request;

import java.math.BigDecimal;

import com.tinjaku.model.PaymentMehod;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    
    @NotBlank(message = "TransactionId wajib di isi!")
    private String transactionId;

    @NotNull(message = "Amount wajib di isi!")
    @Min(1)
    @Max(100000000)
    private BigDecimal amount;

    @NotNull(message = "Wajib di isi!")
    private PaymentMehod paymentMehod;
    
    @NotBlank(message = "PaymentUrl wajib di isi!")
    private String paymentUrl;
}