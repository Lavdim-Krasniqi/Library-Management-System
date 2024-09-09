package com.Library_Management_System.configuration.exception;

public class InternalServerException extends RuntimeException {

    public InternalServerException() {}

    public InternalServerException(String message) {
        super(message);
    }
}