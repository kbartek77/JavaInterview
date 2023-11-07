package com.example.demo.Exception;

import org.springframework.http.HttpStatus;

public class UnprocessableEntityException extends RewardException {
    public UnprocessableEntityException(String message) {
        super(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
