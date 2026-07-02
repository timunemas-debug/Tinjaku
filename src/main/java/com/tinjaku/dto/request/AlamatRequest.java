package com.tinjaku.dto.request;

import lombok.*;
import com.tinjaku.model.Label;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.tinjaku.model.Kota;

@Getter
@Setter
public class AlamatRequest {

    @NotNull(message = "Label wajib di isi!")
    private Label label;

    @NotBlank(message = "Jalan wajib di isi!")
    private String jalan;

    @NotBlank(message = "Kelurahan wajib di isi!")
    private String kelurahan;

    @NotBlank(message = "Kecamatan wajib di isi!")
    private String kecamatan;

    @NotNull(message = "Kota wajib di isi!")
    private Kota kota;

    @NotBlank(message = "Provinsi wajib di isi!")
    private String provinsi;
}
