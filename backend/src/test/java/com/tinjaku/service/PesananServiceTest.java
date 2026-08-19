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

import org.hibernate.query.sqm.tree.expression.SqmCaseSearched.WhenFragment;
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
import com.tinjaku.model.Mitra;
import com.tinjaku.model.Pesanan;
import com.tinjaku.model.PesananHistory;
import com.tinjaku.model.StatusOnOff;
import com.tinjaku.model.StatusPesanan;
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

    @Test
    public void shouldUpdatePesananService(){

        Pesanan pesanan = new Pesanan();
        pesanan.setId(1L);
        pesanan.setKeluhan("Wc mampet");
        pesanan.setStatus(StatusPesanan.DALAM_PERJALANAN);

        Pesanan pesananBaru = new Pesanan();
        pesananBaru.setKeluhan("Wc luber luber");
        pesananBaru.setStatus(StatusPesanan.DIKERJAKAN);

        when(pesananRepository.findById(1L))
                .thenReturn(Optional.of(pesanan));

        when(pesananRepository.save(pesanan))
                .thenReturn(pesanan);

        Pesanan result = pesananService.updatePesananService(1L, pesananBaru);

        assertEquals("Wc luber luber", result.getKeluhan());
        assertEquals(StatusPesanan.DIKERJAKAN, result.getStatus());

        verify(pesananRepository).findById(1L);
        verify(pesananRepository).save(pesanan);
    }

    @Test
    public void shoulUpdatePesananResourceNotFound(){

        Pesanan pesanan = new Pesanan();
        pesanan.setId(1L);

        when(pesananRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFound.class, () -> pesananService.updatePesananService(1L, pesanan));

        verify(pesananRepository).findById(1L);
        verify(pesananRepository, never()).save(any());
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
    public void shouldUserMasihPunyaPesananMenunggu(){

        User user = new User();
        user.setUserId(1L);

        Pesanan pesanan = new Pesanan();
        pesanan.setStatus(StatusPesanan.MENUNGGU);

        user.setPesananList(List.of(pesanan));

        boolean result = pesananService.userMasihPunyaPesananMenunggu(user);

        assertTrue(result);
    }

    @Test
    public void shouldCreatePesanan(){

        User user = new User();
        user.setUserId(1L);
        user.setNamaDepan("Jeremy");
        user.setStatusOnOff(StatusOnOff.ONLINE);
        user.setPesananList(new ArrayList<>());

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

        Mitra mitra = new Mitra();
        mitra.setMitraId(1L);
        mitra.setLatitude(2.90942);
        mitra.setLongitude(2.2981);
        mitra.setStatusOnOff(StatusOnOff.ONLINE);

        when(securityService.getCurrentUserId())
                .thenReturn(1L);

        when(userService.getUserById(1L))
                .thenReturn(user);

        when(alamatService.getAlamatById(1L))
                .thenReturn(alamat);

        when(pesananRepository.save(any(Pesanan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        doReturn(List.of(mitra))
                .when(pesananService)
                .getEligibleMitra(any(Pesanan.class));

        doReturn(mitra)
                .when(pesananService)
                .findMitraTerdekat(eq(user), anyList());

        Pesanan result = pesananService.createPesanan(request);

        assertNotNull(result);

        assertEquals("Jamsuy", result.getNamaPenerima());
        assertEquals(StatusPesanan.MENUNGGU, result.getStatus());
        assertEquals(user, result.getUser());

        verify(pesananRepository).save(any(Pesanan.class));

        verify(notificationService).sendNotificationMitra(1L, "Ada pesanan baru di sekitar anda!");
        verify(notificationService).sendNotification(1L, "Pesanan berhasil dibuat!");
    }

    @Test
    public void shouldTerimaPesanan(){

        AlamatMitra alamat = new AlamatMitra();
        alamat.setIdAlamat(22L);
        alamat.setJalan("TEST JALAN");
        alamat.setKelurahan("KELURAHAN");
        alamat.setKecamatan("KECAMATAN");
        alamat.setKota(Kota.TANGERANG);
        
        User user = new User();
        user.setUserId(1L);
        user.setPickupLat(2.000);
        user.setPickupLong(2.000);
        
        Pesanan pesanan = new Pesanan();
        pesanan.setId(1L);
        pesanan.setKeluhan("WC mampet");
        pesanan.setKota(Kota.TANGERANG);
        pesanan.setKecamatan("KECAMATAN");
        pesanan.setStatus(StatusPesanan.MENUNGGU);
        pesanan.setUser(user);

        Mitra mitra = new Mitra();
        mitra.setMitraId(1L);
        mitra.setNamaMitra("Sedot Wc");
        mitra.setStatusOnOff(StatusOnOff.ONLINE);
        mitra.setLatitude(2.000);
        mitra.setLongitude(2.000);

        mitra.setAlamatList(List.of(alamat));


        when(pesananRepository.save(any(Pesanan.class)))
                .thenReturn(pesanan);

        when(mitraService.getMitraById(1L))
                .thenReturn(mitra);

        when(ratingService.getAverageRating(1L))
                .thenReturn(3.0);

        doReturn(pesanan)
                .when(pesananService)
                .getPesananEntityById(pesanan.getId());

        Pesanan result = pesananService.terimaPesanan(1L, 1L);

        assertNotNull(result);
        assertEquals(Kota.TANGERANG, result.getKota());
        assertEquals("KECAMATAN", result.getKecamatan());
        assertEquals(StatusPesanan.DITERIMA, result.getStatus());
        assertEquals(mitra, result.getMitra());

        verify(pesananHistoryRepository).save(any(PesananHistory.class));
        verify(pesananRepository).save(any(Pesanan.class));
    }

    @Test
    public void shouldSelesaiPesanan(){

        Pesanan pesanan = new Pesanan();
        pesanan.setId(1L);
        pesanan.setStatus(StatusPesanan.DALAM_PERJALANAN);

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
    public void shouldTolakPesanan(){

        Pesanan pesanan = new Pesanan();
        pesanan.setId(1L);
        pesanan.setStatus(StatusPesanan.MENUNGGU);

        when(pesananRepository.findById(1L))
                .thenReturn(Optional.of(pesanan));
        
        when(pesananRepository.save(any(Pesanan.class)))
                .thenReturn(pesanan);

        Pesanan result = pesananService.tolakPesanan(1L);
        
        assertEquals(StatusPesanan.DITOLAK, result.getStatus());

        verify(pesananHistoryRepository).save(any(PesananHistory.class));
        verify(pesananRepository).save(any(Pesanan.class));
    }

    @Test
    public void shouldDalamPerjalanan(){

        Pesanan pesanan = new Pesanan();
        pesanan.setId(1L);
        pesanan.setStatus(StatusPesanan.DITERIMA);

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

        Pesanan pesanan = new Pesanan();
        pesanan.setUser(user);

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