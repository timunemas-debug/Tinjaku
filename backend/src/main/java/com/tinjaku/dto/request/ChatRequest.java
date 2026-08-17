package com.tinjaku.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRequest {
    
    private Long pesananId;

    @NotBlank
    @Size(max = 500)
    private String message;
}