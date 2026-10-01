package com.tinjaku.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tinjaku.model.IdempotencyRecord;

public interface IdempotencyRecordRepository extends JpaRepository<IdempotencyRecord, Long> {

    Optional<IdempotencyRecord> findByKey(String key);
}