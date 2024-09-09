package com.Library_Management_System.configuration.exception;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Object> handleException(Exception exception, HttpStatus httpStatus) {
        ExceptionModel response = new ExceptionModel();
        response.setMessages(List.of(exception.getMessage()));
        response.setStatus(httpStatus.value());
        response.setError(httpStatus.getReasonPhrase());
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return handleException(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException e) {
        return handleException(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {NoAccessException.class})
    public ResponseEntity<Object> handleNoAccessException(NoAccessException ex) {
        return handleException(ex, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {SignatureException.class})
    public ResponseEntity<Object> handleExpiredJwtException(SignatureException ex) {
        return handleException(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        return handleException(ex, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException exception) {
        return handleException(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleAgilityNotFoundException(NotFoundException exception) {
        return handleException(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<Object> handleInternalServerError(InternalServerException exception) {
        return handleException(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception exception) {
        return handleException(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RequiredTokenException.class)
    public ResponseEntity<Object> handleGeneralException(RequiredTokenException exception) {
        return handleException(exception, HttpStatus.UNAUTHORIZED);
    }
}