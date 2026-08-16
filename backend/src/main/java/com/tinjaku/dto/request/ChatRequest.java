package com.tinjaku.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRequest {
    
    private Long pesananId;
    private String message;
}