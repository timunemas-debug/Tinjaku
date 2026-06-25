package com.tinjaku.dto.request;

import com.tinjaku.model.Kota;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    
    @NotBlank(message = "Nama user tidak boleh kosong!")
    private String namaUser;

    @NotBlank(message = "Alamat user tidak boleh kosong!")
    private String alamatUser;

    @NotNull(message = "Kota user tidak boleh kosong!")
    private Kota kota;
}