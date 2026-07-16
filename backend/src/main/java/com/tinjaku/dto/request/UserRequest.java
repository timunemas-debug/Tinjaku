package com.tinjaku.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    
    @NotBlank(message = "Nama user tidak boleh kosong!")
    private String namaDepan;

    @NotBlank(message = "Nama user tidak boleh kosong!")
    private String namaBelakang;

    @NotBlank(message = "Email user tidak boleh kosong")
    @Email
    private String email;
}