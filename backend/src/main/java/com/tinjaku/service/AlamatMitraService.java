package com.tinjaku.service;

import org.springframework.stereotype.Service;

import com.tinjaku.dto.request.AlamatMitraRequest;
import com.tinjaku.dto.response.AlamatMitraResponse;
import com.tinjaku.exception.BadRequestException;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.mapper.AlamatMitraMapper;
import com.tinjaku.repository.AlamatMitraRepository;
import com.tinjaku.repository.MitraRepository;
import com.tinjaku.security.SecurityService;

import jakarta.transaction.Transactional;

import com.tinjaku.model.AlamatMitra;
import com.tinjaku.model.Mitra;

import java.util.List;

@Service
public class AlamatMitraService {
    private final AlamatMitraRepository alamatMitraRepository;
    private final AlamatMitraMapper alamatMitraMapper;
    private final MitraRepository mitraRepository;
    private final SecurityService securityService;

    public AlamatMitraService(AlamatMitraRepository alamatMitraRepository, AlamatMitraMapper alamatMitraMapper, MitraRepository mitraRepository, SecurityService securityService){
        this.alamatMitraRepository = alamatMitraRepository;
        this.alamatMitraMapper = alamatMitraMapper;
        this.mitraRepository = mitraRepository;
        this.securityService =securityService;
    }

    @Transactional
    public AlamatMitraResponse tambahAlamat(AlamatMitraRequest request){

        Long mitraId = securityService.getCurrentMitraId();
        
        Mitra mitra = mitraRepository.findById(mitraId)
                .orElseThrow(() ->
                    new ResourceNotFound("Mitra tidak ditemukan!"));
                    
        AlamatMitra alamat = alamatMitraMapper.toEntity(request);

        alamat.setMitra(mitra);

        alamatMitraRepository.save(alamat);

        return alamatMitraMapper.toResponse(alamat);
    }

    public AlamatMitra getAlamatMitraById(Long mitraId){
        return alamatMitraRepository.findById(mitraId)
                .orElseThrow(() ->
                    new ResourceNotFound("Mitra tida ditemukan!"));
    }

    public AlamatMitraResponse getAlamatResponseById(Long mitraId){

        Long idMitra = securityService.getCurrentUserId();

        AlamatMitra alamatMitra = getAlamatMitraById(mitraId);

        if (!alamatMitra.getMitra().getMitraId().equals(idMitra)) {
            throw new BadRequestException("Alamat bukan milik mitra!");
        }

        return alamatMitraMapper.toResponse(alamatMitra);
    }

    public List<AlamatMitraResponse> getAllAlamat(){
        return alamatMitraRepository.findAll()
                .stream()
                .map(alamatMitraMapper::toResponse)
                .toList();
    }

    public void deleteAlamat(Long mitraId){
        AlamatMitra alamat = getAlamatMitraById(mitraId);
        alamatMitraRepository.delete(alamat);
    }

    public AlamatMitraResponse updateAlamatMitra(Long idAlamat, AlamatMitraRequest request){

        Long idMitra = securityService.getCurrentUserId();

        AlamatMitra alamatMitra = getAlamatMitraById(idAlamat);
        
        if(!alamatMitra.getMitra().getMitraId().equals(idMitra)){
            throw new BadRequestException("Alamat bukan milik mitra!");
        }

        alamatMitra.setLabelMitra(request.getLabelMitra());
        alamatMitra.setJalan(request.getJalan());
        alamatMitra.setKelurahan(request.getKelurahan());
        alamatMitra.setKecamatan(request.getKecamatan());
        alamatMitra.setKota(request.getKota());
        alamatMitra.setProvinsi(request.getProvinsi());

        return alamatMitraMapper.toResponse(alamatMitraRepository.save(alamatMitra));
    }
}