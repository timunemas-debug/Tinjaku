package com.tinjaku.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.tinjaku.dto.request.ForgotPasswordRequest;
import com.tinjaku.dto.request.OTPVerificationRequest;
import com.tinjaku.dto.request.ResetPasswordRequest;
import com.tinjaku.dto.response.ForgotPasswordResponse;
import com.tinjaku.dto.response.VerifyOtpResponse;
import com.tinjaku.service.PasswordResetTokenService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/password")
public class PasswordResetController {
    
    private final PasswordResetTokenService passwordResetTokenService;

    public PasswordResetController(PasswordResetTokenService passwordResetTokenService){
        this.passwordResetTokenService = passwordResetTokenService;
    }

    @PostMapping("/forgot-password")
    public ForgotPasswordResponse requestPasswordReset(@RequestBody ForgotPasswordRequest request){
        return passwordResetTokenService.requestPasswordReset(request.getEmail());
    }

    @PostMapping("/forgot-password/verify")
    public VerifyOtpResponse verifyOtp(@RequestBody OTPVerificationRequest request){
        return passwordResetTokenService.verifyOtp(request);
    }

    @PostMapping("/reset-password")
    public ForgotPasswordResponse resetPassword(@Valid @RequestBody ResetPasswordRequest request){
        return passwordResetTokenService.resetPassword(request);
    }
}