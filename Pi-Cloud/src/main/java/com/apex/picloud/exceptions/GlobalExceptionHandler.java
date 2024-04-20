package com.apex.picloud.exceptions;

import com.apex.picloud.services.post.ContentValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ContentValidationException.class)
    public ResponseEntity<String> handleContentValidationException(ContentValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
