package com.tinjaku.service;

import org.springframework.stereotype.Service;

import com.tinjaku.dto.request.RatingRequest;
import com.tinjaku.dto.response.RatingResponse;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.mapper.RatingMapper;
import com.tinjaku.model.Mitra;
import com.tinjaku.model.Rating;
import com.tinjaku.repository.MitraRepository;
import com.tinjaku.repository.RatingRepository;

@Service
public class RatingService {
    private final RatingMapper ratingMapper;
    private final MitraRepository mitraRepository;
    private final RatingRepository ratingRepository;

    public RatingService(RatingMapper ratingMapper, MitraRepository mitraRepository, RatingRepository ratingRepository){
        this.ratingMapper = ratingMapper;
        this.mitraRepository = mitraRepository;
        this.ratingRepository = ratingRepository;
    }

    public RatingResponse tambahRating(Long mitraId, RatingRequest request){
        Mitra mitra = mitraRepository.findById(mitraId)
                .orElseThrow(() ->
                        new ResourceNotFound("Mitra tidak ditemukan!"));

        Rating rating = ratingMapper.toEntity(request);

        rating.setMitra(mitra);

        Rating savedRating = ratingRepository.save(rating);

        return ratingMapper.toResponse(savedRating);
    }
}