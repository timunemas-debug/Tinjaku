package com.tinjaku.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class IdempotencyRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idempotencyId;

    @Column(name = "key", unique = true, nullable = false)
    private String key;

    @Enumerated(EnumType.STRING)
    private IdempotencyStatus status;

    @OneToOne
    @JoinColumn(name = "payment_id", unique = true)
    private Payment payment;

    private LocalDateTime createdAt;

    public IdempotencyRecord(){
    }
}