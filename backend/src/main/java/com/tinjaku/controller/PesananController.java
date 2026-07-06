package com.tinjaku.controller;

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

import jakarta.validation.Valid;

import com.tinjaku.dto.request.PesananRequest;
import com.tinjaku.dto.response.PesananResponse;
import com.tinjaku.mapper.PesananMapper;
import com.tinjaku.model.*;

@RestController
@RequestMapping("/pesanan")
public class PesananController {
    private final PesananService pesananService;
    private final PesananMapper pesananMapper;

    public PesananController(PesananService pesananService, PesananMapper pesananMapper){
        this.pesananService = pesananService;
        this.pesananMapper = pesananMapper;
    }

    @PostMapping("/{userId}")
    public PesananResponse tambahPesanan(@Valid @RequestBody PesananRequest request, @PathVariable Long userId){
        Pesanan pesanan = pesananService.createPesanan(request, userId);

        return pesananMapper.mapToResponse(pesanan);
    }

    @GetMapping
    public List<PesananResponse> getAll(){
        return pesananService.getAllPesanan();
    }

    @GetMapping("/{id}")
    public PesananResponse pesananbyid(@PathVariable Long id){
        return pesananService.getPesananById(id);
    }

    @GetMapping("/status/{status}")
    public List<PesananResponse> pesananByStatus(@PathVariable StatusPesanan status){
        return pesananService.getPesananByStatus(status);
    }

    @DeleteMapping("/{id}")
    public void hapusPesanan(@PathVariable Long id){
        pesananService.hapusPesananService(id);
    }

    @PutMapping("/{id}")
    public PesananResponse updatePesanan(@PathVariable Long id,
                                         @RequestBody Pesanan pesananDiupdate){
            Pesanan pesanan = pesananService.updatePesananService(id, pesananDiupdate);

            return pesananService.getPesananById(pesanan.getId());
    }

    @GetMapping("/total")
    public long totalPesanan(){
        return pesananService.hitungTotalPesanan();
    }

    @PatchMapping("/{pesananId}/terima/{mitraId}")
    public PesananResponse terimaPesananUserByPesananId(@PathVariable Long pesananId, @PathVariable Long mitraId){
        Pesanan pesanan = pesananService.terimaPesanan(pesananId, mitraId);

        return pesananService.getPesananById(pesanan.getId());
    }

    @PatchMapping("{pesananId}/tolak")
    public PesananResponse tolakPesananByPesananId(@PathVariable Long pesananId){
        Pesanan pesanan = pesananService.tolakPesanan(pesananId);

        return pesananService.getPesananById(pesanan.getId());
    }
    
    @PatchMapping("/{pesananId}/dalam-perjalanan")
    public PesananResponse dalamPerjalananByPesananId(@PathVariable Long pesananId){
        Pesanan pesanan = pesananService.dalamPerjalanan(pesananId);
        
        return pesananService.getPesananById(pesanan.getId());
    }

    @PatchMapping("/{pesananId}/selesai")
    public PesananResponse selesaiPesananUserByPesananId(@PathVariable Long pesananId){
        Pesanan pesanan = pesananService.selesaiPesanan(pesananId);

        return pesananService.getPesananById(pesanan.getId());
    }
}