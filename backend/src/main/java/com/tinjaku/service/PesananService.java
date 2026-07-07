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
    private final AlamatService alamatService;

    public PesananService(UserService userService, MitraService mitraService, PesananRepository pesananRepository, PesananMapper pesananMapper, AlamatService alamatService){
        this.userService = userService;
        this.mitraService = mitraService;
        this.pesananRepository = pesananRepository;
        this.pesananMapper = pesananMapper;
        this.alamatService = alamatService;
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

    public Pesanan createPesanan(PesananRequest request, Long UserId){
        User user = userService.getUserById(UserId);
        Alamat alamat = alamatService.getAlamatById(request.getAlamatId());

        if(user.getStatusOnOff() != StatusOnOff.ONLINE){
            throw new BadRequestException("User sedang offline!");
        }

        if(!alamat.getUser().getUserId().equals(user.getUserId())){
            throw new BadRequestException("Alamat bukan milik user!");
        }

        if(userMasihPunyaPesananMenunggu(user)){
            throw new BadRequestException("Masih ada pesanan yang menunggu!");
        }

        Pesanan pesanan = new Pesanan();
        
        pesanan.setNamaPenerima(request.getNamaPenerima());
        pesanan.setAlamatLengkap(alamat.getJalan());
        pesanan.setKelurahan(alamat.getKelurahan());
        pesanan.setKecamatan(alamat.getKecamatan());
        pesanan.setKota(alamat.getKota());
        pesanan.setProvinsi(alamat.getProvinsi());
        pesanan.setKeluhan(request.getKeluhan());
        pesanan.setUser(user);

        pesanan.setMitra(null);
        pesanan.setStatus(StatusPesanan.MENUNGGU);
        return pesananRepository.save(pesanan);
    }

    public Pesanan terimaPesanan(Long pesananId, Long mitraId){
        Pesanan pesanan = getPesananEntityById(pesananId);

        Mitra mitra = mitraService.getMitraById(mitraId);

        if(mitra.getStatusOnOff() != StatusOnOff.ONLINE){
            throw new BadRequestException("Anda sedang offline!");
        }
        
        boolean sesuaiWilayah = mitra.getAlamatList().stream()
                    .anyMatch(alamat ->
                        alamat.getKota() == pesanan.getKota() &&
                        alamat.getKecamatan().equals(pesanan.getKecamatan())
                    );

        if(!sesuaiWilayah){
            throw new BadRequestException("Pesanan bukan di wilayah mitra!");
        }

        if(pesanan.getStatus() != StatusPesanan.MENUNGGU){
            throw new BadRequestException("Pesanan tidak bisa diterima!");
        }

        pesanan.setMitra(mitra);
        pesanan.setStatus(StatusPesanan.DITERIMA);

        return pesananRepository.save(pesanan);
    }

    public Pesanan selesaiPesanan(Long pesananId){
        Pesanan pesanan = getPesananEntityById(pesananId);

        if(pesanan.getStatus() != StatusPesanan.DALAM_PERJALANAN){
            throw new BadRequestException("Pesanan tidak bisa diselesaikan!");
        }

        pesanan.setStatus(StatusPesanan.SELESAI);

        return pesananRepository.save(pesanan);
    }

    public Pesanan tolakPesanan(Long pesananId){
        Pesanan pesanan = getPesananEntityById(pesananId);

        if(pesanan.getStatus() != StatusPesanan.MENUNGGU){
            throw new BadRequestException("Pesanan tidak dapat ditolak!");
        }

        pesanan.setStatus(StatusPesanan.DITOLAK);

        return pesananRepository.save(pesanan);
    }

    public Pesanan dalamPerjalanan(Long pesananId){
        Pesanan pesanan = getPesananEntityById(pesananId);

        if(pesanan.getStatus() != StatusPesanan.DITERIMA){
            throw new BadRequestException("Status pesanan tidak valid!");
        }

        pesanan.setStatus(StatusPesanan.DALAM_PERJALANAN);

        return pesananRepository.save(pesanan);
    }

}