package com.tinjaku.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    
    @NotBlank(message = "Nama user tidak boleh kosong!")
    private String namaUser;

    @NotBlank(message = "Alamat user tidak boleh kosong!")
    private String alamatUser;

    @NotBlank(message = "Kota user tidak boleh kosong!")
    private String kota;
}