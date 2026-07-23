package com.tinjaku.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tinjaku.dto.request.LoginRequest;
import com.tinjaku.dto.request.RegisterRequest;
import com.tinjaku.dto.response.LoginResponse;
import com.tinjaku.dto.response.RegisterResponse;
import com.tinjaku.exception.BadRequestException;
import com.tinjaku.mapper.UserMapper;
import com.tinjaku.model.User;
import com.tinjaku.repository.UserRepository;
import com.tinjaku.security.CustomUserDetails;
import com.tinjaku.security.CustomUserDetailsService;
import com.tinjaku.security.JwtService;

@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService, JwtService jwtService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtService = jwtService;
    }

    public RegisterResponse register(RegisterRequest request){
        
        if(userRepository.existsByEmailIgnoreCase(request.getEmail())){
            throw new BadRequestException("Email anda sudah terdaftar!");
        }

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.toRegisterResponse(userRepository.save(user));
    }

    public LoginResponse login(LoginRequest request){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(request.getEmail());
        String jwt = jwtService.generateToken(userDetails);

        return new LoginResponse(jwt);
    }
}