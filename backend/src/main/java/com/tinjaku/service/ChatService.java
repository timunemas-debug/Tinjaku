package com.tinjaku.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tinjaku.exception.BadRequestException;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.model.Pesanan;
import com.tinjaku.model.Rating;
import com.tinjaku.model.StatusPesanan;
import com.tinjaku.repository.PesananRepository;
import com.tinjaku.repository.RatingRepository;
import com.tinjaku.security.SecurityService;

@Service
public class ChatService {
    
    private final PesananRepository pesananRepository;
    private final RatingRepository ratingRepository;
    private final SecurityService securityService;

    public ChatService(PesananRepository pesananRepository, RatingRepository ratingRepository, SecurityService securityService){

        this.pesananRepository = pesananRepository;
        this.ratingRepository = ratingRepository;
        this.securityService = securityService;
    }

    public boolean isChatActive(Long pesananId){
        
        Optional <Pesanan> pesananOptional = pesananRepository.findById(pesananId);
        
        if (pesananOptional.isEmpty()) {
            throw new ResourceNotFound("Pesanan tidak ditemukan!");
        }
        
        Pesanan pesanan = pesananOptional.get();
        
        if (!pesanan.getStatus().equals(StatusPesanan.SELESAI)) {
            return true;
        }
        
        boolean ratingExists = ratingRepository.existsByPesananId(pesananId);
        
        if (ratingExists) {
            return false;
        }
        
        LocalDateTime deadLine = pesanan.getCompletedAt().plusMinutes(30);

        return LocalDateTime.now().isBefore(deadLine);
    }
}