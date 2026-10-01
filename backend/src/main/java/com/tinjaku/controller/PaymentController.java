package com.tinjaku.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinjaku.dto.request.PaymentRequest;
import com.tinjaku.dto.response.PaymentResponse;
import com.tinjaku.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping("/add-payment")
    public PaymentResponse addPayment(@RequestHeader("Idempotency-Key") String idempotencyKey, @Valid @RequestBody PaymentRequest request){
        return paymentService.addPayment(request, idempotencyKey);
    }
}