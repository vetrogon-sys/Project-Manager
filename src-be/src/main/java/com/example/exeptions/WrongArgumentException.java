package com.example.exeptions;

public class WrongArgumentException extends RuntimeException {

    public WrongArgumentException(String message) {
        super(message);
    }

}
