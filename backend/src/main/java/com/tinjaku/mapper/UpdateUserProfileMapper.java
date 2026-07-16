package com.tinjaku.mapper;

import org.springframework.stereotype.Component;

import com.tinjaku.dto.response.UpdateUserProfileResponse;
import com.tinjaku.model.User;

@Component
public class UpdateUserProfileMapper {

    public UpdateUserProfileResponse mapToResponse(User user){

        UpdateUserProfileResponse response = new UpdateUserProfileResponse();

        response.setNamaDepan(user.getNamaDepan());
        response.setNamaBelakang(user.getNamaBelakang());
        response.setEmail(user.getEmail());

        return response;
    }
}