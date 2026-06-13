package com.tinjaku.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tinjaku.model.*;

@Service
public class PesananService {
    private List<Pesanan> pesananList = new ArrayList<>();

    public Pesanan tambahPesanan(Pesanan pesanan){
        pesananList.add(pesanan);
        return pesanan;
    }

    public List<Pesanan> getAllPesanan(){
        return pesananList;
    }


    public Pesanan getPesananById(Long id){
        return pesananList.stream()
               .filter(p -> p.getId().equals(id))
               .findFirst()
               .orElse(null);
    }

    // public Pesanan getPesananById(Long id){
    //     for(Pesanan pesanan : pesananList){
    //         if(pesanan.getId().equals(id)){
    //             return pesanan;
    //         }
    //     }
    //     return null;
    // }

    public Pesanan updatePesananService(Long id, Pesanan pesananBaru){
        for(Pesanan pesanan : pesananList){
            if(pesanan.getId().equals(id)){
                pesanan.setAlamat(pesananBaru.getAlamat());
                pesanan.setKeluhan(pesananBaru.getKeluhan());
                pesanan.setStatus(pesananBaru.getStatus());

                return pesanan;
            }
        }
        return null;
    }

    public boolean hapusPesananService(Long id){
        return pesananList.removeIf(p -> p.getId().equals(id));
    }
}