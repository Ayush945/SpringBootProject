package com.example.clinic_model.exception;

import org.springframework.http.HttpStatus;

public class LoginException extends RuntimeException {
    private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    public LoginException(String message) {
        super(message);
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
