package com.tinjaku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tinjaku.exception.BadRequestException;
import com.tinjaku.model.User;
import com.tinjaku.repository.UserRepository;
import com.tinjaku.dto.request.LoginRequest;
import com.tinjaku.dto.request.OnlineRequest;
import com.tinjaku.dto.request.UpdateUserProfileRequest;
import com.tinjaku.dto.request.UserRequest;
import com.tinjaku.dto.response.LoginResponse;
import com.tinjaku.dto.response.OnlineResponse;
import com.tinjaku.dto.response.UpdateUserProfileResponse;
import com.tinjaku.dto.response.UserResponse;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.mapper.UpdateUserProfileMapper;
import com.tinjaku.mapper.UserMapper;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private UpdateUserProfileMapper updateUserProfileMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper, UpdateUserProfileMapper updateUserProfileMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.updateUserProfileMapper = updateUserProfileMapper;
    }

    public UserResponse tambahUser(UserRequest request){
        
        if(userRepository.existsByEmailIgnoreCase(request.getEmail())){
            throw new BadRequestException("Email sudah terdaftar");
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
    
    public UpdateUserProfileResponse updateProfile(Long userId, UpdateUserProfileRequest request){
        User user = getUserById(userId);

        if(!user.getEmail().equalsIgnoreCase(request.getEmail())
                && userRepository.existsByEmailIgnoreCase(request.getEmail())){
            throw new BadRequestException("Email sudah terdaftar!");
            }

        user.setNamaDepan(request.getNamaDepan());
        user.setNamaBelakang(request.getNamaBelakang());
        user.setEmail(request.getEmail());

        userRepository.save(user);
        
        return updateUserProfileMapper.mapToResponse(user);
    }
}