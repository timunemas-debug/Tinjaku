package com.tinjaku.service;

import org.springframework.stereotype.Service;

import com.tinjaku.mapper.IdempotencyRecordMapper;
import com.tinjaku.repository.IdempotencyRecordRepository;

@Service
public class IdempotencyRecordService {
    
    private final IdempotencyRecordMapper idempotencyRecordMapper;
    private final IdempotencyRecordRepository idempotencyRecordRepository;

    public IdempotencyRecordService(IdempotencyRecordMapper idempotencyRecordMapper, IdempotencyRecordRepository idempotencyRecordRepository){
        this.idempotencyRecordMapper = idempotencyRecordMapper;
        this.idempotencyRecordRepository = idempotencyRecordRepository;
    }
}