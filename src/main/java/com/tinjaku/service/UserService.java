package com.tinjaku.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tinjaku.exception.BadRequestException;
import com.tinjaku.model.User;
import com.tinjaku.repository.UserRepository;
import com.tinjaku.dto.request.UserRequest;
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

    public User tambahUser(UserRequest request){
        if(userRepository.existsByNamaUserIgnoreCase(request.getNamaUser())){
            throw new BadRequestException("User sudah terdaftar!");
        }

        User user = new User();

        user.setNamaUser(request.getNamaUser());
        user.setPesananList(new ArrayList<>());
        user.setAlamatList(new ArrayList<>());

        return userRepository.save(user);
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
}