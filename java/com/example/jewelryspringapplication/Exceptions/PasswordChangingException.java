package com.example.jewelryspringapplication.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordChangingException extends RuntimeException {
    public PasswordChangingException(String message) {
        super(message);
    }
}
