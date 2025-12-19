package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponse handleException(ResourceNotFoundException e)
    {
        return new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserBadRequestException.class)
    public ErrorResponse handleBadRequestException(UserBadRequestException ex)
    {
        return new ErrorResponse(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage());
    }
}
