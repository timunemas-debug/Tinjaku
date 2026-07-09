package com.tinjaku.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tinjaku.dto.request.ResponErorRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ResponErorRequest> handleUserNotFound(ResourceNotFound ex){
        return ResponseEntity
               .status(HttpStatus.NOT_FOUND)
               .body(new ResponErorRequest(ex.getMessage(), 404));
    }
    
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponErorRequest> handleBadRequest(BadRequestException ex){
        return ResponseEntity
               .status(HttpStatus.BAD_REQUEST)
               .body(new ResponErorRequest(ex.getMessage(), 400));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponErorRequest> handleBusiness(BusinessException ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT.value())
                .body(new ResponErorRequest(ex.getMessage(), 409));
    }
}