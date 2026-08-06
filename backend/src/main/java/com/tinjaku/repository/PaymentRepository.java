package com.tinjaku.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tinjaku.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
    
}