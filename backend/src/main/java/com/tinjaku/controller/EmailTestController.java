package com.tinjaku.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tinjaku.service.EmailService;

@RestController
@RequestMapping("/test")
public class EmailTestController {
    
    @Autowired
    private EmailService emailService;

    @PostMapping("/email")
    public ResponseEntity<String> testEmail(@RequestParam String email){
        
        emailService.sendOtpEmail(email, "1HD1R2");

        return ResponseEntity.ok("Email berhasil dikirim!");
    }
}