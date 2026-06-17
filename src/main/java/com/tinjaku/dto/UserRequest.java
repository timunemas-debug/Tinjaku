package com.tinjaku.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    
    @NotBlank(message = "Nama tidak boleh kosong")
    private String nama;
}