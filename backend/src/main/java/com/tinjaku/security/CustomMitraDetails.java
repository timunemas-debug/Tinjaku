package com.tinjaku.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tinjaku.model.Mitra;

public class CustomMitraDetails implements UserDetails{
       
    private final Mitra mitra;

    public CustomMitraDetails(Mitra mitra){
        this.mitra = mitra;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(mitra.getRole().name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword(){
        return mitra.getPassword();
    }

    @Override
    public String getUsername(){
        return mitra.getEmail();
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }

    public Mitra getMitra() {
        return mitra;
    }
}