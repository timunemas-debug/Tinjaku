package com.tinjaku.mapper;

import org.springframework.stereotype.Component;

import com.tinjaku.dto.request.AlamatRequest;
import com.tinjaku.dto.response.AlamatResponse;
import com.tinjaku.model.Alamat;

@Component
public class AlamatMapper {

    public Alamat toEntity(AlamatRequest request){
        Alamat alamat = new Alamat();

        alamat.setLabel(request.getLabel());
        alamat.setJalan(request.getJalan());
        alamat.setKelurahan(request.getKelurahan());
        alamat.setKecamatan(request.getKecamatan());
        alamat.setKota(request.getKota());
        alamat.setProvinsi(request.getProvinsi());

        return alamat;
    }

    public AlamatResponse toResponse(Alamat alamat){
        return new AlamatResponse(alamat.getLabel(),
                                  alamat.getJalan(),
                                  alamat.getKelurahan(),
                                  alamat.getKecamatan(),
                                  alamat.getKota(),
                                  alamat.getProvinsi());
    }
}
