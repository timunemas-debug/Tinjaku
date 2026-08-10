package com.tinjaku.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterMitraRequest {
    
    @NotBlank(message = "Nama tidak boleh kosong!")
    private String namaMitra;

    @NotBlank(message = "Email wajib di isi!")
    @Email
    private String email;

    @NotBlank(message = "Password wajib di isi!")
    private String password;
}