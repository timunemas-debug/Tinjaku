package com.tinjaku.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String massege){
        super(massege);
    }
}