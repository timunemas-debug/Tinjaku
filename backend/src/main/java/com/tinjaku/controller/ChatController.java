package com.tinjaku.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.tinjaku.service.ChatService;

@RestController
@MessageMapping("/chat")
public class ChatController {
    
    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatController(ChatService chatService, SimpMessagingTemplate simpMessagingTemplate){
        this.chatService = chatService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }
}