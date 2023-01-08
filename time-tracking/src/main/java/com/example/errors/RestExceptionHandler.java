package com.example.errors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
@ResponseBody
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResponse handleNodataFoundException(
            NoSuchElementException ex, WebRequest request) {

        ErrorResponse err = ErrorResponse.builder()
                .description(ex.getMessage())
                .message("No data found")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();

        return err;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDataIntegrityViolationExceptionException(
            DataIntegrityViolationException ex, WebRequest request) {

        ErrorResponse err = ErrorResponse.builder()
                .description(HttpStatus.BAD_REQUEST.toString())
                .message(ex.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();

        return err;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDataIntegrityViolationExceptionException(
            ConstraintViolationException ex, WebRequest request) {

        ErrorResponse err = ErrorResponse.builder()
                .description(HttpStatus.BAD_REQUEST.toString())
                .message(ex.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();

        return err;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleAuthException(
            ConstraintViolationException ex, WebRequest request) {

        ErrorResponse err = ErrorResponse.builder()
                .description(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .message(ex.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

        return err;
    }

}
