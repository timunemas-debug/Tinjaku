package com.tinjaku.exception;

public class BusinessException extends RuntimeException{
        public BusinessException(String massage){
            super(massage);
        }
}