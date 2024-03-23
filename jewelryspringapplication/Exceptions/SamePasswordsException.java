package com.example.jewelryspringapplication.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class SamePasswordsException extends RuntimeException {
    public SamePasswordsException(String message) {
        super(message);
    }
}
