package com.tinjaku.service;

import java.util.List;

import com.tinjaku.dto.request.MitraRequest;
import com.tinjaku.dto.response.MitraResponse;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.model.Kota;
import com.tinjaku.model.Mitra;
import com.tinjaku.model.Pesanan;
import com.tinjaku.repository.MitraRepository;
import com.tinjaku.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class MitraService {
    private final MitraRepository mitraRepository;

    public MitraService(MitraRepository mitraRepository){
        this.mitraRepository = mitraRepository;
    }

    public Mitra tambahMitra(MitraRequest request){

        if(mitraRepository.existsByNamaMitraIgnoreCase(request.getNamaMitra())){
            throw new BadRequestException("Mitra sudah terdaftar!");
        }

        Mitra mitra = new Mitra();
        
        mitra.setNamaMitra(request.getNamaMitra());
        mitra.setAlamatLengkap(request.getAlamatMitra());
        mitra.setKota(request.getKota());

        return mitraRepository.save(mitra);
    }

    public List<Mitra> getAllMitra(){
        return mitraRepository.findAll();
    }

    public List<Mitra> getMitraByKota(Kota kota){
        List<Mitra> mitraList = mitraRepository.findByKota(kota);

        if(mitraList.isEmpty()){
            throw new ResourceNotFound("Mitra tidak ditemukan!");
        }

        return mitraList;
    }

    public Mitra getMitraById(Long id){
        return mitraRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFound("Mitra tidak ditemukan!"));
    }

    public MitraResponse getMitraResponseById(Long id){
        Mitra mitra = getMitraById(id);

        return MitraResponse.builder()
               .nama(mitra.getNamaMitra())
               .kota(mitra.getKota())
               .build();
    }

    public void deleteMitraById(Long mitraId){

        if(!mitraRepository.existsById(mitraId)){
            throw new ResourceNotFound("Mitra tidak ditemukan!");
        }
        mitraRepository.deleteById(mitraId);
    }

    public List<Pesanan> getPesananMitra(Long mitraId){
        Mitra mitra = mitraRepository.findById(mitraId)
                .orElseThrow(() ->
                    new ResourceNotFound("Mitra tidak ditemukan!"));
                    
        return mitra.getPesanan();
    }
}