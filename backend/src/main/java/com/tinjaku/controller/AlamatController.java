package com.tinjaku.controller;

import org.springframework.http.ResponseEntity;
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
    @PostMapping("/tambah-alamat-user")
    public AlamatResponse tambahAlamat(@Valid @RequestBody AlamatRequest request){
        return alamatService.tambahAlamat(request);
    }

    @PreAuthorize("hasRole('MITRA')")
    @PostMapping("/alamat-mitra")
    public AlamatMitraResponse tambahAlamatMitra(@Valid @RequestBody AlamatMitraRequest request){
        return alamatMitraService.tambahAlamat(request);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public ResponseEntity<List<AlamatResponse>> getAlamatUser(){
        return ResponseEntity.ok(alamatService.getAlamatUser());
    }

    @PreAuthorize("hasRole('MITRA')")
    @GetMapping("/mitra")
    public ResponseEntity<List<AlamatMitraResponse>> getAlamatMitra(){
        return ResponseEntity.ok(alamatMitraService.getAllAlamat());
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
    @PutMapping("/{alamatId}/update-alamat")
    public AlamatResponse updateAlamat(@PathVariable Long alamatId, @Valid @RequestBody AlamatRequest request){
        return alamatService.updateAlamat(alamatId, request);
    }

    @PreAuthorize("hasRole('MITRA')")
    @PutMapping("/{idAlamat}/update-alamat-mitra")
    public AlamatMitraResponse updateAlamatMitra(@PathVariable Long idAlamat, @Valid @RequestBody AlamatMitraRequest request){
        return alamatMitraService.updateAlamatMitra(idAlamat, request);
    }
}