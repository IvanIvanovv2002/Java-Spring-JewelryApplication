package com.example.jewelryspringapplication.Exceptions;


public class WrongLinkedEmailException extends RuntimeException {
    public WrongLinkedEmailException(String message) {
        super(message);
    }
}
