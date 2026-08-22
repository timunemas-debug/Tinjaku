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
    Long countByMitraMitraIdAndRating(Long mitraId, Integer rating);
    Optional<Rating> findTopByPesananId(Long pesananId);

    @Query("""
            SELECT r
            FROM Rating r
            JOIN FETCH r.mitra
            WHERE r.mitra.mitraId = :mitraId
    """)
    List<Rating> findByMitraMitraId(@Param("mitraId") Long mitraId);

    @Query("""
            SELECT AVG(r.rating)
            FROM Rating r
            WHERE r.mitra.mitraId = :mitraId
    """)
    Double getAvargeRating(@Param("mitraId") Long mitraId);

    @Query("""
            SELECT COUNT(r)
            FROM Rating r
            WHERE r.mitra.mitraId = :mitraId
            """)
    Long getTotalRating(@Param("mitraId") Long mitraId);

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