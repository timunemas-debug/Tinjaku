package com.tinjaku.mapper;

import org.springframework.stereotype.Component;
import com.tinjaku.dto.response.MitraResponse;
import com.tinjaku.model.Mitra;

@Component
public class MitraMapper {
    
    public MitraResponse mapToResponse(Mitra mitra){
        MitraResponse response = new MitraResponse();

        response.setKota(mitra.getKota());
        response.setNama(mitra.getNamaMitra());

        return response;
    }
}
