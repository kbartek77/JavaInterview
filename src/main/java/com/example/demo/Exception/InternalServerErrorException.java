package com.example.demo.Exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends RewardException {
    public InternalServerErrorException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
