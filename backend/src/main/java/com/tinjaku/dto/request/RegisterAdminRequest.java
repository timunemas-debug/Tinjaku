package com.tinjaku.dto.request;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class RegisterAdminRequest {
    
    private String email;
    private String password;
}