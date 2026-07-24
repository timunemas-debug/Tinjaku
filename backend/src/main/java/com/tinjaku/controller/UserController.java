package com.tinjaku.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinjaku.service.UserService;

import jakarta.validation.Valid;

import com.tinjaku.dto.request.OnlineRequest;
import com.tinjaku.dto.request.UserRequest;
import com.tinjaku.dto.response.OnlineResponse;
import com.tinjaku.dto.response.UserResponse;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public UserResponse tambahUser(@Valid @RequestBody UserRequest request){
        return userService.tambahUser(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{userId}/online")
    public OnlineResponse onlineUser(@PathVariable Long userId, @RequestBody OnlineRequest request){
        return userService.getUserOnline(userId, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserResponse> getAllUser(){
        return userService.getAllUser();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public UserResponse userById(@PathVariable Long userId){
        return userService.getUserResponseById(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId){
        userService.deleteUserById(userId);
    }
}