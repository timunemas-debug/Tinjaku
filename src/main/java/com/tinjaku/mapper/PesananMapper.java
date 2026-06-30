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
        response.setKota(pesanan.getKota());

        response.setNamaUser(pesanan.getUser() != null ? pesanan.getUser().getNamaUser() : null);
        response.setNamaMitra(pesanan.getMitra() != null ? pesanan.getMitra().getNamaMitra() : null);

        return response;
    }
}
