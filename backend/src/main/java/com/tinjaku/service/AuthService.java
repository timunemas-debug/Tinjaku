package com.tinjaku.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tinjaku.dto.request.RegisterRequest;
import com.tinjaku.dto.response.RegisterResponse;
import com.tinjaku.exception.BadRequestException;
import com.tinjaku.mapper.UserMapper;
import com.tinjaku.model.Role;
import com.tinjaku.model.User;
import com.tinjaku.repository.UserRepository;

@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public RegisterResponse register(RegisterRequest request){
        
        if(userRepository.existsByEmailIgnoreCase(request.getEmail())){
            throw new BadRequestException("Email anda sudah terdaftar!");
        }

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER);

        return userMapper.toRegisterResponse(userRepository.save(user));
    }
}