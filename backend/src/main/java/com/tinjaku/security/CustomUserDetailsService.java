package com.tinjaku.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.tinjaku.exception.UsernameNotFoundException;
import com.tinjaku.model.Mitra;
import com.tinjaku.model.User;
import com.tinjaku.repository.MitraRepository;
import com.tinjaku.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    
    private final UserRepository userRepository;
    private final MitraRepository mitraRepository;

    public CustomUserDetailsService(UserRepository userRepository, MitraRepository mitraRepository){
        this.userRepository = userRepository;
        this.mitraRepository = mitraRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email){
        
        Optional<User> user = userRepository.findByEmailIgnoreCase(email);

        if (user.isPresent()) {
            return new CustomUserDetails(user.get());
        }

        Optional<Mitra> mitra = mitraRepository.findByEmailIgnoreCase(email);
        
        if (mitra.isPresent()) {
            return new CustomMitraDetails(mitra.get());
        }

        throw new UsernameNotFoundException("User tidak ditemukan!");
    }
}