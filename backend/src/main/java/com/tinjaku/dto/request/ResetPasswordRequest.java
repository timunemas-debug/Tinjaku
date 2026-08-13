package com.tinjaku.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {

    private String resetToken;

    @NotBlank(message = "Masukan password baru anda!")
    private String newPassword;
}