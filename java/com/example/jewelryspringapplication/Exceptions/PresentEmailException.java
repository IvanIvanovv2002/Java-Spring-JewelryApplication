package com.example.jewelryspringapplication.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PresentEmailException extends RuntimeException {
    public PresentEmailException(String message) {
        super(message);
    }
}
