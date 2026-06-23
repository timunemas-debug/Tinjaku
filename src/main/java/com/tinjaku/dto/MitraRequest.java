package com.tinjaku.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import com.tinjaku.model.Kota;

@Getter
@Setter
public class MitraRequest {

    @NotBlank(message = "Nama mitra tidak boleh kosong!")
    private String namaMitra;

    @NotBlank(message = "Alamat mitra tidak boleh kosong!")
    private String alamatMitra;

    @NotNull(message = "Kota mitra tidak boleh kosong!")
    private Kota kota;
}