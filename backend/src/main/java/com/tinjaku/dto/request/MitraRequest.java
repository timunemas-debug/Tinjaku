package com.tinjaku.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class MitraRequest {

    @NotBlank(message = "Nama mitra tidak boleh kosong!")
    private String namaMitra;
}