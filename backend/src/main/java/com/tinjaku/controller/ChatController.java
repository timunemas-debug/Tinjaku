package com.tinjaku.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.tinjaku.dto.request.ChatRequest;
import com.tinjaku.dto.response.ChatResponse;
import com.tinjaku.service.ChatService;

import jakarta.validation.Valid;

@RestController
public class ChatController {
    
    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatController(ChatService chatService, SimpMessagingTemplate simpMessagingTemplate){
        this.chatService = chatService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/chat")
    public ChatResponse processMessage(@Valid ChatRequest request){
        
        ChatResponse response = chatService.processMessage(request);

        simpMessagingTemplate.convertAndSend("/chatroom/" + request.getPesananId(), response);
        
        return response;
    }
}