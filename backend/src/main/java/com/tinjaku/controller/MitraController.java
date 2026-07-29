package com.tinjaku.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.tinjaku.dto.request.MitraRequest;
import com.tinjaku.dto.request.OnlineRequest;
import com.tinjaku.dto.response.DashboardResponse;
import com.tinjaku.dto.response.MitraResponse;
import com.tinjaku.dto.response.OnlineResponse;
import com.tinjaku.dto.response.PesananResponse;
import com.tinjaku.dto.response.RatingResponse;
import com.tinjaku.model.Kota;
import com.tinjaku.service.MitraService;
import com.tinjaku.service.RatingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/mitra")
public class MitraController {
    private final MitraService mitraService;
    private final RatingService ratingService;

    public MitraController(MitraService mitraService, RatingService ratingService){
        this.mitraService = mitraService;
        this.ratingService = ratingService;
    }

    @PostMapping
    public MitraResponse tambah(@Valid @RequestBody MitraRequest request){
        return mitraService.tambahMitra(request);
    }

    @PreAuthorize("hasAnyRole('MITRA', 'ADMIN')")
    @PostMapping("/{mitraId}/online")
    public OnlineResponse onlineMitra(@PathVariable Long mitraId, @RequestBody OnlineRequest request){
        return mitraService.getMitraOnline(mitraId, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{mitraId}")
    public MitraResponse getMitraById(@PathVariable Long mitraId){
        return mitraService.getMitraResponseById(mitraId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/kota")
    public List<MitraResponse> getMitraByKota(@RequestParam Kota kota){
        return mitraService.getMitraByKota(kota);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<MitraResponse> getAllMitra(){
        return mitraService.getAllMitra();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{mitraId}/pesanan")
    public List<PesananResponse> getPesananMitraById(@PathVariable Long mitraId){
        return mitraService.getPesananMitra(mitraId);
    }

    @PreAuthorize("hasRole('MITRA')")
    @GetMapping("/{mitraId}/dashboard")
    public DashboardResponse getDashboard(@PathVariable Long mitraId){
        return mitraService.getDashboard(mitraId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{mitraId}/ratings")
    public List<RatingResponse> getByMitraId(@PathVariable Long mitraId){
        return ratingService.getRatingMitra(mitraId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{mitraId}/avg-ratings")
    public double getAvgMitra(@PathVariable Long mitraId){
        return ratingService.getAverageRating(mitraId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{mitraId}")
    public void hapusMitraById(@PathVariable Long mitraId){
        mitraService.deleteMitraById(mitraId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{ratingId}/hapus-rating")
    public void deleteRating(@PathVariable Long ratingId){
        ratingService.hapusRating(ratingId);
    }
}