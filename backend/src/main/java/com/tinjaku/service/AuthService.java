package com.tinjaku.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tinjaku.dto.request.LoginRequest;
import com.tinjaku.dto.request.RegisterMitraRequest;
import com.tinjaku.dto.request.RegisterRequest;
import com.tinjaku.dto.response.LoginResponse;
import com.tinjaku.dto.response.RegisterMitraResponse;
import com.tinjaku.dto.response.RegisterResponse;
import com.tinjaku.exception.BadRequestException;
import com.tinjaku.mapper.MitraMapper;
import com.tinjaku.mapper.UserMapper;
import com.tinjaku.model.Mitra;
import com.tinjaku.model.Role;
import com.tinjaku.model.StatusOnOff;
import com.tinjaku.model.User;
import com.tinjaku.repository.MitraRepository;
import com.tinjaku.repository.UserRepository;
import com.tinjaku.security.CustomMitraDetails;
import com.tinjaku.security.CustomUserDetails;
import com.tinjaku.security.CustomUserDetailsService;
import com.tinjaku.security.JwtService;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MitraRepository mitraRepository;
    private final MitraMapper mitraMapper;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, UserMapper userMapper, MitraRepository mitraRepository, MitraMapper mitraMapper,
                       AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService,
                       PasswordEncoder passwordEncoder, JwtService jwtService) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.mitraRepository = mitraRepository;
        this.mitraMapper = mitraMapper;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    private void setOnline(User user) {
        user.setStatusOnOff(StatusOnOff.ONLINE);
        userRepository.save(user);
    }

    private void setOffline(User user) {
        user.setStatusOnOff(StatusOnOff.OFFLINE);
        userRepository.save(user);
    }

    private void setOffline(Mitra mitra){
        mitra.setStatusOnOff(StatusOnOff.OFFLINE);
        mitraRepository.save(mitra);
    }

    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new BadRequestException("Email sudah terdaftar!");
        }

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.toRegisterResponse(userRepository.save(user));
    }

    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getEmail());

        if (userDetails instanceof CustomUserDetails customUserDetails) {
            setOnline(customUserDetails.getUser());
        }

        String jwt = jwtService.generateToken(userDetails);

        return new LoginResponse(jwt);
    }

    public void logOut() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (userDetails instanceof CustomUserDetails customUserDetails) {
            setOffline(customUserDetails.getUser());
        }

        if (userDetails instanceof CustomMitraDetails customMitraDetails) {
            setOffline(customMitraDetails.getMitra());
        }
    }

    public RegisterMitraResponse registerMitra(RegisterMitraRequest request){
        
        if(mitraRepository.existsByEmailIgnoreCase(request.getEmail())){
            throw new BadRequestException("Email sudah terdaftar!");
        }

        Mitra mitra = mitraMapper.toEntity(request);

        mitra.setPassword(passwordEncoder.encode(request.getPassword()));

        return mitraMapper.toRegisterMitraResponse(mitraRepository.save(mitra));
    }
}