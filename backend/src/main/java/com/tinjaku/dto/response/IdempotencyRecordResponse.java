package com.tinjaku.dto.response;

import java.time.LocalDateTime;

import com.tinjaku.model.IdempotencyStatus;
import com.tinjaku.model.Payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdempotencyRecordResponse {
    
    private Long idempotencyId;
    private String key;
    private Payment payment;
    private IdempotencyStatus status;
    private LocalDateTime createdAt;
}