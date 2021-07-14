package com.lukinhasssss.proposta.config.validation;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    ResponseEntity<List<StandardErrorMessage>> validation(MethodArgumentNotValidException e) {
        List<StandardErrorMessage> errors = new ArrayList<>();

        List<FieldError> fieldErrors = e.getFieldErrors();
        fieldErrors.forEach(error -> errors.add(new StandardErrorMessage(error.getField(), error.getDefaultMessage())));

        return ResponseEntity.badRequest().body(errors);
    }

}
