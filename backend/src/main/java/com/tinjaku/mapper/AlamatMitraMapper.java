package com.tinjaku.mapper;

import org.springframework.stereotype.Component;

import com.tinjaku.dto.request.AlamatMitraRequest;
import com.tinjaku.dto.response.AlamatMitraResponse;
import com.tinjaku.model.AlamatMitra;

@Component
public class AlamatMitraMapper {
    public AlamatMitra toEntity(AlamatMitraRequest request){
        AlamatMitra alamat = new AlamatMitra();

        alamat.setLabelMitra(request.getLabelMitra());
        alamat.setJalan(request.getJalan());
        alamat.setKelurahan(request.getKelurahan());
        alamat.setKecamatan(request.getKecamatan());
        alamat.setKota(request.getKota());
        alamat.setProvinsi(request.getProvinsi());

        return alamat;
    }

    public AlamatMitraResponse toResponse(AlamatMitra alamatMitra){
        return new AlamatMitraResponse(alamatMitra.getLabelMitra(),
                                       alamatMitra.getJalan(),
                                       alamatMitra.getKelurahan(),
                                       alamatMitra.getKecamatan(),
                                       alamatMitra.getKota(),
                                       alamatMitra.getProvinsi());
    }
}
