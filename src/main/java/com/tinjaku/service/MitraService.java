package com.tinjaku.service;

import java.util.ArrayList;
import java.util.List;

import com.tinjaku.dto.request.MitraRequest;
import com.tinjaku.dto.response.MitraResponse;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.model.Kota;
import com.tinjaku.model.Mitra;
import com.tinjaku.model.Pesanan;

import com.tinjaku.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class MitraService {
    private List<Mitra> mitraList = new ArrayList<>();
    private Long nextId = 1L;

    public Mitra tambahMitra(MitraRequest request){

        boolean sudahAda = mitraList.stream()
                .anyMatch(m -> m.getNamaMitra().equalsIgnoreCase(request.getNamaMitra()));
                
        if(sudahAda){
            throw new BadRequestException("Mitra sudah terdaftar!");
        }
        
        Mitra mitra = new Mitra();
        
        mitra.setMitraId(nextId++);
        mitra.setNamaMitra(request.getNamaMitra());
        mitra.setAlamatLengkap(request.getAlamatMitra());
        mitra.setKota(request.getKota());
        mitra.setPesananList(new ArrayList<>());

        mitraList.add(mitra);
        return mitra;
    }

    public List<Mitra> getAllMitra(){
        return mitraList;
    }

    public List<Mitra> getMitraByKota(Kota kota){
        return mitraList.stream()
               .filter(m -> m.getKota() == kota)
               .toList();
    }

    public Mitra getMitraById(Long id){
        Mitra mitra =  mitraList.stream()
               .filter(m -> m.getMitraId().equals(id))
               .findFirst()
               .orElseThrow(() ->
                new ResourceNotFound("Mitra tidak ditemukan!"));
        return mitra;
    }

    public MitraResponse getMitraResponseById(Long id){
        Mitra mitra = getMitraById(id);

        return new MitraResponse(mitra.getNamaMitra(), mitra.getKota());
    }

    public Mitra deleteMitraById(Long mitraId){
        Mitra mitra = mitraList.stream()
                      .filter(m -> m.getMitraId().equals(mitraId))
                      .findFirst()
                      .orElseThrow(() ->
                      new ResourceNotFound("Mitra tidak ditemukan!"));
        mitraList.remove(mitra);

        return mitra;
    }

    public List<Pesanan> getPesananMitra(Long mitraId){
        Mitra mitra = mitraList.stream()
                      .filter(m -> m.getMitraId().equals(mitraId))
                      .findFirst()
                      .orElseThrow(() ->
                      new ResourceNotFound("Mitra dengan id tersebut tidak ditemukan!"));

        return mitra.getPesananList();
    }
}