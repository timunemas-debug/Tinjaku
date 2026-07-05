package com.tinjaku.controller;

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
import com.tinjaku.dto.response.DashboardResponse;
import com.tinjaku.dto.response.MitraResponse;
import com.tinjaku.dto.response.PesananResponse;
import com.tinjaku.model.Kota;
import com.tinjaku.model.Mitra;
import com.tinjaku.service.MitraService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/mitra")
public class MitraController {
    private final MitraService mitraService;

    public MitraController(MitraService mitraService){
        this.mitraService = mitraService;
    }

    @PostMapping
    public Mitra tambah(@Valid @RequestBody MitraRequest request){
        return mitraService.tambahMitra(request);
    }

    @GetMapping("{mitraId}")
    public MitraResponse getMitraById(@PathVariable Long mitraId){
        return mitraService.getMitraResponseById(mitraId);
    }

    @GetMapping("/kota")
    public List<MitraResponse> getMitraByKota(@RequestParam Kota kota){
        return mitraService.getMitraByKota(kota);
    }

    @GetMapping
    public List<MitraResponse> getAllMitra(){
        return mitraService.getAllMitra();
    }

    @GetMapping("/{mitraId}/pesanan")
    public List<PesananResponse> getPesananMitraById(@PathVariable Long mitraId){
        return mitraService.getPesananMitra(mitraId);
    }

    @GetMapping("/{mitraId}/dashboard")
    public DashboardResponse getDashboard(@PathVariable Long mitraId){
        return mitraService.getDashboard(mitraId);
    }

    @DeleteMapping("{mitraId}")
    public void hapusMitraById(@PathVariable Long mitraId){
        mitraService.deleteMitraById(mitraId);
    }

}
