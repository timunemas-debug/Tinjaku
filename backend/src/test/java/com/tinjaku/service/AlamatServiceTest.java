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

import com.tinjaku.dto.request.AlamatRequest;
import com.tinjaku.dto.response.AlamatResponse;
import com.tinjaku.exception.BadRequestException;
import com.tinjaku.mapper.AlamatMapper;
import com.tinjaku.model.Alamat;
import com.tinjaku.model.Kota;
import com.tinjaku.model.Label;
import com.tinjaku.model.User;
import com.tinjaku.repository.AlamatRepository;
import com.tinjaku.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class AlamatServiceTest {
    
    @Mock
    AlamatRepository alamatRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    AlamatMapper alamatMapper;

    @InjectMocks
    AlamatService alamatService;

    @Test
    public void shouldTambahAlamat(){

        User user = new User();
        user.setUserId(1L);

        Alamat alamat = new Alamat();
        alamat.setIdAlamat(1L);
        alamat.setJalan("A");
        alamat.setKelurahan("A");
        alamat.setKecamatan("B");
        alamat.setKota(Kota.TANGERANG);
        alamat.setProvinsi("Banten");

        AlamatResponse response = new AlamatResponse();
        response.setLabel(Label.PABRIK);
        response.setJalan("A");
        response.setKelurahan("B");
        response.setKecamatan("C");
        response.setKota(Kota.TANGERANG);
        response.setProvinsi("Banten");

        AlamatRequest request = new AlamatRequest();
        request.setLabel(Label.PABRIK);
        request.setJalan("A");
        request.setKelurahan("B");
        request.setKecamatan("C");
        request.setKota(Kota.TANGERANG);
        request.setProvinsi("Banten");

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(alamatMapper.toEntity(request))
                .thenReturn(alamat);

        when(alamatRepository.save(alamat))
                .thenReturn(alamat);

        when(alamatMapper.toResponse(alamat))
                .thenReturn(response);

        AlamatResponse result = alamatService.tambahAlamat(1L, request);

        assertEquals(Label.PABRIK, result.getLabel());
        assertEquals("A", result.getJalan());
        assertEquals("B", result.getKelurahan());
        assertEquals("C", result.getKecamatan());
        assertEquals(Kota.TANGERANG, result.getKota());
        assertEquals("Banten", result.getProvinsi());

        verify(userRepository).findById(1L);
        verify(alamatMapper).toEntity(request);
        verify(alamatRepository).save(alamat);
        verify(alamatMapper).toResponse(alamat);
    }

    @Test
    public void shouldGetAlamatById(){

        Alamat alamat = new Alamat();
        alamat.setIdAlamat(1L);

        when(alamatRepository.findById(1L))
                .thenReturn(Optional.of(alamat));

        Alamat result = alamatService.getAlamatById(1L);

        assertEquals(1L, result.getIdAlamat());

        verify(alamatRepository).findById(1L);
    }

    @Test
    public void shouldGetAlamatByIdBadRequest(){

        when(alamatRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(BadRequestException.class,() -> alamatService.getAlamatById(1L));

        verify(alamatRepository).findById(1L);
    }

    @Test
    public void shouldGetAlamatResponseById(){

        Alamat alamat = new Alamat();
        alamat.setIdAlamat(1L);

        AlamatResponse response = new AlamatResponse();
        response.setLabel(Label.RUMAH);
        response.setJalan("A");
        response.setKelurahan("B");

        when(alamatRepository.findById(1L))
                .thenReturn(Optional.of(alamat));

        when(alamatMapper.toResponse(alamat))
                .thenReturn(response);

        AlamatResponse result = alamatService.getAlamatResponseById(1L);

        assertEquals(Label.RUMAH, result.getLabel());
        assertEquals("A", result.getJalan());
        assertEquals("B", result.getKelurahan());

        verify(alamatRepository).findById(1L);
        verify(alamatMapper).toResponse(alamat);
    }

    @Test
    public void sholdDeleteAlamat(){

        Alamat alamat = new Alamat();
        alamat.setIdAlamat(1L);

        when(alamatRepository.findById(1L))
                .thenReturn(Optional.of(alamat));

        alamatService.deleteAlamat(1L);

        verify(alamatRepository).findById(1L);
        verify(alamatRepository).delete(alamat);
    }

    @Test
    public void shouldUpdateAlamat(){

        Alamat alamat = new Alamat();
        alamat.setIdAlamat(1L);

        AlamatRequest request = new AlamatRequest();
        request.setJalan("A");
        request.setKelurahan("B");
        request.setKecamatan("C");

        AlamatResponse response = new AlamatResponse();
        response.setJalan("A");
        response.setKelurahan("B");
        response.setKecamatan("C");

        when(alamatRepository.findById(1L))
                .thenReturn(Optional.of(alamat));

        when(alamatMapper.toResponse(alamat))
                .thenReturn(response);
            
        when(alamatRepository.save(alamat))
                .thenReturn(alamat);

        AlamatResponse result = alamatService.updateAlamat(1L, request);

        assertEquals("A", result.getJalan());
        assertEquals("B", result.getKelurahan());
        assertEquals("C", result.getKecamatan());

        verify(alamatRepository).findById(1L);
        verify(alamatRepository).save(alamat);
        verify(alamatMapper).toResponse(alamat);
    }

    @Test
    public void shouldGetAllAlamat(){

        Alamat alamat1 = new Alamat();
        alamat1.setIdAlamat(1L);

        Alamat alamat2 = new Alamat();
        alamat2.setIdAlamat(2L);

        AlamatResponse response1 = new AlamatResponse();
        response1.setJalan("A");
        response1.setKelurahan("B");
        response1.setKecamatan("C");

        AlamatResponse response2 = new AlamatResponse();
        response2.setJalan("D");
        response2.setKelurahan("E");
        response2.setKecamatan("F");

        when(alamatRepository.findAll())
                .thenReturn(List.of(alamat1, alamat2));

        when(alamatMapper.toResponse(alamat1))
                .thenReturn(response1);

        when(alamatMapper.toResponse(alamat2))
                .thenReturn(response2);

        List<AlamatResponse> result = alamatService.getAllAlamat();

        assertEquals(2, result.size());
        assertEquals("A", result.get(0).getJalan());
        assertEquals("B", result.get(0).getKelurahan());
        assertEquals("C", result.get(0).getKecamatan());
        assertEquals("D", result.get(1).getJalan());
        assertEquals("E", result.get(1).getKelurahan());
        assertEquals("F", result.get(1).getKecamatan());

        verify(alamatRepository).findAll();
        verify(alamatMapper).toResponse(alamat1);
        verify(alamatMapper).toResponse(alamat2);
    }
}