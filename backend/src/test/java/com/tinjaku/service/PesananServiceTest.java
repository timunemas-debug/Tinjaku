package com.tinjaku.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tinjaku.dto.request.PesananRequest;
import com.tinjaku.dto.response.PesananResponse;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.mapper.PesananMapper;
import com.tinjaku.model.Alamat;
import com.tinjaku.model.AlamatMitra;
import com.tinjaku.model.Kota;
import com.tinjaku.model.Label;
import com.tinjaku.model.Mitra;
import com.tinjaku.model.Pesanan;
import com.tinjaku.model.PesananHistory;
import com.tinjaku.model.StatusOnOff;
import com.tinjaku.model.StatusPesanan;
import com.tinjaku.model.UkuranSepticTank;
import com.tinjaku.model.User;
import com.tinjaku.repository.PesananHistoryRepository;
import com.tinjaku.repository.PesananRepository;
import com.tinjaku.security.SecurityService;

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

    @Mock
    RatingService ratingService;

    @Mock
    SecurityService securityService;

    @Mock
    NotificationService notificationService;

    @Mock
    PesananHistoryRepository pesananHistoryRepository;

    @Mock
    OfferPesananService offerPesananService;

    @Mock
    MitraMatchingService mitraMatchingService;

    @Spy
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

        pesananService.getPesananByStatus(StatusPesanan.DIKERJAKAN);

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

    @Test
    public void shouldHapusPesananService(){

        when(pesananRepository.existsById(1L))
                .thenReturn(true);

        pesananService.hapusPesananService(1L);

        verify(pesananRepository).existsById(1L);
        verify(pesananRepository).deleteById(1L);
    }

    @Test
    public void shouldHapusPesananExistsById(){

        when(pesananRepository.existsById(1L))
                .thenReturn(false);

        assertThrows(ResourceNotFound.class, () -> pesananService.hapusPesananService(1L));

        verify(pesananRepository).existsById(1L);
        verify(pesananRepository, never()).deleteById(any());
    }

    @Test
    public void shouldUserMasihPunyaPesananAktif(){

        User user = new User();
        user.setUserId(1L);

        Pesanan pesanan = new Pesanan();
        pesanan.setStatus(StatusPesanan.MENUNGGU);

        user.setPesananList(List.of(pesanan));

        boolean result = pesananService.userMasihPunyaPesananAktif(user);

        assertTrue(result);
    }

    @Test
    public void shouldCreatePesanan(){

        User user = new User();
        user.setUserId(1L);
        user.setNamaDepan("Jeremy");
        user.setStatusOnOff(StatusOnOff.ONLINE);
        user.setPesananList(new ArrayList<>());
        user.setPickupLat(2.0214);
        user.setPickupLong(3.4123);
        user.setCreatedAt(LocalDateTime.now());

        Alamat alamat = new Alamat();
        alamat.setUser(user);
        alamat.setJalan("jalan : A");
        alamat.setKelurahan("Kelurahan : A");
        alamat.setKecamatan("Kecamatan : B");
        alamat.setKota(Kota.TANGERANG);
        alamat.setProvinsi("Banten");

        PesananRequest request = new PesananRequest();
        request.setAlamatId(1L);
        request.setNamaPenerima("Jamsuy");
        request.setKeluhan("WC mampet");
        request.setLabel(Label.RUMAH);
        request.setUkuranSepticTank(UkuranSepticTank.KECIL);

        when(securityService.getCurrentUserId())
                .thenReturn(1L);

        when(userService.getUserById(1L))
                .thenReturn(user);

        when(alamatService.getAlamatById(1L))
                .thenReturn(alamat);

        when(pesananRepository.save(any(Pesanan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Pesanan result = pesananService.createPesanan(request);

        assertNotNull(result);

        assertEquals("Jamsuy", result.getNamaPenerima());
        assertEquals(StatusPesanan.MENUNGGU, result.getStatus());
        assertEquals(user, result.getUser());
        assertEquals(BigDecimal.valueOf(400000), result.getHargaJasa());
        assertEquals(StatusPesanan.MENUNGGU, result.getStatus());

        verify(pesananRepository).save(any(Pesanan.class));
        verify(offerPesananService).sendOfferToNextMitra(result.getId());
        verify(notificationService).sendNotification(1L, "Pesanan berhasil dibuat dan sedang mencari mitra.");
    }

    @Test
    public void shouldSelesaiPesanan(){

        Mitra mitra = new Mitra();
        mitra.setMitraId(2L);

        Pesanan pesanan = new Pesanan();
        pesanan.setId(1L);
        pesanan.setStatus(StatusPesanan.DALAM_PERJALANAN);
        pesanan.setMitra(mitra);

        when(securityService.getCurrentMitraId())
                .thenReturn(2L);

        when(pesananRepository.findById(1L))
                .thenReturn(Optional.of(pesanan));
        
        when(pesananRepository.save(any(Pesanan.class)))
                .thenReturn(pesanan);

        Pesanan result = pesananService.selesaiPesanan(1L);

        assertEquals(StatusPesanan.SELESAI, result.getStatus());

        verify(pesananHistoryRepository).save(any(PesananHistory.class));
        verify(pesananRepository).save(any(Pesanan.class));
    }

    @Test
    public void shouldDalamPerjalanan(){

        Mitra mitra = new Mitra();
        mitra.setMitraId(2L);

        Pesanan pesanan = new Pesanan();
        pesanan.setId(1L);
        pesanan.setStatus(StatusPesanan.DITERIMA);
        pesanan.setMitra(mitra);

        when(securityService.getCurrentMitraId())
                .thenReturn(2L);

        when(pesananRepository.findById(1L))
                .thenReturn(Optional.of(pesanan));

        when(pesananRepository.save(any(Pesanan.class)))
                .thenReturn(pesanan);

        Pesanan result = pesananService.dalamPerjalanan(1L);

        assertEquals(StatusPesanan.DALAM_PERJALANAN, result.getStatus());

        verify(pesananHistoryRepository).save(any(PesananHistory.class));
        verify(pesananRepository).save(any(Pesanan.class));
    }

    @Test
    public void shouldHitungTotalHarga(){

        User user = new User();
        user.setCreatedAt(LocalDateTime.now());

        Pesanan pesanan = new Pesanan();
        pesanan.setHargaJasa(BigDecimal.valueOf(100000));

        when(userService.getUserById(1L))
                .thenReturn(user);

        BigDecimal total = pesananService.hitungTotalHarga(1L, pesanan);

        assertEquals(0, BigDecimal.valueOf(80000).compareTo(total));

        assertEquals(0, BigDecimal.valueOf(5000).compareTo(pesanan.getBiayaAdmin()));
    }

    @Test
    public void shouldHitungDiskon(){

        User user = new User();
        user.setCreatedAt(LocalDateTime.now().minusDays(3));

        Pesanan pesanan = new Pesanan();

        when(userService.getUserById(1L))
                .thenReturn(user);

        BigDecimal diskon = pesananService.hitungDiskon(1L, pesanan);

        assertEquals(BigDecimal.valueOf(25000), diskon);

        verify(userService).getUserById(1L);
    }

    @Test
    public void shouldAjukanHarga(){

        User user = new User();
        user.setUserId(1L);
        user.setCreatedAt(LocalDateTime.now().minusDays(3));

        Mitra mitra = new Mitra();
        mitra.setMitraId(2L);

        Pesanan pesanan = new Pesanan();
        pesanan.setUser(user);
        pesanan.setStatus(StatusPesanan.DITERIMA);
        pesanan.setMitra(mitra);

        when(securityService.getCurrentMitraId())
                .thenReturn(2L);

        when(pesananRepository.findById(1L))
                .thenReturn(Optional.of(pesanan));

        when(userService.getUserById(1L))
                .thenReturn(user);

        pesananService.ajukanHarga(1L, BigDecimal.valueOf(100000));

        assertEquals(BigDecimal.valueOf(100000), pesanan.getHargaJasa());
        assertEquals(StatusPesanan.MENUNGGU_PEMBAYARAN, pesanan.getStatus());
        assertNotNull(pesanan.getTotalHarga());

        verify(pesananRepository).save(pesanan);
    }
}