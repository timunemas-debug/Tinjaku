package com.tinjaku.mapper;

import org.springframework.stereotype.Component;

import com.tinjaku.dto.response.IdempotencyRecordResponse;
import com.tinjaku.model.IdempotencyRecord;

@Component
public class IdempotencyRecordMapper {
    
    public IdempotencyRecordResponse toMapResponse(IdempotencyRecord idempotencyRecord){
        return new IdempotencyRecordResponse(idempotencyRecord.getIdempotencyId(),
                                             idempotencyRecord.getKey(),
                                             idempotencyRecord.getPayment(),
                                             idempotencyRecord.getStatus(),
                                             idempotencyRecord.getCreatedAt());
    }
}