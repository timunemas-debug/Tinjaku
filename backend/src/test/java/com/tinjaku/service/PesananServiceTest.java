package com.tinjaku.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tinjaku.dto.response.PesananResponse;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.mapper.PesananMapper;
import com.tinjaku.model.Pesanan;
import com.tinjaku.model.StatusPesanan;
import com.tinjaku.repository.PesananRepository;

@ExtendWith(MockitoExtension.class)
public class PesananServiceTest {
    
    @Mock
    PesananRepository pesananRepository;

    @Mock
    PesananMapper pesananMapper;

    @Mock
    UserService userService;

    @Mock
    MitraService mitraService;

    @Mock
    AlamatService alamatService;

    @InjectMocks
    PesananService pesananService;

    @Test
    public void shouldGetAllPesanan(){

        Pesanan pesanan = new Pesanan();
        pesanan.setId(1L);
        pesanan.setKeluhan("Wc Mampet");
        pesanan.setKecamatan("Pasar Kemis");

        PesananResponse response = new PesananResponse();
        response.setNamaLengkap("Jeremy Putra Darma");
        response.setKeluhan("Wc Mampet");
        response.setKecamatan("Pasar Kemis");

        when(pesananRepository.findAll())
                .thenReturn(List.of(pesanan));

        when(pesananMapper.mapToResponse(pesanan))
                .thenReturn(response);

        List<PesananResponse> result = pesananService.getAllPesanan();

        assertEquals(1, result.size());
        assertEquals("Jeremy Putra Darma", result.get(0).getNamaLengkap());
        assertEquals("Wc Mampet", result.get(0).getKeluhan());
        assertEquals("Pasar Kemis", result.get(0).getKecamatan());

        verify(pesananRepository).findAll();
        verify(pesananMapper).mapToResponse(pesanan);
    }

    @Test
    public void shouldGetPesananEntityById(){

        Pesanan pesanan = new Pesanan();
        pesanan.setId(1L);

        PesananResponse response = new PesananResponse();

        when(pesananRepository.findById(1L))
                .thenReturn(Optional.of(pesanan));

        when(pesananMapper.mapToResponse(pesanan))
                .thenReturn(response);

        PesananResponse result = pesananService.getPesananById(1L);

        assertEquals(response, result);

        verify(pesananRepository).findById(1L);
        verify(pesananMapper).mapToResponse(pesanan);
    }

    @Test
    public void shouldGetPesananByIdResourceNotFound(){

        Pesanan pesanan = new Pesanan();
        pesanan.setId(1L);
        
        when(pesananRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFound.class, () -> pesananService.getPesananById(1L));

        verify(pesananRepository).findById(1L);
    }

    @Test
    public void shouldGetPesananByStatus(){

        Pesanan pesanan = new Pesanan();
        pesanan.setId(1L);
        pesanan.setKeluhan("Wc Mampet");

        PesananResponse response = new PesananResponse();
        response.setId(1L);
        response.setKeluhan("Wc Mampet");
        response.setStatus(StatusPesanan.DIKERJAKAN);

        when(pesananRepository.findPesananByStatus(StatusPesanan.DIKERJAKAN))
                .thenReturn(List.of(pesanan));

        when(pesananMapper.mapToResponse(pesanan))
                .thenReturn(response);

        List<PesananResponse> result = pesananService.getPesananByStatus(StatusPesanan.DIKERJAKAN);

        assertEquals(1, result.size());
        assertEquals(StatusPesanan.DIKERJAKAN, result.get(0).getStatus());
        assertEquals("Wc Mampet", result.get(0).getKeluhan());

        verify(pesananRepository).findPesananByStatus(StatusPesanan.DIKERJAKAN);
        verify(pesananMapper).mapToResponse(pesanan);
    }

    @Test
    public void shouldGetPesananByStatusResourceNotFound(){

        when(pesananRepository.findPesananByStatus(StatusPesanan.DIKERJAKAN))
                .thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFound.class, () -> pesananService.getPesananByStatus(StatusPesanan.DIKERJAKAN));

        verify(pesananRepository).findPesananByStatus(StatusPesanan.DIKERJAKAN);
    }

    @Test
    public void shouldHitungTotalPesanan(){

        when(pesananRepository.count())
                .thenReturn(2L);

        long result = pesananService.hitungTotalPesanan();

        assertEquals(2L, result);

        verify(pesananRepository).count();
    }
}
