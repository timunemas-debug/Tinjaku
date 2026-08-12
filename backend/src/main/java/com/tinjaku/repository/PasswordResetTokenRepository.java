package com.tinjaku.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tinjaku.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>{
    
    Optional<PasswordResetToken> findTopByUserEmailOrderByCreatedAtDesc(String email);
}