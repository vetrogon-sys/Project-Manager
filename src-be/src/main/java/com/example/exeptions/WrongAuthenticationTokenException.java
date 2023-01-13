package com.example.exeptions;

public class WrongAuthenticationTokenException extends RuntimeException {

    public WrongAuthenticationTokenException(String message) {
        super(message);
    }
}
