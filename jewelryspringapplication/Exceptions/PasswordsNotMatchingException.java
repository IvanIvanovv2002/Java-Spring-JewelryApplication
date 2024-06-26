package com.example.jewelryspringapplication.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class PasswordsNotMatchingException extends RuntimeException {
    public PasswordsNotMatchingException(String message) {
        super(message);
    }
}
