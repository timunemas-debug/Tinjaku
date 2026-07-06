package com.tinjaku.mapper;

import org.springframework.stereotype.Component;

import com.tinjaku.dto.request.MitraRequest;
import com.tinjaku.dto.response.MitraResponse;
import com.tinjaku.model.Mitra;

@Component
public class MitraMapper {
    
    public Mitra toEntity(MitraRequest request){
        Mitra mitra = new Mitra();

        mitra.setKota(request.getKota());
        mitra.setNamaMitra(request.getNamaMitra());

        return mitra;
    }

    public MitraResponse toResponse(Mitra mitra, Double ratingMitra, Long totalRating){
        return new MitraResponse(mitra.getNamaMitra(),
                                 mitra.getKota(),
                                 ratingMitra,
                                 totalRating);
    }
}