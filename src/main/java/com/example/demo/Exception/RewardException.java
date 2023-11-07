package com.example.demo.Exception;

import org.springframework.http.HttpStatus;

public abstract class RewardException extends RuntimeException{
    private final HttpStatus httpStatus;
    public RewardException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
