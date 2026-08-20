package com.tinjaku.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tinjaku.model.Rating;
import com.tinjaku.repository.projection.RatingSummary;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    boolean existsByPesananId(Long id);
    Long countByMitraMitraIdAndRating(Long mitraId, Rating rating);
    List<Rating> findByMitraMitraId(Long mitraId);
    Optional<Rating> findTopByPesananId(Long pesananId);

    @Query("""
            SELECT AVG(r.rating)
            FROM Rating r
            WHERE r.mitra.mitraId = :mitraId
    """)
    Double getAvargeRating(Long mitraId);

    @Query("""
            SELECT COUNT(r)
            FROM Rating r
            WHERE r.mitra.mitraId = :mitraId
            """)
    Long getTotalRating(Long mitraId);

    @Query("""
           SELECT r.mitra.mitraId AS mitraId,
                  AVG(r.rating) AS avgRating,
                  COUNT(r.rating) AS totalRating
           FROM Rating r
           WHERE r.mitra.mitraId IN :mitraIds
           GROUP BY r.mitra.mitraId
           """)
    List<RatingSummary> getRatingSummaryByMitraIds(@Param("mitraIds") List<Long> mitraIds);
}