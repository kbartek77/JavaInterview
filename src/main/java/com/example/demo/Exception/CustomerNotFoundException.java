package com.example.demo.Exception;

import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends RewardException{
    public CustomerNotFoundException (String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
