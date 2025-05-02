package com.gnomeshift.springtaskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.util.Date;

@ControllerAdvice
public class ExHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExDetails> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ExDetails details = new ExDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(details);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExDetails> httpMessageNotReadable(HttpMessageNotReadableException ex, WebRequest request) {
        ExDetails details = new ExDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExDetails> validationException(MethodArgumentNotValidException ex, WebRequest request) {
        ExDetails details = new ExDetails(new Date(), ex.getBindingResult().getAllErrors().getFirst()
                .getDefaultMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExDetails> globalExceptionHandler(Exception ex, WebRequest request) {
        ExDetails details = new ExDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(details);
    }
}
