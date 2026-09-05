package com.tinjaku.security;

import java.util.Collection;
import java.util.Collections;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tinjaku.model.Admin;
import com.tinjaku.model.Role;

public class CustomAdminDetails implements UserDetails{
    
    private final Admin admin;

    public CustomAdminDetails(Admin admin){
        this.admin = admin;
    }

    @Override 
    public Collection<? extends GrantedAuthority> getAuthorities(){
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(admin.getRole().name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword(){
        return admin.getPassword();
    }

    @Override 
    public String getUsername(){
        return admin.getEmail();
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

    public Role getRole(){
        return admin.getRole();
    }

    public Long getAdminID(){
        return admin.getAdminId();
    }

    public Admin getAdmin(){
        return admin;
    }
}
