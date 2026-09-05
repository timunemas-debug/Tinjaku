package com.tinjaku.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.tinjaku.dto.request.RegisterAdminRequest;
import com.tinjaku.dto.response.RegisterAdminResponse;
import com.tinjaku.model.Admin;

@Component
public class AdminMapper {
    
    public Admin toEntity(RegisterAdminRequest request){
        Admin admin = new Admin();
        admin.setEmail(request.getEmail());
        admin.setPassword(request.getPassword());
        admin.setCreateAt(LocalDateTime.now());

        return admin;
    }

    public RegisterAdminResponse toMapResponse(Admin admin){
        return new RegisterAdminResponse(admin.getEmail());
    }
}