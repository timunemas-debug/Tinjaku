package com.tinjaku.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tinjaku.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    
}
