package com.tinjaku.service;

import com.tinjaku.exception.BadRequestException;
import org.springframework.stereotype.Service;

import com.tinjaku.dto.request.RatingRequest;
import com.tinjaku.dto.response.RatingResponse;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.mapper.RatingMapper;
import com.tinjaku.model.Mitra;
import com.tinjaku.model.Rating;
import com.tinjaku.model.StatusPesanan;
import com.tinjaku.model.Pesanan;
import com.tinjaku.repository.PesananRepository;
import com.tinjaku.repository.RatingRepository;

@Service
public class RatingService {
    private final RatingMapper ratingMapper;
    private final PesananRepository pesananRepository;
    private final RatingRepository ratingRepository;

    public RatingService(RatingMapper ratingMapper, PesananRepository pesananRepository, RatingRepository ratingRepository){
        this.ratingMapper = ratingMapper;
        this.pesananRepository = pesananRepository;
        this.ratingRepository = ratingRepository;
    }

    public RatingResponse tambahRating(Long pesananId, RatingRequest request){
        Pesanan pesanan = pesananRepository.findById(pesananId)
                .orElseThrow(() ->
                        new ResourceNotFound("Pesanan tidak ditemukan!"));

        if(pesanan.getStatus() != StatusPesanan.SELESAI){
            throw new BadRequestException("Pesanan harus diselesaikan terlebih dahulu sebelum memberikan rating!");
        }

        if(ratingRepository.existsByPesananPesananId(pesananId)){
            throw new BadRequestException("Pesanan sudah diberi rating!");
        }

        Rating rating = ratingMapper.toEntity(request);

        rating.setPesanan(pesanan);
        rating.setMitra(pesanan.getMitra());

        Rating savedRating = ratingRepository.save(rating);

        return ratingMapper.toResponse(savedRating);
    }
}