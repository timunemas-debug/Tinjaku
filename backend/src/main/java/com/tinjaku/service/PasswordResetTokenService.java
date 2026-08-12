package com.tinjaku.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tinjaku.dto.request.OTPVerificationRequest;
import com.tinjaku.dto.response.ForgotPasswordResponse;
import com.tinjaku.exception.BadRequestException;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.model.PasswordResetToken;
import com.tinjaku.model.User;
import com.tinjaku.repository.PasswordResetTokenRepository;
import com.tinjaku.repository.UserRepository;

@Service
public class PasswordResetTokenService {

    private PasswordResetTokenRepository passwordResetTokenRepository;
    private UserRepository userRepository;
    private EmailService emailService;

    public PasswordResetTokenService(UserRepository userRepository, EmailService emailService, PasswordResetTokenRepository passwordResetTokenRepository){
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    private String generatedOtp(){

        SecureRandom random = new SecureRandom();

        int otp = 100000 + random.nextInt(900000);

        return String.valueOf(otp);
    }

    public ForgotPasswordResponse requestPasswordReset(String email){

        Optional<User> optionalUser = userRepository.findByEmailIgnoreCase(email);
        if (optionalUser.isEmpty()) {
            return new ForgotPasswordResponse("OTP berhasil terkirim! tapi boong");
        }

        User user = optionalUser.get();

        String otp = generatedOtp();

        PasswordResetToken passwordReset = new PasswordResetToken();
        passwordReset.setOtp(otp);
        passwordReset.setCreatedAt(LocalDateTime.now());
        passwordReset.setOtpExpiry(LocalDateTime.now().plusMinutes(10));
        passwordReset.setUsed(false);
        passwordReset.setUser(user);

        passwordResetTokenRepository.save(passwordReset);

        emailService.sendOtpEmail(email, otp);
        
        return new ForgotPasswordResponse("OTP berhasil terkirim!");
    }

    public ForgotPasswordResponse verifyOtp(OTPVerificationRequest request){

        Optional<PasswordResetToken> passwordReset = passwordResetTokenRepository.findTopByUserEmailOrderByCreatedAtDesc(request.getEmail());
        if (passwordReset.isEmpty()) {
            throw new ResourceNotFound("Token tidak ditemukan!");
        }

        PasswordResetToken token = passwordReset.get();

        if (!token.getOtp().equals(request.getOtp())) {
            throw new BadRequestException("Token tidak valid!");
        }

        if (token.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Token tidak valid!");
        }

        if (token.isUsed()) {
            throw new BadRequestException("Token tidak valid!");
        }
        
        return new ForgotPasswordResponse("OTP berhasil diverifikasi!");
    }
}