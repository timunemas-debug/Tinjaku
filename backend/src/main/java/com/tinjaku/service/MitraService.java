package com.tinjaku.service;

import java.util.List;

import com.tinjaku.dto.request.MitraRequest;
import com.tinjaku.dto.request.OnlineRequest;
import com.tinjaku.dto.response.DashboardResponse;
import com.tinjaku.dto.response.MitraResponse;
import com.tinjaku.dto.response.OnlineResponse;
import com.tinjaku.dto.response.PesananResponse;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.mapper.MitraMapper;
import com.tinjaku.mapper.PesananMapper;
import com.tinjaku.model.Kota;
import com.tinjaku.model.Mitra;
import com.tinjaku.model.StatusPesanan;
import com.tinjaku.repository.MitraRepository;
import com.tinjaku.repository.PesananRepository;
import com.tinjaku.repository.RatingRepository;
import com.tinjaku.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class MitraService {
    private final MitraRepository mitraRepository;
    private final PesananRepository pesananRepository;
    private final RatingRepository ratingRepository;
    private final PesananMapper pesananMapper;
    private final MitraMapper mitraMapper;

    public MitraService(MitraRepository mitraRepository, PesananRepository pesananRepository, RatingRepository ratingRepository ,PesananMapper pesananMapper, MitraMapper mitraMapper){
        this.mitraRepository = mitraRepository;
        this.pesananRepository = pesananRepository;
        this.ratingRepository = ratingRepository;
        this.pesananMapper = pesananMapper;
        this.mitraMapper = mitraMapper;
    }

    public MitraResponse tambahMitra(MitraRequest request){

        if(mitraRepository.existsByNamaMitraIgnoreCase(request.getNamaMitra())){
            throw new BadRequestException("Mitra sudah terdaftar!");
        }

        Mitra mitra = mitraMapper.toEntity(request);
        
        return mitraMapper.toResponse(mitra, null, null);
    }

    public List<MitraResponse> getAllMitra(){
        return mitraRepository.findAll()
                .stream()
                .map(mitra -> {
                    Double ratingMitra = ratingRepository.getAvargeRating(mitra.getMitraId());
                    Long totalRating = ratingRepository.getTotalRating(mitra.getMitraId());
                    return mitraMapper.toResponse(mitra, ratingMitra, totalRating);
                })
                .toList();
    }

    public List<MitraResponse> getMitraByKota(Kota kota){
        return mitraRepository.findByAlamatList_Kota(kota)
                .stream()
                .map(mitra -> {
                    Double ratingMitra = ratingRepository.getAvargeRating(mitra.getMitraId());
                    Long totalRating = ratingRepository.getTotalRating(mitra.getMitraId());
                    return mitraMapper.toResponse(mitra, ratingMitra, totalRating);
                })
                .toList();
    }

    public Mitra getMitraById(Long id){
        return mitraRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFound("Mitra tidak ditemukan!"));
    }

    public MitraResponse getMitraResponseById(Long id){
        Mitra mitra = getMitraById(id);

        Double ratingMitra = ratingRepository.getAvargeRating(id);
        Long totalRating = ratingRepository.getTotalRating(id);

        return mitraMapper.toResponse(mitra, ratingMitra != null ? ratingMitra : 0.0, totalRating);
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
                    
        return mitra.getPesananList()
                .stream()
                .map(pesananMapper::mapToResponse)
                .toList();
    }

    public OnlineResponse getMitraOnline(Long mitraId, OnlineRequest request){

        Mitra mitra = getMitraById(mitraId);

        mitra.setStatusOnOff(request.getStatusOnOff());

        Mitra savedMitra = mitraRepository.save(mitra);

        return mitraMapper.toOnlineResponse(savedMitra);
    }

    public DashboardResponse getDashboard(Long mitraId){
        getMitraById(mitraId);
        
        Long totalPesanan = pesananRepository.countByMitraMitraId(mitraId);
        Long pesananMenunggu = pesananRepository.countByMitraMitraIdAndStatus(mitraId, StatusPesanan.MENUNGGU);
        Long pesananDiTerima = pesananRepository.countByMitraMitraIdAndStatus(mitraId, StatusPesanan.DITERIMA);
        Long pesananDiKerjakan = pesananRepository.countByMitraMitraIdAndStatus(mitraId, StatusPesanan.DIKERJAKAN);
        Long pesananSelesai = pesananRepository.countByMitraMitraIdAndStatus(mitraId, StatusPesanan.SELESAI);

        return new DashboardResponse(totalPesanan,
                                     pesananMenunggu,
                                     pesananDiTerima,
                                     pesananDiKerjakan,
                                     pesananSelesai);
    }
}