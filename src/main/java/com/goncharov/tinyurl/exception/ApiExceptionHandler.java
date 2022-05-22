package com.goncharov.tinyurl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UrlIsNullException.class)
    public ResponseEntity<String> UrlIsNullException() {
        String message = "Url must be not null";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UrlIsToShortException.class)
    public ResponseEntity<String> UrlIsToShortException() {
        String message = "Url is to short";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UrlDoesntExistException.class)
    public ResponseEntity<String> UrlDoesntExist() {
        String message = "Url is outdated or was never created";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
