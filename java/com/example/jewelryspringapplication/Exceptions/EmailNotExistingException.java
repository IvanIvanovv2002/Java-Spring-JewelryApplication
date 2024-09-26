package com.example.jewelryspringapplication.Exceptions;

public class EmailNotExistingException extends RuntimeException{
    public EmailNotExistingException(String message) {
        super(message);
    }
}
