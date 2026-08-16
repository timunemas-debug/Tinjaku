package com.tinjaku.dto.response;

import java.time.LocalDateTime;

import com.tinjaku.model.SenderType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {
    
    private Long pesananId;
    private Long senderId;
    private String senderName;
    private SenderType senderType;
    private String message;
    private LocalDateTime timeStamp;
}