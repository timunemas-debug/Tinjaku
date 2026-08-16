package com.tinjaku.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.tinjaku.dto.request.ChatRequest;
import com.tinjaku.dto.response.ChatResponse;
import com.tinjaku.exception.BadRequestException;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.model.Pesanan;
import com.tinjaku.model.Rating;
import com.tinjaku.model.SenderType;
import com.tinjaku.model.StatusPesanan;
import com.tinjaku.repository.PesananRepository;
import com.tinjaku.repository.RatingRepository;
import com.tinjaku.security.CustomMitraDetails;
import com.tinjaku.security.CustomUserDetails;
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

    public boolean canAccessChat(Long pesananId){

        Optional<Pesanan> pesananOptional = pesananRepository.findById(pesananId);

        if (pesananOptional.isEmpty()) {
            throw new ResourceNotFound("Pesanan tidak ditemukan!");
        }

        Pesanan pesanan = pesananOptional.get();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (userDetails instanceof CustomUserDetails customUserDetails) {

            boolean milikUser = pesanan.getUser().getUserId().equals(customUserDetails.getUser().getUserId());
            if (milikUser) {
                return true;
            }
        }

        if (userDetails instanceof CustomMitraDetails customMitraDetails) {

            if (pesanan.getMitra() == null) {
                throw new ResourceNotFound("Mitra tidak ditemukan!");
            }

            boolean milikMitra = pesanan.getMitra().getMitraId().equals(customMitraDetails.getMitra().getMitraId());
            if (milikMitra) {
                return true;
            }
        }

        return false;
    }

    public ChatResponse processMessage(ChatRequest request){

        Long senderId = null;
        String senderName = null;
        SenderType senderType = null;

        boolean bolehAkses = canAccessChat(request.getPesananId());
        if (!bolehAkses) {
            throw new BadRequestException("Tidak memiliki akses!");
        }

        boolean chatAktif = isChatActive(request.getPesananId());
        if (!chatAktif) {
            throw new BadRequestException("Chat sudah tidak aktif!");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (userDetails instanceof CustomUserDetails customUserDetails) {
            Long senderId = customUserDetails.getUser().getUserId();
            String senderName = customUserDetails.getUser().getNamaDepan();
            SenderType senderType = SenderType.USER;
        }

        if (userDetails instanceof CustomMitraDetails customMitraDetails) {
            Long senderId = customMitraDetails.getMitra().getMitraId();
            String senderName = customMitraDetails.getMitra().getNamaMitra();
            SenderType senderType = SenderType.MITRA;
        }

        LocalDateTime timeStamp = LocalDateTime.now();
    }
}