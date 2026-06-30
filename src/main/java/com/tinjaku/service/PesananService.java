package com.tinjaku.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tinjaku.exception.BadRequestException;
import com.tinjaku.dto.request.PesananRequest;
import com.tinjaku.dto.response.PesananResponse;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.mapper.PesananMapper;
import com.tinjaku.model.*;
import com.tinjaku.repository.PesananRepository;

@Service
public class PesananService {
    private final UserService userService;
    private final MitraService mitraService;
    private final PesananRepository pesananRepository;
    private final PesananMapper pesananMapper;

    public PesananService(UserService userService, MitraService mitraService, PesananRepository pesananRepository, PesananMapper pesananMapper){
        this.userService = userService;
        this.mitraService = mitraService;
        this.pesananRepository = pesananRepository;
        this.pesananMapper = pesananMapper;
    }

    public Pesanan tambahPesanan(Pesanan pesanan){
        return pesananRepository.save(pesanan);
    }

    public List<PesananResponse> getAllPesanan(){
        return pesananRepository.findAll()
                .stream()
                .map(pesananMapper::mapToResponse)
                .toList();
    }

    private Pesanan getPesananEntityById(Long id){
        return pesananRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFound("Pesanan tidak ditemukan!"));
    }

    public PesananResponse getPesananById(Long id){
        return pesananMapper.mapToResponse(getPesananEntityById(id));
    }

    public List<PesananResponse> getPesananByStatus(StatusPesanan status){
        List<Pesanan> pesananList = pesananRepository.findPesananByStatus(status);

        if(pesananList.isEmpty()){
            throw new ResourceNotFound("Pesanan tidak ditemukan!");
        }

        return pesananList.stream()
                .map(pesananMapper::mapToResponse)
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

    public Pesanan selesaiPesanan(Long pesananId, Long mitraId){
        Pesanan pesanan = getPesananEntityById(pesananId);

        Mitra mitra = mitraService.getMitraById(mitraId);

        if(pesanan.getStatus() != StatusPesanan.DITERIMA){
            throw new BadRequestException("Pesanan tidak bisa diselesaikan!");
        }

        pesanan.setMitra(mitra);
        pesanan.setStatus(StatusPesanan.SELESAI);
        mitra.getPesanan().add(pesanan);

        return pesananRepository.save(pesanan);
    }
}