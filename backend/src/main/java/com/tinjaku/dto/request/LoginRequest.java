package com.tinjaku.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Email tidak boleh kosong!")
    private String email;

    @NotBlank(message = "Password tidak boleh kosong!")
    private String password;
}