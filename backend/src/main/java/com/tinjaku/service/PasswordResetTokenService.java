package com.tinjaku.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tinjaku.dto.request.OTPVerificationRequest;
import com.tinjaku.dto.request.ResetPasswordRequest;
import com.tinjaku.dto.response.ForgotPasswordResponse;
import com.tinjaku.dto.response.VerifyOtpResponse;
import com.tinjaku.exception.BadRequestException;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.model.PasswordResetToken;
import com.tinjaku.model.User;
import com.tinjaku.repository.PasswordResetTokenRepository;
import com.tinjaku.repository.UserRepository;

@Service
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetTokenService(UserRepository userRepository, EmailService emailService, PasswordResetTokenRepository passwordResetTokenRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private String generatedOtp(){

        SecureRandom random = new SecureRandom();

        int otp = 100000 + random.nextInt(900000);

        return String.valueOf(otp);
    }

    private String generateResetToken(){

        byte[] randomBytes = new byte[32];
        SecureRandom random = new SecureRandom();

        random.nextBytes(randomBytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);
    }

    public ForgotPasswordResponse requestPasswordReset(String email){

        Optional<User> optionalUser = userRepository.findByEmailIgnoreCase(email);
        
        if (optionalUser.isEmpty()) {
            return new ForgotPasswordResponse("OTP berhasil terkirim!");
        }

        User user = optionalUser.get();
        
        Optional<PasswordResetToken> latestOtp = passwordResetTokenRepository.findTopByUserEmailOrderByCreatedAtDesc(email);

        if (latestOtp.isPresent()) {
            
            PasswordResetToken token = latestOtp.get();

            LocalDateTime nextRequestTime = token.getCreatedAt().plusMinutes(1);

            if (LocalDateTime.now().isBefore(nextRequestTime)) {
                throw new BadRequestException("Silakan tunggu 1 menit sebelum meminta OTP lagi!");
            }
        }

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

    public VerifyOtpResponse verifyOtp(OTPVerificationRequest request){

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
        
        String resetToken = generateResetToken();

        token.setResetToken(resetToken);

        passwordResetTokenRepository.save(token);

        return new VerifyOtpResponse("Token berhasil di verifikasi!", resetToken);
    }

    public ForgotPasswordResponse resetPassword(ResetPasswordRequest request){

        Optional<PasswordResetToken> optionalToken = passwordResetTokenRepository.findByResetToken(request.getResetToken());

        if (optionalToken.isEmpty()) {
            throw new ResourceNotFound("Reset token tidak ditemukan!");
        }

        PasswordResetToken token = optionalToken.get();
        
        if (token.isUsed()) {
            throw new BadRequestException("Reset token sudah digunakan!");
        }

        if (token.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Reset token sudah expired!");
        }

        User user = token.getUser();
        
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        token.setUsed(true);
        passwordResetTokenRepository.save(token);

        return new ForgotPasswordResponse("Password berhasil di ubah!");
    }
}