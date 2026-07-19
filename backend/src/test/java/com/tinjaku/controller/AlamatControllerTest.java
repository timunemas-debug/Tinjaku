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
import com.tinjaku.dto.request.AlamatMitraRequest;
import com.tinjaku.dto.request.AlamatRequest;
import com.tinjaku.dto.response.AlamatMitraResponse;
import com.tinjaku.dto.response.AlamatResponse;
import com.tinjaku.model.Kota;
import com.tinjaku.model.Label;
import com.tinjaku.model.LabelMitra;
import com.tinjaku.service.AlamatMitraService;
import com.tinjaku.service.AlamatService;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AlamatController.class)
public class AlamatControllerTest {
    

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    AlamatService alamatService;

    @MockitoBean
    AlamatMitraService alamatMitraService;

    String jalan = "A";
    String kelurahan = "B";
    String kecamatan = "C";
    String provinsi = "D";


    @Test
    public void shouldTambahAlamat() throws Exception{

        AlamatRequest request = new AlamatRequest();
        request.setLabel(Label.RUMAH);
        request.setJalan(jalan);
        request.setKelurahan(kelurahan);
        request.setKecamatan(kecamatan);
        request.setKota(Kota.TANGERANG);
        request.setProvinsi(provinsi);

        AlamatResponse response = new AlamatResponse();
        response.setLabel(Label.RUMAH);
        response.setJalan(jalan);
        response.setKelurahan(kelurahan);
        response.setKecamatan(kecamatan);
        response.setKota(Kota.TANGERANG);
        response.setProvinsi(provinsi);

        when(alamatService.tambahAlamat(eq(1L), any(AlamatRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/alamat/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(response)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.label").value("RUMAH"))
                .andExpect(jsonPath("$.jalan").value(jalan))
                .andExpect(jsonPath("$.kelurahan").value(kelurahan))
                .andExpect(jsonPath("$.kecamatan").value(kecamatan))
                .andExpect(jsonPath("$.kota").value("TANGERANG"))
                .andExpect(jsonPath("$.provinsi").value(provinsi));
    }

    @Test
    public void shouldTambahAlamatMitra() throws Exception{

        AlamatMitraRequest request = new AlamatMitraRequest();
        request.setLabelMitra(LabelMitra.CABANG);
        request.setJalan(jalan);
        request.setKelurahan(kelurahan);
        request.setKecamatan(kecamatan);
        request.setKota(Kota.BEKASI);
        request.setProvinsi(provinsi);

        AlamatMitraResponse response = new AlamatMitraResponse();
        response.setLabelMitra(LabelMitra.CABANG);
        response.setJalan(jalan);
        response.setKelurahan(kelurahan);
        response.setKecamatan(kecamatan);
        response.setKota(Kota.BEKASI);
        response.setProvinsi(provinsi);

        when(alamatMitraService.tambahAlamat(eq(1L), any(AlamatMitraRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/alamat/1/alamat-mitra")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(response)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.labelMitra").value("CABANG"))
                .andExpect(jsonPath("$.jalan").value(jalan))
                .andExpect(jsonPath("$.kelurahan").value(kelurahan))
                .andExpect(jsonPath("$.kecamatan").value(kecamatan))
                .andExpect(jsonPath("$.kota").value("BEKASI"))
                .andExpect(jsonPath("$.provinsi").value(provinsi));

    }

    @Test
    public void shouldGetAllAlamat() throws Exception{

        AlamatResponse response = new AlamatResponse();
        response.setLabel(Label.APARTEMENT);
        response.setJalan(jalan);
        response.setKelurahan(kelurahan);
        response.setKecamatan(kecamatan);
        response.setKota(Kota.BEKASI);
        response.setProvinsi(provinsi);

        AlamatResponse response2 = new AlamatResponse();
        response2.setLabel(Label.GUDANG);
        response2.setJalan("K");
        response2.setKelurahan("M");
        response2.setKecamatan("N");
        response2.setKota(Kota.BOGOR);
        response2.setProvinsi("L");

        when(alamatService.getAllAlamat())
                .thenReturn(List.of(response, response2));

        mockMvc.perform(get("/alamat"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].label").value("APARTEMENT"))
                .andExpect(jsonPath("$[0].jalan").value(jalan))
                .andExpect(jsonPath("$[0].kelurahan").value(kelurahan))
                .andExpect(jsonPath("$[0].kecamatan").value(kecamatan))
                .andExpect(jsonPath("$[0].kota").value("BEKASI"))
                .andExpect(jsonPath("$[0].provinsi").value(provinsi))
                .andExpect(jsonPath("$[1].label").value("GUDANG"))
                .andExpect(jsonPath("$[1].jalan").value("K"))
                .andExpect(jsonPath("$[1].kelurahan").value("M"))
                .andExpect(jsonPath("$[1].kecamatan").value("N"))
                .andExpect(jsonPath("$[1].kota").value("BOGOR"))
                .andExpect(jsonPath("$[1].provinsi").value("L"));
    }

    @Test
    public void shouldAlamatById() throws Exception{

        AlamatResponse response = new AlamatResponse();
        response.setLabel(Label.APARTEMENT);
        response.setJalan(jalan);
        response.setKelurahan(kelurahan);
        response.setKecamatan(kecamatan);
        response.setKota(Kota.BEKASI);
        response.setProvinsi(provinsi);

        when(alamatService.getAlamatResponseById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/alamat/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.label").value("APARTEMENT"))
                .andExpect(jsonPath("$.jalan").value(jalan))
                .andExpect(jsonPath("$.kelurahan").value(kelurahan))
                .andExpect(jsonPath("$.kecamatan").value(kecamatan))
                .andExpect(jsonPath("$.kota").value("BEKASI"))
                .andExpect(jsonPath("$.provinsi").value(provinsi));
    }
    
    @Test
    public void shouldDeleteById() throws Exception{

        alamatService.deleteAlamat(1L);

        mockMvc.perform(delete("/alamat/1"))
                .andExpect(status().isOk());
    }
}