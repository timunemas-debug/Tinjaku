package com.tinjaku.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tinjaku.dto.request.AlamatMitraRequest;
import com.tinjaku.dto.response.AlamatMitraResponse;
import com.tinjaku.exception.BadRequestException;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.mapper.AlamatMitraMapper;
import com.tinjaku.model.AlamatMitra;
import com.tinjaku.model.Mitra;
import com.tinjaku.repository.AlamatMitraRepository;
import com.tinjaku.repository.MitraRepository;
import com.tinjaku.security.SecurityService;

@ExtendWith(MockitoExtension.class)
public class AlamatMitraServiceTest {
    
    @Mock
    AlamatMitraRepository alamatMitraRepository;

    @Mock
    AlamatMitraMapper alamatMitraMapper;

    @Mock
    MitraRepository mitraRepository;

    @Mock
    SecurityService securityService;

    @InjectMocks
    AlamatMitraService alamatMitraService;

    String jalan = "A";
    String kelurahan = "B";
    String kecamatan = "C";

    @Test
    public void shouldTambahAlamat(){

        Mitra mitra = new Mitra();
        mitra.setMitraId(1L);

        AlamatMitra alamat = new AlamatMitra();
        alamat.setIdAlamat(1L);
        alamat.setJalan(jalan);
        alamat.setKelurahan(kelurahan);
        alamat.setKecamatan(kecamatan);

        AlamatMitraRequest request = new AlamatMitraRequest();
        request.setJalan(jalan);
        request.setKelurahan(kelurahan);
        request.setKecamatan(kecamatan);

        AlamatMitraResponse response = new AlamatMitraResponse();
        response.setJalan(jalan);
        response.setKelurahan(kelurahan);
        response.setKecamatan(kecamatan);

        when(securityService.getCurrentMitraId())
                .thenReturn(1L);

        when(mitraRepository.findById(1L))
                .thenReturn(Optional.of(mitra));

        when(alamatMitraMapper.toEntity(request))
                .thenReturn(alamat);

        when(alamatMitraRepository.save(alamat))
                .thenReturn(alamat);

        when(alamatMitraMapper.toResponse(alamat))
                .thenReturn(response);

        AlamatMitraResponse result = alamatMitraService.tambahAlamat(request);

        assertEquals(jalan, result.getJalan());
        assertEquals(kelurahan, result.getKelurahan());
        assertEquals(kecamatan, result.getKecamatan());

        verify(mitraRepository).findById(1L);
        verify(alamatMitraMapper).toEntity(request);
        verify(alamatMitraRepository).save(alamat);
        verify(alamatMitraMapper).toResponse(alamat);
    }

    @Test
    public void shouldGetAlamatMitraById(){

        AlamatMitra alamat = new AlamatMitra();
        alamat.setIdAlamat(1L);

        when(alamatMitraRepository.findById(1L))
                .thenReturn(Optional.of(alamat));

        alamatMitraRepository.findById(1L);

        verify(alamatMitraRepository).findById(1L);
    }

    @Test
    public void shouldGetAlamatMitraByIdResourceNotFound(){

        when(alamatMitraRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFound.class, () -> alamatMitraService.getAlamatMitraById(1L));

        verify(alamatMitraRepository).findById(1L);
    }

    @Test
    public void shouldGetAlamatResponseById(){

        Mitra mitra = new Mitra();
        mitra.setMitraId(1L);

        AlamatMitra alamat = new AlamatMitra();
        alamat.setIdAlamat(1L);
        alamat.setMitra(mitra);

        AlamatMitraResponse response = new AlamatMitraResponse();
        response.setJalan(jalan);
        response.setKelurahan(kelurahan);
        response.setKecamatan(kecamatan);

        when(securityService.getCurrentUserId())
                .thenReturn(1L);

        when(alamatMitraRepository.findById(1L))
                .thenReturn(Optional.of(alamat));
        
        when(alamatMitraMapper.toResponse(alamat))
                .thenReturn(response);

        AlamatMitraResponse result = alamatMitraService.getAlamatResponseById(1L);

        assertEquals(jalan, result.getJalan());
        assertEquals(kelurahan, result.getKelurahan());
        assertEquals(kecamatan, result.getKecamatan());

        verify(securityService).getCurrentUserId();
        verify(alamatMitraRepository).findById(1L);
        verify(alamatMitraMapper).toResponse(alamat);
    }

    @Test
    public void shouldGetAllAlamat(){

        AlamatMitra alamat1 = new AlamatMitra();
        alamat1.setIdAlamat(1L);
        alamat1.setJalan(jalan);
        alamat1.setKelurahan(kelurahan);
        alamat1.setKecamatan(kecamatan);

        AlamatMitra alamat2 = new AlamatMitra();
        alamat2.setIdAlamat(2L);
        alamat2.setJalan("D");
        alamat2.setKelurahan("E");
        alamat2.setKecamatan("F");

        AlamatMitraResponse response1 = new AlamatMitraResponse();
        response1.setJalan(jalan);
        response1.setKelurahan(kelurahan);
        response1.setKecamatan(kecamatan);

        AlamatMitraResponse response2 = new AlamatMitraResponse();
        response2.setJalan("D");
        response2.setKelurahan("E");
        response2.setKecamatan("F");

        when(alamatMitraRepository.findAll())
                .thenReturn(List.of(alamat1, alamat2));

        when(alamatMitraMapper.toResponse(alamat1))
                .thenReturn(response1);

        when(alamatMitraMapper.toResponse(alamat2))
                .thenReturn(response2);

        List<AlamatMitraResponse> result = alamatMitraService.getAllAlamat();

        assertEquals(jalan, result.get(0).getJalan());
        assertEquals(kelurahan, result.get(0).getKelurahan());
        assertEquals(kecamatan, result.get(0).getKecamatan());
        assertEquals("D", result.get(1).getJalan());
        assertEquals("E", result.get(1).getKelurahan());
        assertEquals("F", result.get(1).getKecamatan());

        verify(alamatMitraRepository).findAll();
        verify(alamatMitraMapper).toResponse(alamat1);
        verify(alamatMitraMapper).toResponse(alamat2);
    }

    @Test
    public void shouldDeleteAlamat(){
        
        AlamatMitra alamat = new AlamatMitra();
        alamat.setIdAlamat(1L);
        alamat.setJalan(jalan);
        alamat.setKelurahan(kelurahan);
        alamat.setKecamatan(kecamatan);

        when(alamatMitraRepository.findById(1L))
                .thenReturn(Optional.of(alamat));
        
        alamatMitraService.deleteAlamat(1L);

        verify(alamatMitraRepository).findById(1L);
        verify(alamatMitraRepository).delete(alamat);
    }

    @Test
    public void shouldUpdateAlamatMitra(){

        Mitra mitra = new Mitra();
        mitra.setMitraId(1L);

        AlamatMitra alamatMitra = new AlamatMitra();
        alamatMitra.setIdAlamat(1L);
        alamatMitra.setJalan(jalan);
        alamatMitra.setKelurahan(kelurahan);
        alamatMitra.setKecamatan(kecamatan);
        alamatMitra.setMitra(mitra);

        AlamatMitraRequest request = new AlamatMitraRequest();
        request.setJalan(jalan);
        request.setKelurahan(kelurahan);
        request.setKecamatan(kecamatan);

        AlamatMitraResponse response = new AlamatMitraResponse();
        response.setJalan(jalan);
        response.setKelurahan(kelurahan);
        response.setKecamatan(kecamatan);

        when(securityService.getCurrentUserId())
                .thenReturn(1L);

        when(alamatMitraRepository.findById(1L))
                .thenReturn(Optional.of(alamatMitra));

        when(alamatMitraMapper.toResponse(alamatMitra))
                .thenReturn(response);

        when(alamatMitraRepository.save(alamatMitra))
                .thenReturn(alamatMitra);

        AlamatMitraResponse result = alamatMitraService.updateAlamatMitra(1L, request);

        assertEquals(jalan, result.getJalan());
        assertEquals(kelurahan, result.getKelurahan());
        assertEquals(kecamatan, result.getKecamatan());

        verify(securityService).getCurrentUserId();
        verify(alamatMitraRepository).findById(1L);
        verify(alamatMitraRepository).save(alamatMitra);
        verify(alamatMitraMapper).toResponse(alamatMitra);
    }
}