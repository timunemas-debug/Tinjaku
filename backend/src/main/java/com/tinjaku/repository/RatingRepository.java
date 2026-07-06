package com.tinjaku.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tinjaku.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    boolean existsByPesananPesananId(Long pesananId);

    List<Rating> findByMitraMitraId(Long mitraId);
}