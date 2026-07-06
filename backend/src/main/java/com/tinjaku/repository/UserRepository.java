package com.tinjaku.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tinjaku.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByNamaDepanIgnoreCaseAndNamaBelakangIgnoreCase(String namaDepan, String namaBelakang);
}