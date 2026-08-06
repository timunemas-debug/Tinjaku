package com.tinjaku.mapper;

import org.springframework.stereotype.Component;

import com.tinjaku.dto.request.PaymentRequest;
import com.tinjaku.dto.response.PaymentResponse;
import com.tinjaku.model.Payment;

@Component
public class PaymentMapper {
    
    public Payment toEntity(PaymentRequest request){

        Payment payment = new Payment();
        payment.setTransactionId(request.getTransactionId());
        payment.setAmount(request.getAmount());
        payment.setPaymentMehod(request.getPaymentMehod());
        payment.setPaymentUrl(request.getPaymentUrl());

        return payment;
    }

    public PaymentResponse toResponse(Payment payment){

        return new PaymentResponse(payment.getPaymentId(),
                                   payment.getStatus(),
                                   payment.getAmount(),
                                   payment.getPaymentMehod(),
                                   payment.getTransactionId(),
                                   payment.getPaymentUrl(),
                                   payment.getPaidAt(),
                                   payment.getExpiredAt(),
                                   payment.getCreatedAt(),
                                   payment.getUpdateAt());
    }
}