package com.tinjaku.mapper;

import org.springframework.stereotype.Component;

import com.tinjaku.dto.request.UserRequest;
import com.tinjaku.dto.response.UserResponse;
import com.tinjaku.dto.response.AlamatResponse;
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

        user.setNamaUser(request.getNamaUser());
        user.setKota(request.getKota());

        return user;
    }

    public UserResponse toResponse(User user){
        List<AlamatResponse> alamatResponse = user.getAlamatList()
                .stream()
                .map(alamatMapper::toResponse)
                .toList();

        return new UserResponse(user.getNamaUser(),
                                alamatResponse,
                                user.getKota());
    }
}