package com.tinjaku.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.tinjaku.service.PesananService;
import com.tinjaku.service.RatingService;

import jakarta.validation.Valid;

import com.tinjaku.dto.request.PesananRequest;
import com.tinjaku.dto.request.RatingRequest;
import com.tinjaku.dto.response.PesananResponse;
import com.tinjaku.dto.response.RatingResponse;
import com.tinjaku.mapper.PesananMapper;
import com.tinjaku.model.*;

@RestController
@RequestMapping("/pesanan")
public class PesananController {
    private final PesananService pesananService;
    private final RatingService ratingService;
    private final PesananMapper pesananMapper;

    public PesananController(PesananService pesananService, PesananMapper pesananMapper, RatingService ratingService){
        this.pesananService = pesananService;
        this.pesananMapper = pesananMapper;
        this.ratingService = ratingService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{userId}")
    public PesananResponse tambahPesanan(@Valid @RequestBody PesananRequest request, @PathVariable Long userId){
        Pesanan pesanan = pesananService.createPesanan(request);

        return pesananMapper.mapToResponse(pesanan);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{pesananId}/rating")
    public RatingResponse tambahRatingMitra(@PathVariable Long pesananId, @Valid  @RequestBody RatingRequest request){
        return ratingService.tambahRating(pesananId, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<PesananResponse> getAll(){
        return pesananService.getAllPesanan();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public PesananResponse pesananbyid(@PathVariable Long id){
        return pesananService.getPesananById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/status/{status}")
    public List<PesananResponse> pesananByStatus(@PathVariable StatusPesanan status){
        return pesananService.getPesananByStatus(status);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void hapusPesanan(@PathVariable Long id){
        pesananService.hapusPesananService(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public PesananResponse updatePesanan(@PathVariable Long id,
                                         @RequestBody Pesanan pesananDiupdate){
            Pesanan pesanan = pesananService.updatePesananService(id, pesananDiupdate);

            return pesananService.getPesananById(pesanan.getId());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/total")
    public long totalPesanan(){
        return pesananService.hitungTotalPesanan();
    }

    @PreAuthorize("hasRole('MITRA')")
    @PatchMapping("/{pesananId}/terima/{mitraId}")
    public PesananResponse terimaPesananUserByPesananId(@PathVariable Long pesananId, @PathVariable Long mitraId){
        Pesanan pesanan = pesananService.terimaPesanan(pesananId, mitraId);

        return pesananService.getPesananById(pesanan.getId());
    }

    @PreAuthorize("hasRole('MITRA')")
    @PatchMapping("{pesananId}/tolak")
    public PesananResponse tolakPesananByPesananId(@PathVariable Long pesananId){
        Pesanan pesanan = pesananService.tolakPesanan(pesananId);

        return pesananService.getPesananById(pesanan.getId());
    }
    
    @PreAuthorize("hasRole('MITRA')")
    @PatchMapping("/{pesananId}/dalam-perjalanan")
    public PesananResponse dalamPerjalananByPesananId(@PathVariable Long pesananId){
        Pesanan pesanan = pesananService.dalamPerjalanan(pesananId);
        
        return pesananService.getPesananById(pesanan.getId());
    }

    @PreAuthorize("hasRole('MITRA')")
    @PatchMapping("/{pesananId}/selesai")
    public PesananResponse selesaiPesananUserByPesananId(@PathVariable Long pesananId){
        Pesanan pesanan = pesananService.selesaiPesanan(pesananId);

        return pesananService.getPesananById(pesanan.getId());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{userId}/riwayat-user")
    public List<PesananResponse> riwayatPesananUser(@PathVariable Long userId){
        return pesananService.getRiwayatUser(userId);
    }

    @PreAuthorize("hasRole('MITRA')")
    @GetMapping("/{mitraId}/riwayat-mitra")
    public List<PesananResponse> riwayatPesananMitra(@PathVariable Long mitraId){
        return pesananService.getRiwayatMitra(mitraId);
    }
}