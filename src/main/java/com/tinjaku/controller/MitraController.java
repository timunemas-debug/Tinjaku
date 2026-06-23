package com.tinjaku.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.tinjaku.dto.MitraRequest;
import com.tinjaku.dto.MitraResponse;
import com.tinjaku.model.Kota;
import com.tinjaku.model.Mitra;
import com.tinjaku.service.MitraService;
import com.tinjaku.model.Pesanan;

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

    @GetMapping("/kota/{kota}")
    public List<Mitra> getMitraByKota(@PathVariable Kota kota){
        return mitraService.getMitraByKota(kota);
    }

    @GetMapping("/{mitraId}/pesanan")
    public List<Pesanan> getPesananMitraById(@PathVariable Long mitraId){
        return mitraService.getPesananMitra(mitraId);
    }
}
