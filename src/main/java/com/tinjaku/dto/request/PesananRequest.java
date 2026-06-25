package com.tinjaku.dto.request;

import com.tinjaku.model.Kota;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class PesananRequest {

    @NotBlank(message = "Alamat tidak boleh kosong")
    private String alamat;

    @NotBlank(message = "Keluhan tidak boleh kosong")
    private String keluhan;

    @NotNull(message = "Kota tidak boleh kosong")
    private Kota kota;
}