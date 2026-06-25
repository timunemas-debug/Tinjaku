package com.tinjaku.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinjaku.service.UserService;
import com.tinjaku.service.PesananService;


import jakarta.validation.Valid;

import com.tinjaku.dto.request.PesananRequest;
import com.tinjaku.dto.request.UserRequest;
import com.tinjaku.dto.response.UserResponse;
import com.tinjaku.model.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final PesananService pesananService;

    public UserController(UserService userService, PesananService pesananService){
        this.userService = userService;
        this.pesananService = pesananService;
    }

    @PostMapping
    public User tambahUser(@Valid @RequestBody UserRequest request){
        return userService.tambahUser(request);
    }

    @PostMapping("/{userId}/pesanan")
    public Pesanan tambahPesanan(@PathVariable Long userId,
                              @RequestBody PesananRequest request){
        return pesananService.tambahPesananKeMitra(request, userId);
    }

    @GetMapping
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("/{userId}")
    public UserResponse userById(@PathVariable Long userId){
        return userService.getUserResponseById(userId);
    }

    @DeleteMapping("/{userId}")
    public User deleteUserById(@PathVariable Long userId){
        return userService.deleteUserById(userId);
    }
}