package com.tinjaku.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{userId}")
    public AlamatResponse tambahAlamat(@PathVariable Long userId, @Valid @RequestBody AlamatRequest request){
        return alamatService.tambahAlamat(userId, request);
    }

    @PreAuthorize("hasRole('MITRA')")
    @PostMapping("/{mitraId}/alamat-mitra")
    public AlamatMitraResponse tambahAlamatMitra(@PathVariable Long mitraId, @Valid @RequestBody AlamatMitraRequest request){
        return alamatMitraService.tambahAlamat(mitraId, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<AlamatResponse> getAllAlamat(){
        return alamatService.getAllAlamat();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public AlamatResponse getAlamatById(@PathVariable Long id){
        return alamatService.getAlamatResponseById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        alamatService.deleteAlamat(id);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{userId}/update-alamat")
    public AlamatResponse updateAlamat(@PathVariable Long userId, @Valid AlamatRequest request){
        return alamatService.updateAlamat(userId, request);
    }

    @PreAuthorize("hasRole('MITRA')")
    @PutMapping("/{mitraId}/update-alamat-mitra")
    public AlamatMitraResponse updateAlamatMitra(@PathVariable Long mitraId, @Valid AlamatMitraRequest request){
        return alamatMitraService.updateAlamatMitra(mitraId, request);
    }
}