package com.tinjaku.service;

import org.springframework.stereotype.Service;

import com.tinjaku.dto.request.AlamatRequest;
import com.tinjaku.dto.response.AlamatResponse;
import com.tinjaku.exception.BadRequestException;
import com.tinjaku.exception.ResourceNotFound;
import java.util.List;
import com.tinjaku.mapper.AlamatMapper;
import com.tinjaku.model.Alamat;
import com.tinjaku.model.User;
import com.tinjaku.repository.AlamatRepository;
import com.tinjaku.repository.UserRepository;
import com.tinjaku.security.SecurityService;

@Service
public class AlamatService {
    private final AlamatRepository alamatRepository;
    private final UserRepository userRepository;
    private final AlamatMapper alamatMapper;
    private final SecurityService securityService;

    public AlamatService(AlamatRepository alamatRepository, AlamatMapper alamatMapper, UserRepository userRepository, SecurityService securityService){
        this.alamatRepository = alamatRepository;
        this.alamatMapper = alamatMapper;
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    public AlamatResponse tambahAlamat(Long userId, AlamatRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFound("User tidak ditemukan!"));

        Alamat alamat = alamatMapper.toEntity(request);

        alamat.setUser(user);

        alamatRepository.save(alamat);

        return alamatMapper.toResponse(alamat);
    }

    public Alamat getAlamatById(Long id){
        return alamatRepository.findById(id)
                .orElseThrow(() ->
                    new BadRequestException("Alamat tidak ditemukan!"));
    }

    public AlamatResponse getAlamatResponseById(Long id){
        Long userId = securityService.getCurrentUserId();

        Alamat alamat = getAlamatById(id);

        if(!alamat.getUser().getUserId().equals(userId)){
            throw new BadRequestException("Alamat bukan milik user!");
        }

        return alamatMapper.toResponse(alamat);
    }

    public void deleteAlamat(Long id){
        Alamat alamat = getAlamatById(id);

        alamatRepository.delete(alamat);
    }

    public AlamatResponse updateAlamat(Long id, AlamatRequest request){
        Alamat alamat = getAlamatById(id);

        alamat.setLabel(request.getLabel());
        alamat.setJalan(request.getJalan());
        alamat.setKelurahan(request.getKelurahan());
        alamat.setKecamatan(request.getKecamatan());
        alamat.setKota(request.getKota());
        alamat.setProvinsi(request.getProvinsi());

        return alamatMapper.toResponse(alamatRepository.save(alamat));
    }

    public List<AlamatResponse> getAllAlamat(){
        return alamatRepository.findAll()
                .stream()
                .map(alamatMapper::toResponse)
                .toList();
    }
}
