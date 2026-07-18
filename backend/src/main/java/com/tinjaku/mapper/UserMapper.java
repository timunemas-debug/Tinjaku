package com.tinjaku.mapper;

import org.springframework.stereotype.Component;

import com.tinjaku.dto.request.RegisterRequest;
import com.tinjaku.dto.request.UserRequest;
import com.tinjaku.dto.response.UserResponse;
import com.tinjaku.dto.response.AlamatResponse;
import com.tinjaku.dto.response.OnlineResponse;
import com.tinjaku.dto.response.RegisterResponse;

import java.util.List;

import com.tinjaku.model.User;
@Component
public class UserMapper {

    private final AlamatMapper alamatMapper;

    public UserMapper(AlamatMapper alamatMapper){
        this.alamatMapper = alamatMapper;
    }

    public User toEntity(UserRequest request){
        User user = new User();

        user.setNamaDepan(request.getNamaDepan());
        user.setNamaBelakang(request.getNamaBelakang());
        user.setEmail(request.getEmail());

        return user;
    }

    public UserResponse toResponse(User user){
        List<AlamatResponse> alamatResponse = user.getAlamatList()
                .stream()
                .map(alamatMapper::toResponse)
                .toList();
        
        return new UserResponse(user.getNamaDepan(),
                                user.getNamaBelakang(),
                                user.getNamaLengkap(),
                                alamatResponse);
    }

    public OnlineResponse toOnlineResponse(User user){
        return new OnlineResponse(user.getStatusOnOff());
    }
    
    public User toEntity(RegisterRequest request){
        User user = new User();

        user.setNamaDepan(request.getNamaDepan());
        user.setNamaBelakang(request.getNamaBelakang());
        user.setEmail(request.getEmail());

        return user;
    }

    public RegisterResponse toRegisterResponse(User user){
        return new RegisterResponse(user.getUserId(),
                                    user.getNamaDepan(),
                                    user.getNamaBelakang(),
                                    user.getEmail());
    }
}