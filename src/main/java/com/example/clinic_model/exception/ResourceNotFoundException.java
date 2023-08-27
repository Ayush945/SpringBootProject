package com.example.clinic_model.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException {
    private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    public ResourceNotFoundException(String message) {
        super(message);

    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
