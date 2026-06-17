package com.tinjaku.exception;

public class ResourceNotFound extends RuntimeException{
    public ResourceNotFound(String massege){
        super(massege);
    }
}