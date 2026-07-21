package com.tinjaku.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.tinjaku.exception.BadRequestException;
import com.tinjaku.model.User;
import com.tinjaku.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService{
    
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email){

        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                    new BadRequestException("Email tidak ditemukan!"));

        return new CustomUserDetails(user);
    }
}