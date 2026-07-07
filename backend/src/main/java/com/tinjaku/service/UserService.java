package com.tinjaku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tinjaku.exception.BadRequestException;
import com.tinjaku.model.User;
import com.tinjaku.repository.UserRepository;
import com.tinjaku.dto.request.OnlineRequest;
import com.tinjaku.dto.request.UserRequest;
import com.tinjaku.dto.response.OnlineResponse;
import com.tinjaku.dto.response.UserResponse;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.mapper.UserMapper;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponse tambahUser(UserRequest request){
        if(userRepository.existsByNamaDepanIgnoreCaseAndNamaBelakangIgnoreCase(request.getNamaDepan(), request.getNamaBelakang())){
            throw new BadRequestException("User sudah terdaftar!");
        }

        User user = userMapper.toEntity(request);

        return userMapper.toResponse(userRepository.save(user));
    }

    public List<UserResponse> getAllUser(){
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() ->
                    new ResourceNotFound("User tidak ditemukan!"));
    }

    public UserResponse getUserResponseById(Long userId){
        User user = getUserById(userId);

        return userMapper.toResponse(user);
    }

    public void deleteUserById(Long userId){
        if(!userRepository.existsById(userId)){
            throw new ResourceNotFound("User tidak ditemukan!");
        }
        userRepository.deleteById(userId);
    }

    public OnlineResponse getUserOnline(Long userId, OnlineRequest request){
        User user = getUserById(userId);

        user.setStatusOnOff(request.getStatusOnOff());
        
        User savedUser = userRepository.save(user);

        return userMapper.toOnlineResponse(savedUser);
    }
}