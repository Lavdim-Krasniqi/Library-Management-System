package com.Library_Management_System.configuration.exception;

public class NoAccessException extends RuntimeException{

    public NoAccessException(String message) {
        super(message);
    }

    public NoAccessException() {}
}