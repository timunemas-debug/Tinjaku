package com.tinjaku.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tinjaku.dto.request.LoginRequest;
import com.tinjaku.dto.request.RegisterRequest;
import com.tinjaku.dto.response.LoginResponse;
import com.tinjaku.dto.response.RegisterResponse;
import com.tinjaku.exception.BadRequestException;
import com.tinjaku.mapper.UserMapper;
import com.tinjaku.model.StatusOnOff;
import com.tinjaku.model.User;
import com.tinjaku.repository.UserRepository;
import com.tinjaku.security.CustomUserDetails;
import com.tinjaku.security.CustomUserDetailsService;
import com.tinjaku.security.JwtService;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, UserMapper userMapper, AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    private void setOnline(User user){
        user.setStatusOnOff(StatusOnOff.ONLINE);
        userRepository.save(user);
    }

    private void setOffline(User user){
        user.setStatusOnOff(StatusOnOff.OFFLINE);
        userRepository.save(user);
    }

    public RegisterResponse register(RegisterRequest request){

        if(userRepository.existsByEmailIgnoreCase(request.getEmail())){
            throw new BadRequestException("Email sudah terdaftar!");
        }

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.toRegisterResponse(userRepository.save(user));
    }

    public LoginResponse login(LoginRequest request){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(request.getEmail());

        setOnline(userDetails.getUser());

        String jwt = jwtService.generateToken(userDetails);

        return new LoginResponse(jwt);
    }

    public void logOut(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        setOffline(userDetails.getUser());
    }
}