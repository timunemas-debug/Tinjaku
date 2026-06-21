package com.tinjaku.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class MitraRequest {

    @NotBlank(message = "Nama mitra tidak boleh kosong!")
    private String namaMitra;

    @NotBlank(message = "Alamat mitra tidak boleh kosong!")
    private String alamatMitra;

    @NotBlank(message = "Kota mitra tidak boleh kosong!")
    private String kota;
}
