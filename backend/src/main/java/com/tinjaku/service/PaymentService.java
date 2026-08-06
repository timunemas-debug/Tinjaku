package com.tinjaku.service;

import org.springframework.stereotype.Service;

import com.tinjaku.mapper.PaymentMapper;
import com.tinjaku.repository.PaymentRepository;

@Service
public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper){

        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }
}