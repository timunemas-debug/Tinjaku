package com.tinjaku.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinjaku.dto.request.AlamatMitraRequest;
import com.tinjaku.dto.request.AlamatRequest;
import com.tinjaku.dto.response.AlamatMitraResponse;
import com.tinjaku.dto.response.AlamatResponse;
import com.tinjaku.service.AlamatMitraService;
import com.tinjaku.service.AlamatService;
import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/alamat")
public class AlamatController {
    private final AlamatService alamatService;
    private final AlamatMitraService alamatMitraService;

    public AlamatController(AlamatService alamatService, AlamatMitraService alamatMitraService){
        this.alamatService = alamatService;
        this.alamatMitraService = alamatMitraService;
    }

    @PostMapping("/{userId}")
    public AlamatResponse tambahAlamat(@PathVariable Long userId, @Valid @RequestBody AlamatRequest request){
        return alamatService.tambahAlamat(userId, request);
    }

    @PostMapping("/{mitraId}/alamat-mitra")
    public AlamatMitraResponse tambahAlamatMitra(@PathVariable Long mitraId, @Valid @RequestBody AlamatMitraRequest request){
        return alamatMitraService.tambahAlamat(mitraId, request);
    }

    @GetMapping
    public List<AlamatResponse> getAllAlamat(){
        return alamatService.getAllAlamat();
    }

    @GetMapping("/{id}")
    public AlamatResponse getAlamatById(@PathVariable Long id){
        return alamatService.getAlamatResponseById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        alamatService.deleteAlamat(id);
    }
}