package com.tinjaku.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinjaku.dto.request.PesananRequest;
import com.tinjaku.dto.request.RatingRequest;
import com.tinjaku.dto.response.PesananResponse;
import com.tinjaku.dto.response.RatingResponse;
import com.tinjaku.mapper.PesananMapper;
import com.tinjaku.model.Pesanan;
import com.tinjaku.model.StatusPesanan;
import com.tinjaku.service.PesananService;
import com.tinjaku.service.RatingService;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(PesananController.class)
public class PesananControllerTest {
    
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    PesananService pesananService;

    @MockitoBean
    PesananMapper pesananMapper;

    @MockitoBean
    RatingService ratingService;

    @Test
    public void shouldTambahPesanan() throws Exception{

        Pesanan pesanan = new Pesanan();
        pesanan.setId(1L);
        pesanan.setKeluhan("WC MAMPET");
        pesanan.setKelurahan("A");
        pesanan.setKecamatan("B");

        PesananRequest request = new PesananRequest();
        request.setAlamatId(1L);
        request.setKeluhan("WC MAMPET");
        request.setNamaPenerima("Jeremy");

        PesananResponse response = new PesananResponse();
        response.setId(1L);
        response.setKeluhan("WC MAMPET");
        response.setKelurahan("A");
        response.setKecamatan("B");

        when(pesananService.createPesanan(any(PesananRequest.class), eq(1L)))
                .thenReturn(pesanan);

        when(pesananMapper.mapToResponse(pesanan))
                .thenReturn(response);

        mockMvc.perform(post("/pesanan/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keluhan").value("WC MAMPET"))
                .andExpect(jsonPath("$.kelurahan").value("A"))
                .andExpect(jsonPath("$.kecamatan").value("B"));

    }

    @Test
    public void shouldTambahRatingMitra() throws Exception{

        RatingRequest request = new RatingRequest();
        request.setRating(5);

        RatingResponse response = new RatingResponse();
        response.setNamaMitra("WC MAKMUR");
        response.setRating(5);

        when(ratingService.tambahRating(eq(1L), any(RatingRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/pesanan/1/rating")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(5));
    }

    @Test
    public void shouldGetAllPesanan() throws Exception{

        PesananResponse response = new PesananResponse();
        response.setId(1L);
        response.setKeluhan("WC MAMPET");
        response.setKelurahan("A");
        response.setKecamatan("B");

        PesananResponse response2 = new PesananResponse();
        response2.setId(2L);
        response2.setKeluhan("WC PENUHH");
        response2.setKelurahan("C");
        response2.setKecamatan("D");

        when(pesananService.getAllPesanan())
                .thenReturn(List.of(response, response2));

        mockMvc.perform(get("/pesanan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].keluhan").value("WC MAMPET"))
                .andExpect(jsonPath("$[1].keluhan").value("WC PENUHH"))
                .andExpect(jsonPath("$[0].kelurahan").value("A"))
                .andExpect(jsonPath("$[0].kecamatan").value("B"))
                .andExpect(jsonPath("$[1].kelurahan").value("C"))
                .andExpect(jsonPath("$[1].kecamatan").value("D"));
    }

    @Test
    public void shouldPesananById() throws Exception{

        PesananResponse response = new PesananResponse();
        response.setId(1L);
        response.setKeluhan("WC MAMPET");
        response.setKelurahan("A");
        response.setKecamatan("B");

        when(pesananService.getPesananById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/pesanan/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keluhan").value("WC MAMPET"))
                .andExpect(jsonPath("$.kelurahan").value("A"))
                .andExpect(jsonPath("$.kecamatan").value("B"));
    }

    @Test
    public void shouldPesananByStatus() throws Exception{

        PesananResponse response = new PesananResponse();
        response.setId(1L);
        response.setStatus(StatusPesanan.DALAM_PERJALANAN);
        response.setKelurahan("A");
        response.setKecamatan("B");


        when(pesananService.getPesananByStatus(StatusPesanan.DALAM_PERJALANAN))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/pesanan/status/DALAM_PERJALANAN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].kelurahan").value("A"))
                .andExpect(jsonPath("$[0].kecamatan").value("B"));
    }

    @Test
    public void shouldDeletePesanan() throws Exception{

        pesananService.hapusPesananService(1L);
        
        mockMvc.perform(delete("/pesanan/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdatePesanan() throws Exception{

        PesananResponse response = new PesananResponse();
        response.setId(1L);
        response.setKeluhan("WC MAMPET");
        response.setKelurahan("A");
        response.setKecamatan("B");
        response.setNamaPenerima("TEST");

        Pesanan pesanan = new Pesanan();
        pesanan.setId(1L);
        pesanan.setKeluhan("WC MAMPET");
        pesanan.setKelurahan("C");
        pesanan.setKecamatan("D");
        pesanan.setNamaPenerima("YOGI");

        when(pesananService.updatePesananService(eq(1L), any(Pesanan.class)))
                .thenReturn(pesanan);

        when(pesananService.getPesananById(1L))
                .thenReturn(response);

        mockMvc.perform(put("/pesanan/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pesanan)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keluhan").value("WC MAMPET"))
                .andExpect(jsonPath("$.kelurahan").value("A"))
                .andExpect(jsonPath("$.kecamatan").value("B"))
                .andExpect(jsonPath("$.namaPenerima").value("TEST"));
    }

    @Test
    public void shouldTotalPesanan() throws Exception{

        pesananService.hitungTotalPesanan();

        mockMvc.perform(get("/pesanan/total"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldTerimaPesananUserByPesananId() throws Exception{

        Pesanan pesanan = new Pesanan();
        pesanan.setId(1L);
        pesanan.setStatus(StatusPesanan.MENUNGGU);
        pesanan.setKeluhan("WC MAMPET");

        PesananResponse response = new PesananResponse();
        response.setId(1L);
        response.setStatus(StatusPesanan.DITERIMA);
        response.setKeluhan("WC MAMPET");

        when(pesananService.terimaPesanan(1L, 2L))
                .thenReturn(pesanan);

        when(pesananService.getPesananById(1L))
                .thenReturn(response);

        mockMvc.perform(patch("/pesanan/1/terima/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DITERIMA"))
                .andExpect(jsonPath("$.keluhan").value("WC MAMPET"));
    }

    @Test
    public void shouldTolakPesanan() throws Exception{

        Pesanan pesanan = new Pesanan();
        pesanan.setId(1L);
        pesanan.setStatus(StatusPesanan.MENUNGGU);
        pesanan.setKeluhan("WC MAMPET");

        PesananResponse response = new PesananResponse();
        response.setId(1L);
        response.setStatus(StatusPesanan.DITOLAK);
        response.setKeluhan("WC MAMPET");

        when(pesananService.tolakPesanan(1L))
                .thenReturn(pesanan);

        when(pesananService.getPesananById(1L))
                .thenReturn(response);

        mockMvc.perform(patch("/pesanan/1/tolak"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DITOLAK"));
    }

    @Test
    public void shouldDalamPerjalanan() throws Exception{

        Pesanan pesanan = new Pesanan();
        pesanan.setId(1L);
        pesanan.setStatus(StatusPesanan.DITERIMA);
        pesanan.setKeluhan("WC MAMPET");

        PesananResponse response = new PesananResponse();
        response.setId(1L);
        response.setStatus(StatusPesanan.DALAM_PERJALANAN);
        response.setKeluhan("WC MAMPET");

        when(pesananService.dalamPerjalanan(1L))
                .thenReturn(pesanan);

        when(pesananService.getPesananById(1L))
                .thenReturn(response);

        mockMvc.perform(patch("/pesanan/1/dalam-perjalanan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DALAM_PERJALANAN"));
    }

    @Test
    public void shouldSelesaiPesananUserByPesananId() throws Exception{

        Pesanan pesanan = new Pesanan();
        pesanan.setId(1L);
        pesanan.setStatus(StatusPesanan.DIKERJAKAN);
        pesanan.setKeluhan("WC MAMPET");

        PesananResponse response = new PesananResponse();
        response.setId(1L);
        response.setStatus(StatusPesanan.SELESAI);
        response.setKeluhan("WC MAMPET");

        when(pesananService.selesaiPesanan(1L))
                .thenReturn(pesanan);

        when(pesananService.getPesananById(1L))
                .thenReturn(response);

        mockMvc.perform(patch("/pesanan/1/selesai"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SELESAI"));
    }
}