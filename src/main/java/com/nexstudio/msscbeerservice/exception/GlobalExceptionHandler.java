package com.nexstudio.msscbeerservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BeerNotFoundException.class)
    public ResponseEntity<String> handleBeerNotFoundException(BeerNotFoundException exception, WebRequest request) {
        return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
    }
}
