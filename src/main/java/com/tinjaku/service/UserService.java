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

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User tambahUser(UserRequest request){
        if(userRepository.existsByNamaUserIgnoreCase(request.getNamaUser())){
            throw new BadRequestException("User sudah terdaftar!");
        }

        User user = new User();

        user.setNamaUser(request.getNamaUser());
        user.setAlamatLengkap(request.getAlamatUser());
        user.setKota(request.getKota());
        user.setPesananList(new ArrayList<>());

        return userRepository.save(user);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() ->
                    new ResourceNotFound("User tidak ditemukan!"));
    }

    public UserResponse getUserResponseById(Long userId){
        User user = getUserById(userId);

        return new UserResponse(user.getNamaUser(), user.getAlamatLengkap(), user.getKota());
    }

    public void deleteUserById(Long userId){
        if(!userRepository.existsById(userId)){
            throw new ResourceNotFound("User tidak ditemukan!");
        }
        userRepository.deleteById(userId);
    }
}