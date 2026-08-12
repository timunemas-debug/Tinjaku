package com.tinjaku.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {

    @NotBlank(message = "Masukan email anda!")
    private String email;

    @NotBlank(message = "Masukan password baru anda!")
    private String newPassword;
}