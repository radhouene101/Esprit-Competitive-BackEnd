package com.apex.picloud.services.post;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ContentValidationException extends RuntimeException {
    public ContentValidationException(String message) {
        super(message);
    }
}

