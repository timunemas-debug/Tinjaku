package com.tinjaku.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinjaku.dto.request.MitraRequest;
import com.tinjaku.dto.response.MitraResponse;
import com.tinjaku.service.MitraService;
import com.tinjaku.service.RatingService;

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
}