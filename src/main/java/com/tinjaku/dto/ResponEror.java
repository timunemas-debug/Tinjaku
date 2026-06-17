package com.tinjaku.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponEror {
    private String message;
    private int status;
}