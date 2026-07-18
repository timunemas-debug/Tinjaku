package com.tinjaku.controller;

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
import com.tinjaku.dto.request.MitraRequest;
import com.tinjaku.dto.request.OnlineRequest;
import com.tinjaku.dto.response.DashboardResponse;
import com.tinjaku.dto.response.MitraResponse;
import com.tinjaku.dto.response.OnlineResponse;
import com.tinjaku.dto.response.PesananResponse;
import com.tinjaku.dto.response.RatingResponse;
import com.tinjaku.model.StatusOnOff;
import com.tinjaku.service.MitraService;
import com.tinjaku.service.RatingService;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(MitraController.class)
public class MitraControllerTest {
    
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    MitraService mitraService;

    @MockitoBean
    RatingService ratingService;

    @Test
    public void shouldTambahMitra() throws Exception {

        MitraResponse response = new MitraResponse();
        response.setNama("WC MAKMUR");

        MitraRequest request = new MitraRequest();
        request.setNamaMitra("WC MAKMUR");
        request.setEmail("example@gmail.com");

        when(mitraService.tambahMitra(any(MitraRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/mitra")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nama").value("WC MAKMUR"));
    }

    @Test
    public void shouldOnlineMitra() throws Exception{

        OnlineResponse response = new OnlineResponse();
        response.setStatusOnOff(StatusOnOff.ONLINE);

        OnlineRequest request = new OnlineRequest();
        request.setStatusOnOff(StatusOnOff.ONLINE);

        when(mitraService.getMitraOnline(eq(1L), any(OnlineRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/mitra/1/online")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(response)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusOnOff").value("ONLINE"));
    }

    @Test
    public void shouldGetMitraById() throws Exception{

        MitraResponse response = new MitraResponse();
        response.setNama("Jeremy");

        when(mitraService.getMitraResponseById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/mitra/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nama").value("Jeremy"));
    }

    @Test
    public void shouldGetMitraKota()throws Exception{

        MitraResponse response1 = new MitraResponse();
        response1.setNama("jeremy");

        MitraResponse response2 = new MitraResponse();
        response2.setNama("prety");

        when(mitraService.getAllMitra())
                .thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/mitra"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nama").value("jeremy"))
                .andExpect(jsonPath("$[1].nama").value("prety"));
    }

    @Test
    public void shouldGetPesananMitraById()throws Exception{

        PesananResponse response = new PesananResponse();
        response.setId(1L);
        response.setKeluhan("WC MAMPET");
        response.setKelurahan("A");

        when(mitraService.getPesananMitra(1L))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/mitra/1/pesanan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].keluhan").value("WC MAMPET"))
                .andExpect(jsonPath("$[0].kelurahan").value("A"));
    }

    @Test
    public void shouldGetDashboard() throws Exception{

        DashboardResponse response = new DashboardResponse();
        response.setPesananDiTerima(4L);
        response.setPesananMenunggu(1L);

        when(mitraService.getDashboard(1L))
                .thenReturn(response);

        mockMvc.perform(get("/mitra/1/dashboard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pesananDiTerima").value(4L))
                .andExpect(jsonPath("$.pesananMenunggu").value(1L));
    }

    @Test
    public void shouldGetByMitraId() throws Exception{

        RatingResponse response1 = new RatingResponse();
        response1.setRating(4);

        RatingResponse response2 = new RatingResponse();
        response2.setRating(5);

        when(ratingService.getRatingMitra(1L))
                .thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/mitra/1/ratings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rating").value(4))
                .andExpect(jsonPath("$[1].rating").value(5));
    }

    @Test
    public void shouldGetAvgMitra() throws Exception{

        when(ratingService.getAverageRating(1L))
                .thenReturn(80.0);

        mockMvc.perform(get("/mitra/1/avg-ratings"))
                .andExpect(status().isOk())
                .andExpect(content().string("80.0"));
    }

    @Test
    public void shouldHapusMitraById() throws Exception{

        mitraService.deleteMitraById(1L);

        mockMvc.perform(delete("/mitra/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteRating() throws Exception{

        ratingService.hapusRating(1L);

        mockMvc.perform(delete("/mitra/1/hapus-rating"))
                .andExpect(status().isOk());
    }
}