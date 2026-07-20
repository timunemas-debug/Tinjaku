package com.tinjaku.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    
    @NotBlank(message = "Email wajib di isi!")
    private String email;

    @NotBlank(message = "Password wajib di isi!")
    private String password;
}