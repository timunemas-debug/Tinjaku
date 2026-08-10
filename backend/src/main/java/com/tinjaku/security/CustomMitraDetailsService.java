package com.tinjaku.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.model.Mitra;
import com.tinjaku.repository.MitraRepository;

public class CustomMitraDetailsService implements UserDetailsService{
    
    private final MitraRepository mitraRepository;

    public CustomMitraDetailsService(MitraRepository mitraRepository){
        this.mitraRepository = mitraRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email){

        Mitra mitra = mitraRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResourceNotFound("Email tidak ditemukan!"));

        return new CustomMitraDetails(mitra);
    }
}