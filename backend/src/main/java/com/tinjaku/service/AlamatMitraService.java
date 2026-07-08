package com.tinjaku.service;

import org.springframework.stereotype.Service;

import com.tinjaku.dto.request.AlamatMitraRequest;
import com.tinjaku.dto.response.AlamatMitraResponse;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.mapper.AlamatMitraMapper;
import com.tinjaku.repository.AlamatMitraRepository;
import com.tinjaku.repository.MitraRepository;
import com.tinjaku.model.AlamatMitra;
import com.tinjaku.model.Mitra;

@Service
public class AlamatMitraService {
    private final AlamatMitraRepository alamatMitraRepository;
    private final AlamatMitraMapper alamatMitraMapper;
    private final MitraRepository mitraRepository;

    public AlamatMitraService(AlamatMitraRepository alamatMitraRepository, AlamatMitraMapper alamatMitraMapper, MitraRepository mitraRepository){
        this.alamatMitraRepository = alamatMitraRepository;
        this.alamatMitraMapper = alamatMitraMapper;
        this.mitraRepository = mitraRepository;
    }

    public AlamatMitraResponse tambahAlamat(Long mitraId, AlamatMitraRequest request){
        Mitra mitra = mitraRepository.findById(mitraId)
                .orElseThrow(() ->
                    new ResourceNotFound("Mitra tidak ditemukan!"));
                    
        AlamatMitra alamat = alamatMitraMapper.toEntity(request);

        alamat.setMitra(mitra);

        alamatMitraRepository.save(alamat);

        return alamatMitraMapper.toResponse(alamat);
    }
}