package com.tinjaku.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    
    @NotBlank(message = "Nama depan tidak boleh kosong!")
    private String namaDepan;

    private String namaBelakang;

    @NotBlank(message = "Email tidak boleh kosong!")
    private String email;

    @NotBlank(message = "Password tidak boleh kosong")
    private String password;
}