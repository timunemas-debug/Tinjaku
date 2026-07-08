package com.tinjaku.service;

import com.tinjaku.exception.BadRequestException;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tinjaku.dto.request.RatingRequest;
import com.tinjaku.dto.response.RatingResponse;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.mapper.RatingMapper;
import com.tinjaku.model.Rating;
import com.tinjaku.model.StatusPesanan;
import com.tinjaku.model.Pesanan;
import com.tinjaku.repository.MitraRepository;
import com.tinjaku.repository.PesananRepository;
import com.tinjaku.repository.RatingRepository;

@Service
public class RatingService {
    private final RatingMapper ratingMapper;
    private final PesananRepository pesananRepository;
    private final RatingRepository ratingRepository;
    private final MitraRepository mitraRepository;

    public RatingService(RatingMapper ratingMapper, PesananRepository pesananRepository, RatingRepository ratingRepository, MitraRepository mitraRepository){
        this.ratingMapper = ratingMapper;
        this.pesananRepository = pesananRepository;
        this.ratingRepository = ratingRepository;
        this.mitraRepository = mitraRepository;
    }

    public RatingResponse tambahRating(Long pesananId, RatingRequest request){
        Pesanan pesanan = pesananRepository.findById(pesananId)
                .orElseThrow(() ->
                        new ResourceNotFound("Pesanan tidak ditemukan!"));

        if(pesanan.getStatus() != StatusPesanan.SELESAI){
            throw new BadRequestException("Pesanan harus diselesaikan terlebih dahulu sebelum memberikan rating!");
        }

        if(ratingRepository.existsByPesananId(pesananId)){
            throw new BadRequestException("Pesanan sudah diberi rating!");
        }

        Rating rating = ratingMapper.toEntity(request);

        rating.setPesanan(pesanan);
        rating.setMitra(pesanan.getMitra());

        Rating savedRating = ratingRepository.save(rating);

        return ratingMapper.toResponse(savedRating);
    }

    public List<RatingResponse> getRatingMitra(Long mitraId){
        mitraRepository.findById(mitraId)
                .orElseThrow(() ->
                    new ResourceNotFound("Mitra tidak ditemukan!"));

        List<Rating> ratingList = ratingRepository.findByMitraMitraId(mitraId);

        return ratingList.stream()
                .map(ratingMapper::toResponse)
                .toList();
    }

    public Double getAverageRating(Long mitraId){
        mitraRepository.findById(mitraId)
                .orElseThrow(() ->
                    new ResourceNotFound("Mitra tidak ditemukan!"));

        Double avarge = ratingRepository.getAvargeRating(mitraId);

        return avarge != null ? avarge : 0.0;
    }

    public void hapusRating(Long mitraId){
        ratingRepository.deleteById(mitraId);
    }
}