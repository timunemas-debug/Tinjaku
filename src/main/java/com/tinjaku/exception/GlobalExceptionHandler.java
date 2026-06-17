package com.tinjaku.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tinjaku.dto.ResponErorRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ResponErorRequest> handleUserNotFound(ResourceNotFound ex){
        return ResponseEntity
               .status(HttpStatus.NOT_FOUND)
               .body(new ResponErorRequest(ex.getMessage(), 404));
    }
}