package com.example.demo.Exception;

import org.springframework.http.HttpStatus;

public class TransactionNotFoundException extends RewardException{
    public TransactionNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
