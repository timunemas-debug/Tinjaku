package com.tinjaku.mapper;

import org.springframework.stereotype.Component;

import com.tinjaku.dto.request.MitraRequest;
import com.tinjaku.dto.response.MitraResponse;
import com.tinjaku.dto.response.OnlineResponse;
import com.tinjaku.dto.response.AlamatResponse;
import java.util.List;
import com.tinjaku.model.Mitra;

@Component
public class MitraMapper {
    
    private final AlamatMapper alamatMapper;
    
    public MitraMapper(AlamatMapper alamatMapper){
        this.alamatMapper = alamatMapper;
    }

    public Mitra toEntity(MitraRequest request){
        Mitra mitra = new Mitra();

        mitra.setNamaMitra(request.getNamaMitra());

        return mitra;
    }

    public MitraResponse toResponse(Mitra mitra, Double ratingMitra, Long totalRating){
        List<AlamatResponse> alamatResponse = mitra.getAlamatList()
                .stream()
                .map(alamatMapper::toResponse)
                .toList();

        return new MitraResponse(mitra.getNamaMitra(),
                                 alamatResponse,
                                 ratingMitra,
                                 totalRating);
    }

    public OnlineResponse toOnlineResponse(Mitra mitra){
        return new OnlineResponse(mitra.getStatusOnOff());
    }
}