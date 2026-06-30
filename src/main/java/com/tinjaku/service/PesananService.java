package com.tinjaku.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tinjaku.exception.BadRequestException;
import com.tinjaku.dto.request.PesananRequest;
import com.tinjaku.dto.response.PesananResponse;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.model.*;
import com.tinjaku.repository.PesananRepository;

@Service
public class PesananService {
    private final UserService userService;
    private final MitraService mitraService;
    private final PesananRepository pesananRepository;

    public PesananService(UserService userService, MitraService mitraService, PesananRepository pesananRepository){
        this.userService = userService;
        this.mitraService = mitraService;
        this.pesananRepository = pesananRepository;
    }

    public Pesanan tambahPesanan(Pesanan pesanan){
        return pesananRepository.save(pesanan);
    }

    public List<PesananResponse> getAllPesanan(){
        return pesananRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private Pesanan getPesananEntityById(Long id){
        return pesananRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFound("Pesanan tidak ditemukan!"));
    }

    public PesananResponse getPesananById(Long id){
        return mapToResponse(getPesananEntityById(id));
    }

    public List<PesananResponse> getPesananByStatus(StatusPesanan status){
        List<Pesanan> pesananList = pesananRepository.findPesananByStatus(status);

        if(pesananList.isEmpty()){
            throw new ResourceNotFound("Pesanan tidak ditemukan!");
        }

        return pesananList.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public long hitungTotalPesanan(){
        return pesananRepository.count();
    }

    public Pesanan updatePesananService(Long id, Pesanan pesananBaru){
        Pesanan pesanan = pesananRepository.findById(id)
                    .orElseThrow(() ->
                        new ResourceNotFound("Pesanan tidak ditemukan!"));

        pesanan.setKeluhan(pesananBaru.getKeluhan());
        pesanan.setStatus(pesananBaru.getStatus());

        return pesananRepository.save(pesanan);
    }

    public void hapusPesananService(Long id){
        pesananRepository.deleteById(id);
    }

    public boolean userMasihPunyaPesananMenunggu(User user){
        return user.getPesananList().stream()
               .anyMatch(u -> u.getStatus() == StatusPesanan.MENUNGGU);
    }

    public Pesanan tambahPesananKeMitra(PesananRequest request, Long UserId){
        User user = userService.getUserById(UserId);

        if(userMasihPunyaPesananMenunggu(user)){
            throw new BadRequestException("Masih ada pesanan yang menunggu!");
        }

        Pesanan pesanan = new Pesanan();

        pesanan.setKeluhan(request.getKeluhan());
        pesanan.setUser(user);

        pesanan.setMitra(null);
        pesanan.setStatus(StatusPesanan.MENUNGGU);
        pesanan.setKota(request.getKota());

        user.getPesananList().add(pesanan);
        return pesananRepository.save(pesanan);
    }

    public Pesanan terimaPesanan(Long pesananId, Long mitraId){
        Pesanan pesanan = getPesananEntityById(pesananId);

        Mitra mitra = mitraService.getMitraById(mitraId);

        if(!mitra.getKota().equals(pesanan.getKota())){
            throw new ResourceNotFound("Pesanan bukan di wilayah mitra!");
        }

        if(pesanan.getStatus() != StatusPesanan.MENUNGGU){
            throw new BadRequestException("Pesanan tidak bisa diterima!");
        }

        pesanan.setMitra(mitra);
        pesanan.setStatus(StatusPesanan.DITERIMA);
        mitra.getPesanan().add(pesanan);

        return pesananRepository.save(pesanan);
    }

    public PesananResponse mapToResponse(Pesanan pesanan){
        PesananResponse response = new PesananResponse();

        response.setId(pesanan.getId());
        response.setKeluhan(pesanan.getKeluhan());
        response.setStatus(pesanan.getStatus());
        response.setKota(pesanan.getKota());

        response.setNamaUser(pesanan.getUser() != null ? pesanan.getUser().getNamaUser() : null);
        response.setNamaMitra(pesanan.getMitra() != null ? pesanan.getMitra().getNamaMitra() : null);

        return response;
    }
}