package com.tinjaku.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tinjaku.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    boolean existsByPesananId(Long id);
    Long countByMitraMitraIdAndRating(Long mitraId, Rating rating);
    List<Rating> findByMitraMitraId(Long mitraId);

    @Query("""
            SELECT AVG(r.rating)
            FROM Rating r
            WHERE r.mitra.mitraId = :mitraId
    """)
    Double getAvargeRating(Long mitraId);

    @Query("""
            SELECT COUNT(r.rating)
            FROM Rating r
            WHERE r.mitra.mitraId = :mitraId
    """)
    Long getTotalRating(Long mitraId);
}