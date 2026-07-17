package com.tinjaku.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tinjaku.dto.request.RatingRequest;
import com.tinjaku.dto.response.RatingResponse;
import com.tinjaku.mapper.RatingMapper;
import com.tinjaku.model.Mitra;
import com.tinjaku.model.Pesanan;
import com.tinjaku.model.Rating;
import com.tinjaku.model.StatusPesanan;
import com.tinjaku.repository.MitraRepository;
import com.tinjaku.repository.PesananRepository;
import com.tinjaku.repository.RatingRepository;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {
    
    @Mock
    RatingRepository ratingRepository;

    @Mock
    RatingMapper ratingMapper;

    @Mock
    PesananRepository pesananRepository;

    @Mock
    MitraRepository mitraRepository;

    @InjectMocks
    RatingService ratingService;

    @Test
    public void shouldTambahRating(){

        Pesanan pesanan = new Pesanan();
        pesanan.setId(1L);
        pesanan.setStatus(StatusPesanan.SELESAI);

        Rating rating = new Rating();
        rating.setId(1L);
        rating.setRating(4);

        RatingRequest request = new RatingRequest();
        request.setRating(4);

        RatingResponse response = new RatingResponse();
        response.setNamaMitra("WC MAKMUR");
        response.setRating(4);

        when(pesananRepository.findById(1L))
                .thenReturn(Optional.of(pesanan));

        when(ratingRepository.existsByPesananId(1L))
                .thenReturn(false);

        when(ratingRepository.save(rating))
                .thenReturn(rating);

        when(ratingMapper.toEntity(request))
                .thenReturn(rating);

        when(ratingMapper.toResponse(rating))
                .thenReturn(response);

        RatingResponse result = ratingService.tambahRating(1L, request);

        assertEquals("WC MAKMUR", result.getNamaMitra());
        assertEquals(4, result.getRating());

        verify(pesananRepository).findById(1L);
        verify(ratingRepository).existsByPesananId(1L);
        verify(ratingRepository).save(rating);
        verify(ratingMapper).toEntity(request);
        verify(ratingMapper).toResponse(rating);
    }

    @Test
    public void shouldGetRatingMitra(){

        Mitra mitra = new Mitra();
        mitra.setMitraId(1L);

        Rating rating = new Rating();
        rating.setId(1L);
        rating.setRating(4);

        RatingResponse response = new RatingResponse();
        response.setRating(4);
        response.setNamaMitra("WC MAKMUR");

        when(mitraRepository.findById(1L))
                .thenReturn(Optional.of(mitra));

        when(ratingRepository.findByMitraMitraId(1L))
                .thenReturn(List.of(rating));

        when(ratingMapper.toResponse(rating))
                .thenReturn(response);

        List<RatingResponse> result = ratingService.getRatingMitra(1L);

        assertEquals("WC MAKMUR", result.get(0).getNamaMitra());
        assertEquals(4, result.get(0).getRating());

        verify(mitraRepository).findById(1L);
        verify(ratingRepository).findByMitraMitraId(1L);
        verify(ratingMapper).toResponse(rating);
    }

    @Test
    public void shouldGetAverageRating(){

        Mitra mitra = new Mitra();
        mitra.setMitraId(1L);

        when(mitraRepository.findById(1L))
                .thenReturn(Optional.of(mitra));

        when(ratingRepository.getAvargeRating(1L))
                .thenReturn(3.4);

        Double average = ratingService.getAverageRating(1L);

        assertEquals(3.4, average);

        verify(mitraRepository).findById(1L);
        verify(ratingRepository).getAvargeRating(1L);
    }

    @Test
    public void shouldHapusRating(){

        ratingService.hapusRating(1L);

        verify(ratingRepository).deleteById(1L);
    }
}
