package com.tinjaku.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponErorRequest {
    private String message;
    private int status;
}