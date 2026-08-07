package com.tinjaku.dto.request;

import com.tinjaku.model.PaymentMehod;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {

    @NotNull(message = "Pesanan wajib di isi!")
    private Long pesananId;

    @NotNull(message = "Wajib di isi!")
    private PaymentMehod paymentMehod;
}