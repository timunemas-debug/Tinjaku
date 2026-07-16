package com.tinjaku.mapper;

import org.springframework.stereotype.Component;

import com.tinjaku.dto.request.MitraRequest;
import com.tinjaku.dto.response.MitraResponse;
import com.tinjaku.dto.response.OnlineResponse;
import com.tinjaku.dto.response.AlamatMitraResponse;
import java.util.List;
import com.tinjaku.model.Mitra;

@Component
public class MitraMapper {
    
    private final AlamatMitraMapper alamatMitraMapper;
    
    public MitraMapper(AlamatMitraMapper alamatMitraMapper){
        this.alamatMitraMapper = alamatMitraMapper;
    }

    public Mitra toEntity(MitraRequest request){
        Mitra mitra = new Mitra();

        mitra.setNamaMitra(request.getNamaMitra());
        mitra.setEmail(request.getEmail());

        return mitra;
    }

    public MitraResponse toResponse(Mitra mitra, Double ratingMitra, Long totalRating){
        List<AlamatMitraResponse> alamatResponse = mitra.getAlamatList()
                .stream()
                .map(alamatMitraMapper::toResponse)
                .toList();

        return new MitraResponse(mitra.getNamaMitra(),
                                 ratingMitra,
                                 totalRating,
                                 alamatResponse);
    }

    public OnlineResponse toOnlineResponse(Mitra mitra){
        return new OnlineResponse(mitra.getStatusOnOff());
    }
}