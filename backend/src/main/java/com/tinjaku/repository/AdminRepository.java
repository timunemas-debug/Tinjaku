package com.tinjaku.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tinjaku.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{
    
    Optional<Admin> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
}
