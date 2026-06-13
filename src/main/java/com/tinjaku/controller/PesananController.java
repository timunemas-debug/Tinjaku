package com.tinjaku.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.tinjaku.service.PesananService;
import com.tinjaku.model.*;

@RestController
@RequestMapping("/pesanan")
public class PesananController {
    private final PesananService pesananService;

    public PesananController(PesananService pesananService){
        this.pesananService = pesananService;
    }

    @PostMapping
    public Pesanan tambah(@RequestBody Pesanan pesanan){
        return pesananService.tambahPesanan(pesanan);
    }

    @GetMapping
    public List<Pesanan> getAll(){
        return pesananService.getAllPesanan();
    }

    @GetMapping("/{id}")
    public Pesanan pesananbyid(@PathVariable Long id){
        return pesananService.getPesananById(id);
    }

    @DeleteMapping("/{id}")
    public Pesanan hapusPesanan(@PathVariable Long id){
        return pesananService.hapusPesananService(id);
    }

    @PutMapping("/{id}")
    public Pesanan updatePesanan(@PathVariable Long id,
                                 @RequestBody Pesanan pesananDiupdate){
        return pesananService.updatePesananService(id, pesananDiupdate);
    }
}