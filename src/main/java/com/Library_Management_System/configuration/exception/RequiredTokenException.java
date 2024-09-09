package com.Library_Management_System.configuration.exception;

public class RequiredTokenException extends RuntimeException {

    public RequiredTokenException() {
    }

    public RequiredTokenException(String message) {
        super(message);
    }
}
