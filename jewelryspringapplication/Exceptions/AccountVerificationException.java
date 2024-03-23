package com.example.jewelryspringapplication.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AccountVerificationException extends RuntimeException {
    public AccountVerificationException(String message) {
        super(message);
    }
}
