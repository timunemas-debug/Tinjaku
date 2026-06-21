package com.tinjaku.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class PesananRequest {

    @NotBlank(message = "Alamat tidak boleh kosong")
    private String alamat;

    @NotBlank(message = "Keluhan tidak boleh kosong")
    private String keluhan;
}
