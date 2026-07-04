package com.tinjaku.mapper;
import org.springframework.stereotype.Component;

import com.tinjaku.dto.response.PesananResponse;
import com.tinjaku.model.Pesanan;

@Component
public class PesananMapper {
    public PesananResponse mapToResponse(Pesanan pesanan){
        PesananResponse response = new PesananResponse();

        response.setId(pesanan.getId());
        response.setKeluhan(pesanan.getKeluhan());
        response.setStatus(pesanan.getStatus());
        response.setNamaPenerima(pesanan.getNamaPenerima() != null ? pesanan.getNamaPenerima() : pesanan.getUser().getNamaLengkap());
        response.setAlamatLengkap(pesanan.getAlamatLengkap());
        response.setKelurahan(pesanan.getKelurahan());
        response.setKecamatan(pesanan.getKecamatan());
        response.setKota(pesanan.getKota());
        response.setProvinsi(pesanan.getProvinsi());

        response.setNamaLengkap(pesanan.getUser() != null ? pesanan.getUser().getNamaLengkap() : null);
        response.setNamaMitra(pesanan.getMitra() != null ? pesanan.getMitra().getNamaMitra() : null);

        return response;
    }
}