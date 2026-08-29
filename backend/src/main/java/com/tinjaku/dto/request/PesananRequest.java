package com.tinjaku.dto.request;


import com.tinjaku.model.Label;
import com.tinjaku.model.UkuranSepticTank;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class PesananRequest {

    private String namaPenerima;

    @NotNull(message = "Alamat tidak boleh kosong!")
    private Long alamatId;

    @NotBlank(message = "Keluhan tidak boleh kosong")
    private String keluhan;

    @NotNull(message = "Label wajib di isi!")
    private Label label;

    @NotNull(message = "Ukuran septic tank tidak boleh kosong!")
    private UkuranSepticTank ukuranSepticTank;

}