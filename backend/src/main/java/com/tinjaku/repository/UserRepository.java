package com.tinjaku.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tinjaku.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailIgnoreCase(String email);
    Optional<User> findByEmailIgnoreCase(String email);
}