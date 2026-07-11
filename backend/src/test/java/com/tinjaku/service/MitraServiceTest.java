package com.tinjaku.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tinjaku.model.Mitra;
import com.tinjaku.dto.request.MitraRequest;
import com.tinjaku.dto.response.MitraResponse;
import com.tinjaku.exception.BadRequestException;
import com.tinjaku.mapper.MitraMapper;
import com.tinjaku.mapper.PesananMapper;
import com.tinjaku.repository.MitraRepository;
import com.tinjaku.repository.PesananRepository;
import com.tinjaku.repository.RatingRepository;

@ExtendWith(MockitoExtension.class)
public class MitraServiceTest {

    @Mock
    private MitraRepository mitraRepository;

    @Mock
    private PesananRepository pesananRepository;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private PesananMapper pesananMapper;

    @Mock
    private MitraMapper mitraMapper;

    @InjectMocks
    private MitraService mitraService;

    @Test
    public void shouldTambahMitra(){
        
        MitraRequest request = new MitraRequest();
        request.setNamaMitra("Jeremy ganteng!");

        Mitra mitra = new Mitra();
        mitra.setNamaMitra("Jeremy ganteng!");

        MitraResponse response = new MitraResponse();
        response.setNama("Jeremy ganteng!");

        when(mitraRepository.existsByNamaMitraIgnoreCase("Jeremy ganteng!"))
                    .thenReturn(false);

        when(mitraMapper.toEntity(request))
                    .thenReturn(mitra);

        when(mitraMapper.toResponse(mitra, null, null))
                    .thenReturn(response);
        
        when(mitraRepository.save(mitra))
                    .thenReturn(mitra);

        MitraResponse hasil = mitraService.tambahMitra(request);
        
        assertEquals("Jeremy ganteng!", hasil.getNama());

        verify(mitraRepository).save(mitra);
        verify(mitraMapper).toEntity(request);
        verify(mitraMapper).toResponse(mitra, null, null);
    }
}
