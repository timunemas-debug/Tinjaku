package com.tinjaku.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class PesananRequest {

    @NotNull(message = "User Id tidak boleh kosong")
    private long userId;

    @NotBlank(message = "Alamat tidak boleh kosong")
    private String alamat;

    @NotBlank(message = "Keluhan tidak boleh kosong")
    private String keluhan;
}
