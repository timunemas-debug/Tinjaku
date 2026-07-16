package com.tinjaku.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMitraProfileRequest {
    
    @NotBlank(message = "Nama tidak boleh kosong!")
    private String namaMitra;

    @NotBlank(message = "Enauk tidak boleh kosong!")
    private String email;

}
