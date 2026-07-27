package com.tinjaku.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinjaku.dto.request.LoginRequest;
import com.tinjaku.dto.request.RegisterRequest;
import com.tinjaku.dto.response.LoginResponse;
import com.tinjaku.dto.response.RegisterResponse;
import com.tinjaku.service.AuthService;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/regist")
    public RegisterResponse regist(@RequestBody @Valid RegisterRequest request){
        System.out.println("postmapping masukkk");
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request){
        return authService.login(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(){
        authService.logOut();
        return ResponseEntity.ok().build();
    }
}