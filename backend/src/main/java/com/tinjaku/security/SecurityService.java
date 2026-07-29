package com.tinjaku.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    
    public CustomUserDetails getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (CustomUserDetails) authentication.getPrincipal();
    }

    public Long getCurrentUserId(){
        return getCurrentUser().getUserId();
    }

    public String getCurrentEmail(){
        return getCurrentUser().getUsername();
    }

    public String currentRole(){
        return getCurrentUser()
                .getAuthorities()
                .iterator()
                .next()
                .getAuthority();
    }
}