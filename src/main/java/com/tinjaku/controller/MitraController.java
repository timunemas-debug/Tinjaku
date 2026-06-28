package com.tinjaku.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.tinjaku.dto.request.MitraRequest;
import com.tinjaku.dto.response.MitraResponse;
import com.tinjaku.model.Kota;
import com.tinjaku.model.Mitra;
import com.tinjaku.service.MitraService;
import com.tinjaku.service.PesananService;
import com.tinjaku.model.Pesanan;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/mitra")
public class MitraController {
    private final MitraService mitraService;
    private final PesananService pesananService;

    public MitraController(MitraService mitraService, PesananService pesananService){
        this.mitraService = mitraService;
        this.pesananService = pesananService;
    }

    @PostMapping
    public Mitra tambah(@Valid @RequestBody MitraRequest request){
        return mitraService.tambahMitra(request);
    }

    @GetMapping
    public List<Mitra> getMitraByKota(@RequestParam Kota kota){
        return mitraService.getMitraByKota(kota);
    }

    @GetMapping("/{mitraId}/pesanan")
    public List<Pesanan> getPesananMitraById(@PathVariable Long mitraId){
        return mitraService.getPesananMitra(mitraId);
    }

    @PatchMapping("/{mitraId}/pesanan/{pesananId}/diterima")
    public Pesanan teirmaPesananUserByPesananId(@PathVariable Long pesananId, @PathVariable Long mitraId){
        return pesananService.terimaPesanan(pesananId, mitraId);
    }
}
