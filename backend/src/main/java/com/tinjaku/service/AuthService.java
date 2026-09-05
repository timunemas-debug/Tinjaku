package com.tinjaku.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tinjaku.dto.request.LoginRequest;
import com.tinjaku.dto.request.RegisterAdminRequest;
import com.tinjaku.dto.request.RegisterMitraRequest;
import com.tinjaku.dto.request.RegisterRequest;
import com.tinjaku.dto.response.LoginResponse;
import com.tinjaku.dto.response.RegisterAdminResponse;
import com.tinjaku.dto.response.RegisterMitraResponse;
import com.tinjaku.dto.response.RegisterResponse;
import com.tinjaku.exception.BadRequestException;
import com.tinjaku.mapper.AdminMapper;
import com.tinjaku.mapper.MitraMapper;
import com.tinjaku.mapper.UserMapper;
import com.tinjaku.model.Admin;
import com.tinjaku.model.Mitra;
import com.tinjaku.model.StatusOnOff;
import com.tinjaku.model.User;
import com.tinjaku.repository.AdminRepository;
import com.tinjaku.repository.MitraRepository;
import com.tinjaku.repository.UserRepository;
import com.tinjaku.security.CustomAdminDetails;
import com.tinjaku.security.CustomMitraDetails;
import com.tinjaku.security.CustomUserDetails;
import com.tinjaku.security.JwtService;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MitraRepository mitraRepository;
    private final MitraMapper mitraMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;

    public AuthService(UserRepository userRepository, UserMapper userMapper, MitraRepository mitraRepository, MitraMapper mitraMapper,
                       AuthenticationManager authenticationManager,
                       PasswordEncoder passwordEncoder, JwtService jwtService, AdminRepository adminRepository, AdminMapper adminMapper) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.mitraRepository = mitraRepository;
        this.mitraMapper = mitraMapper;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
    }

    private void setOnline(User user) {
        user.setStatusOnOff(StatusOnOff.ONLINE);
        userRepository.save(user);
    }

    private void setOffline(User user) {
        user.setStatusOnOff(StatusOnOff.OFFLINE);
        userRepository.save(user);
    }

    private void setOfflineMitra(Mitra mitra){
        mitra.setStatusOnOff(StatusOnOff.OFFLINE);
        mitraRepository.save(mitra);
    }

    private void setOnlineMitra(Mitra mitra){
        mitra.setStatusOnOff(StatusOnOff.ONLINE);
        mitraRepository.save(mitra);
    }

    private void setOnlineAdmin(Admin admin){
        admin.setStatus(StatusOnOff.ONLINE);
        adminRepository.save(admin);
    }

    private void setOfflineAdmin(Admin admin){
        admin.setStatus(StatusOnOff.OFFLINE);
        adminRepository.save(admin);
    }

    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new BadRequestException("Email sudah terdaftar!");
        }
        if (mitraRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new BadRequestException("Email sudah terdaftar!");
        }

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.toRegisterResponse(userRepository.save(user));
    }

    public LoginResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (userDetails instanceof CustomUserDetails customUserDetails) {
            setOnline(customUserDetails.getUser());
        }
        if (userDetails instanceof CustomMitraDetails customMitraDetails) {
            setOnlineMitra(customMitraDetails.getMitra());
        }
        if (userDetails instanceof CustomAdminDetails customAdminDetails) {
            setOnlineAdmin(customAdminDetails.getAdmin());
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
            setOfflineMitra(customMitraDetails.getMitra());
        }

        if (userDetails instanceof CustomAdminDetails customAdminDetails) {
            setOfflineAdmin(customAdminDetails.getAdmin());
        }
    }

    public RegisterMitraResponse registerMitra(RegisterMitraRequest request){
        
        if(mitraRepository.existsByEmailIgnoreCase(request.getEmail())){
            throw new BadRequestException("Email sudah terdaftar!");
        }
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new BadRequestException("Email sudah terdaftar!");
        }

        Mitra mitra = mitraMapper.toEntity(request);
        mitra.setPassword(passwordEncoder.encode(request.getPassword()));

        return mitraMapper.toRegisterMitraResponse(mitraRepository.save(mitra));
    }

    public RegisterAdminResponse registerAdmin(RegisterAdminRequest request){

        if (adminRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new BadRequestException("Email sudah terdaftar!");
        }

        Admin admin = adminMapper.toEntity(request);
        admin.setPassword(passwordEncoder.encode(request.getPassword()));

        return adminMapper.toMapResponse(adminRepository.save(admin));
    }
}