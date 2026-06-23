package com.tinjaku.service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tinjaku.exception.BadRequestException;
import com.tinjaku.dto.MitraRequest;
import com.tinjaku.dto.PesananRequest;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.model.*;

@Service
public class PesananService {
    private final UserService userService;
    private final MitraService mitraService;
    private List<Pesanan> pesananList = new ArrayList<>();

    public PesananService(UserService userService, MitraService mitraService){
        this.userService = userService;
        this.mitraService = mitraService;
    }
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
               .orElseThrow(() -> new ResourceNotFound("Pesanan dengan id : " + id + " tidak ditemukan"));
    }

    public List<Pesanan> getPesananByStatus(StatusPesanan status){
        return pesananList.stream()
               .filter(p -> p.getStatus().equals(status))
               .collect(Collectors.toList());
    }

    public int hitungTotalPesanan(){
        return pesananList.size();
    }

    public Pesanan updatePesananService(Long id, Pesanan pesananBaru){
        for(Pesanan pesanan : pesananList){
            if(pesanan.getId().equals(id)){
                pesanan.setKeluhan(pesananBaru.getKeluhan());
                pesanan.setStatus(pesananBaru.getStatus());

                return pesanan;
            }
        }
        throw new ResourceNotFound("Pesanan dengan id : " + id + " tidak ditemukan");
    }

    public Pesanan hapusPesananService(Long id){
        Pesanan pesanan = pesananList.stream()
                          .filter(p -> p.getId().equals(id))
                          .findFirst()
                          .orElseThrow(() ->
                          new ResourceNotFound("Pesanan tidak ditemukan!"));
        pesananList.remove(pesanan);

        return pesanan;
    }

    public boolean userMasihPunyaPesananMenunggu(User user){
        return user.getPesananList().stream()
               .anyMatch(u -> u.getStatus() == StatusPesanan.MENUNGGU);
    }

    public Pesanan tambahPesananKeMitra(PesananRequest request, Long UserId){
        User user = userService.getUserById(UserId);

        List<Mitra> mitraKota = mitraService.getMitraByKota(request.getKota());

        if(mitraKota.isEmpty()){
            throw new ResourceNotFound("Kota tidak ditemukan!");
        }
        
        Mitra mitra = mitraKota.get(0);

        if(userMasihPunyaPesananMenunggu(user)){
            throw new BadRequestException("Masih ada pesanan yang menunggu!");
        }

        Pesanan pesanan = new Pesanan();

        pesanan.setId((long) (Math.random() * 100000));
        pesanan.setKeluhan(request.getKeluhan());
        pesanan.setUser(user);
        pesanan.setMitra(mitra);
        pesanan.setStatus(StatusPesanan.MENUNGGU);
        pesanan.setKota(request.getKota());

        mitra.getPesananList().add(pesanan);
        user.getPesananList().add(pesanan);
        pesananList.add(pesanan);

        return pesanan;
    }

}