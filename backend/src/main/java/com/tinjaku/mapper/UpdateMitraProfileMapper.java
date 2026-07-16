package com.tinjaku.mapper;

import org.springframework.stereotype.Component;

import com.tinjaku.dto.response.UpdateMitraProfileResponse;
import com.tinjaku.model.Mitra;

@Component
public class UpdateMitraProfileMapper {
    
    public UpdateMitraProfileResponse mapToResponse(Mitra mitra){

        UpdateMitraProfileResponse response = new UpdateMitraProfileResponse();
        response.setNamaMitra(mitra.getNamaMitra());
        response.setEmail(mitra.getEmail());

        return response;
    }
}