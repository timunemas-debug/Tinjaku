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
import com.tinjaku.model.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public User tambahUser(@RequestBody User user){
        return userService.tambahUser(user);
    }

    @PostMapping("/{userId}/pesanan")
    public User tambahPesanan(@PathVariable Long userId,
                              @RequestBody Pesanan pesanan){
        return userService.tambahPesananUser(userId, pesanan);
    }

    @GetMapping
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("/{userId}")
    public User userById(@PathVariable Long userId){
        return userService.getUserById(userId);
    }

    @DeleteMapping("/{userId}")
    public User deleteUserById(@PathVariable Long userId){
        return userService.deleteUserById(userId);
    }
}