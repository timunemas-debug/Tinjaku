package com.tinjaku.service;

import java.util.List;

import com.tinjaku.dto.request.MitraRequest;
import com.tinjaku.dto.response.DashboardResponse;
import com.tinjaku.dto.response.MitraResponse;
import com.tinjaku.dto.response.PesananResponse;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.mapper.MitraMapper;
import com.tinjaku.mapper.PesananMapper;
import com.tinjaku.model.Kota;
import com.tinjaku.model.Mitra;
import com.tinjaku.model.Pesanan;
import com.tinjaku.repository.MitraRepository;
import com.tinjaku.repository.PesananRepository;
import com.tinjaku.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class MitraService {
    private final MitraRepository mitraRepository;
    private final PesananRepository pesananRepository;
    private final PesananMapper pesananMapper;
    private final MitraMapper mitraMapper;

    public MitraService(MitraRepository mitraRepository, PesananRepository pesananRepository, PesananMapper pesananMapper, MitraMapper mitraMapper){
        this.mitraRepository = mitraRepository;
        this.pesananMapper = pesananMapper;
        this.mitraMapper = mitraMapper;
        this.pesananRepository = pesananRepository;
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

    public List<MitraResponse> getAllMitra(){
        return mitraRepository.findAll()
                .stream()
                .map(mitraMapper::mapToResponse)
                .toList();
    }

    public List<MitraResponse> getMitraByKota(Kota kota){
        return mitraRepository.findByKota(kota)
                .stream()
                .map(mitraMapper::mapToResponse)
                .toList();
    }

    public Mitra getMitraById(Long id){
        return mitraRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFound("Mitra tidak ditemukan!"));
    }

    public MitraResponse getMitraResponseById(Long id){
        Mitra mitra = getMitraById(id);

        return mitraMapper.mapToResponse(mitra);
    }

    public void deleteMitraById(Long mitraId){

        if(!mitraRepository.existsById(mitraId)){
            throw new ResourceNotFound("Mitra tidak ditemukan!");
        }
        mitraRepository.deleteById(mitraId);
    }

    public List<PesananResponse> getPesananMitra(Long mitraId){
        Mitra mitra = mitraRepository.findById(mitraId)
                .orElseThrow(() ->
                    new ResourceNotFound("Mitra tidak ditemukan!"));
                    
        return mitra.getPesanan()
                .stream()
                .map(pesananMapper::mapToResponse)
                .toList();
    }

    public DashboardResponse getDashboard(Long mitraId){
        Mitra mitra = mitraRepository.findById(mitraId)
                .orElseThrow(() ->
                    new ResourceNotFound("Mitra tidak ditemukan!"));
        
        Long totalPesanan = pesananRepository.countByMitraId(mitraId);
    }
}