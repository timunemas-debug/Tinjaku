package com.tinjaku.exception;

public class UsernameNotFoundException extends RuntimeException{
    public UsernameNotFoundException(String massege){
        super(massege);
    }
}