package com.tinjaku.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tinjaku.model.IdempotencyRecord;

public interface IdempotencyRecordRepository extends JpaRepository<IdempotencyRecord, Long> {

}