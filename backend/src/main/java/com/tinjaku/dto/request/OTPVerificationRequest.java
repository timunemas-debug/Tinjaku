package com.tinjaku.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OTPVerificationRequest {
    
    @NotBlank(message = "Wajib memasukan email anda!")
    private String email;

    @NotBlank(message = "Masukan code OTP anda!")
    private String otp;
}