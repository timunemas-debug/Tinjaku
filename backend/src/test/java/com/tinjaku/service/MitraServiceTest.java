package com.tinjaku.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tinjaku.dto.request.MitraRequest;
import com.tinjaku.dto.response.MitraResponse;
import com.tinjaku.dto.response.PesananResponse;
import com.tinjaku.exception.BadRequestException;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.mapper.MitraMapper;
import com.tinjaku.mapper.PesananMapper;
import com.tinjaku.model.Kota;
import com.tinjaku.model.Mitra;
import com.tinjaku.model.Pesanan;
import com.tinjaku.repository.MitraRepository;
import com.tinjaku.repository.PesananRepository;
import com.tinjaku.repository.RatingRepository;

@ExtendWith(MockitoExtension.class)
public class MitraServiceTest {
    
    @Mock
    MitraRepository mitraRepository;

    @Mock
    PesananRepository pesananRepository;

    @Mock
    RatingRepository ratingRepository;

    @Mock
    MitraMapper mitraMapper;

    @Mock
    PesananMapper pesananMapper;

    @InjectMocks
    MitraService mitraService;

    Long mitraId = 1L;
    Long mitraId2 = 2L;

    @Test
    public void shouldTambahMitra(){

        Mitra mitra = new Mitra();
        mitra.setMitraId(mitraId);

        MitraRequest request = new MitraRequest();
        request.setNamaMitra("Jeremy");

        MitraResponse response = new MitraResponse();
        response.setNama("Jeremy");

        when(mitraRepository.existsByNamaMitraIgnoreCase("Jeremy"))
                .thenReturn(false);

        when(mitraRepository.save(mitra))
                .thenReturn(mitra);

        when(mitraMapper.toEntity(request))
                .thenReturn(mitra);

        when(mitraMapper.toResponse(mitra, null, null))
                .thenReturn(response);

        MitraResponse result = mitraService.tambahMitra(request);

        assertEquals("Jeremy", result.getNama());

        verify(mitraRepository).existsByNamaMitraIgnoreCase("Jeremy");
        verify(mitraRepository).save(mitra);
        verify(mitraMapper).toEntity(request);
        verify(mitraMapper).toResponse(mitra, null, null);
    }

    @Test
    public void shouldThrowBadRequestWhenMitraAlreadyExists(){

        MitraRequest request = new MitraRequest();
        request.setNamaMitra("Jeremy");

        when(mitraRepository.existsByNamaMitraIgnoreCase("Jeremy"))
                .thenReturn(true);
        
        assertThrows(BadRequestException.class, () -> mitraService.tambahMitra(request));

        verify(mitraRepository).existsByNamaMitraIgnoreCase("Jeremy");
        verify(mitraRepository, never()).save(any());
        verify(mitraMapper, never()).toEntity(any());
        verify(mitraMapper, never()).toResponse(any(), any(), any());
    }

    @Test
    public void shouldGetAllMitra(){

        Mitra mitra1 = new Mitra();
        mitra1.setMitraId(mitraId);
        mitra1.setNamaMitra("Jeremy");

        Mitra mitra2 = new Mitra();
        mitra2.setMitraId(mitraId2);
        mitra2.setNamaMitra("Pretty");

        MitraResponse response1 = new MitraResponse();
        response1.setNama("Jeremy");
        response1.setRatingMitra(4.5);
        response1.setTotalRating(500L);

        MitraResponse response2 = new MitraResponse();
        response2.setNama("Pretty");
        response2.setRatingMitra(2.3);
        response2.setTotalRating(800L);

        when(mitraRepository.findAll())
                .thenReturn(List.of(mitra1, mitra2));
        
        when(ratingRepository.getAvargeRating(mitraId))
                .thenReturn(4.5);

        when(ratingRepository.getAvargeRating(mitraId2))
                .thenReturn(2.3);

        when(ratingRepository.getTotalRating(mitraId))
                .thenReturn(500L);

        when(ratingRepository.getTotalRating(mitraId2))
                .thenReturn(800L);

        when(mitraMapper.toResponse(mitra1, 4.5, 500L))
                .thenReturn(response1);

        when(mitraMapper.toResponse(mitra2, 2.3, 800L))
                .thenReturn(response2);

        List<MitraResponse> result = mitraService.getAllMitra();

        assertEquals(2, result.size());
        assertEquals(4.5, result.get(0).getRatingMitra());
        assertEquals(500L, result.get(0).getTotalRating());
        assertEquals(2.3, result.get(1).getRatingMitra());
        assertEquals(800L, result.get(1).getTotalRating());

        verify(mitraRepository).findAll();
        verify(ratingRepository).getAvargeRating(mitraId);
        verify(ratingRepository).getAvargeRating(mitraId2);
        verify(ratingRepository).getTotalRating(mitraId);
        verify(ratingRepository).getTotalRating(mitraId2);
        verify(mitraMapper).toResponse(mitra1, 4.5, 500L);
        verify(mitraMapper).toResponse(mitra2, 2.3, 800L);
    }

    @Test
    public void shouldGetMitraByKota(){

        Mitra mitra = new Mitra();
        mitra.setMitraId(mitraId);
        mitra.setNamaMitra("Jeremy");

        MitraResponse response = new MitraResponse();
        response.setNama("Jeremy");
        response.setRatingMitra(4.5);
        response.setTotalRating(500L);

        when(mitraRepository.findByAlamatList_Kota(Kota.JAKARTA))
                .thenReturn(List.of(mitra));

        when(ratingRepository.getAvargeRating(mitraId))
                .thenReturn(4.5);

        when(ratingRepository.getTotalRating(mitraId))
                .thenReturn(500L);
        
        when(mitraMapper.toResponse(mitra, 4.5, 500L))
                .thenReturn(response);

        List<MitraResponse> result = mitraService.getMitraByKota(Kota.JAKARTA);

        assertEquals(1, result.size());
        assertEquals("Jeremy", result.get(0).getNama());
        assertEquals(4.5, result.get(0).getRatingMitra());
        assertEquals(500L, result.get(0).getTotalRating());

        verify(mitraRepository).findByAlamatList_Kota(Kota.JAKARTA);
        verify(ratingRepository).getAvargeRating(mitraId);
        verify(ratingRepository).getTotalRating(mitraId);
        verify(mitraMapper).toResponse(mitra, 4.5, 500L);
    }

    @Test
    public void shouldGetMitraById(){

        Mitra mitra = new Mitra();
        mitra.setMitraId(mitraId);

        when(mitraRepository.findById(mitraId))
                .thenReturn(Optional.of(mitra));

        Mitra result = mitraService.getMitraById(mitraId);

        assertEquals(mitraId, result.getMitraId());

        verify(mitraRepository).findById(mitraId);
    }

    @Test
    public void shouldGetMitraResourceNotFound(){

        when(mitraRepository.findById(mitraId))
                .thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFound.class, () -> mitraService.getMitraById(mitraId));

        verify(mitraRepository).findById(mitraId);
    }

    @Test
    public void shouldGetMitraResponseById(){

        Mitra mitra = new Mitra();
        mitra.setMitraId(mitraId);
        mitra.setNamaMitra("Jeremy");

        MitraResponse response = new MitraResponse();
        response.setNama("Jeremy");
        response.setRatingMitra(4.5);
        response.setTotalRating(500L);

        when(mitraRepository.findById(mitraId))
                .thenReturn(Optional.of(mitra));

        when(ratingRepository.getAvargeRating(mitraId))
                .thenReturn(4.5);
        
        when(ratingRepository.getTotalRating(mitraId))
                .thenReturn(500L);
            
        when(mitraMapper.toResponse(mitra, 4.5, 500L))
                .thenReturn(response);

        MitraResponse result = mitraService.getMitraResponseById(mitraId);

        assertEquals("Jeremy", result.getNama());
        assertEquals(4.5, result.getRatingMitra());
        assertEquals(500L, result.getTotalRating());

        verify(mitraRepository).findById(mitraId);
        verify(ratingRepository).getAvargeRating(mitraId);
        verify(ratingRepository).getTotalRating(mitraId);
        verify(mitraMapper).toResponse(mitra, 4.5, 500L);
    }

    @Test
    public void shouldDeleteMitraById(){

        when(mitraRepository.existsById(mitraId))
                .thenReturn(true);

        mitraService.deleteMitraById(mitraId);

        verify(mitraRepository).existsById(mitraId);
        verify(mitraRepository).deleteById(mitraId);
    }

    @Test
    public void shouldGetPesananMitra(){
        Mitra mitra = new Mitra();
        mitra.setMitraId(mitraId);
        
        Pesanan pesanan = new Pesanan();
        pesanan.setKeluhan("Wc Mampet");

        mitra.setPesananList(List.of(pesanan));

        PesananResponse response = new PesananResponse();
        response.setKeluhan("Wc Mampet");
        response.setNamaLengkap("Jeremy Putra Darma");

        when(mitraRepository.findById(mitraId))
                .thenReturn(Optional.of(mitra));

        when(pesananMapper.mapToResponse(pesanan))
                .thenReturn(response);

        List<PesananResponse> result = mitraService.getPesananMitra(mitraId);

        assertEquals(1, result.size());
        assertEquals("Wc Mampet", result.get(0).getKeluhan());
        assertEquals("Jeremy Putra Darma", result.get(0).getNamaLengkap());

        verify(mitraRepository).findById(mitraId);
        verify(pesananMapper).mapToResponse(pesanan);
    }
}
