package com.tinjaku.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tinjaku.model.IdempotencyRecord;
import com.tinjaku.model.IdempotencyStatus;
import com.tinjaku.repository.IdempotencyRecordRepository;

@Service
public class IdempotencyRecordService {
    
    private final IdempotencyRecordRepository idempotencyRecordRepository;

    public IdempotencyRecordService(IdempotencyRecordRepository idempotencyRecordRepository){
        this.idempotencyRecordRepository = idempotencyRecordRepository;
    }
    
    public IdempotencyRecord createRecord(String key){

        IdempotencyRecord idempotencyRecord = new IdempotencyRecord();
        idempotencyRecord.setKey(key);
        idempotencyRecord.setStatus(IdempotencyStatus.PROCESSING);
        idempotencyRecord.setCreatedAt(LocalDateTime.now());

        idempotencyRecordRepository.save(idempotencyRecord);

        return idempotencyRecord;
    }

    public Optional<IdempotencyRecord> findByKey(String key){
        return idempotencyRecordRepository.findByKey(key);
    }

    public IdempotencyRecord save(IdempotencyRecord idempotencyRecord){
        return idempotencyRecordRepository.save(idempotencyRecord);
    }

}