package com.ensta.myfilmlist.handler;

import com.ensta.myfilmlist.exception.*;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.ensta.myfilmlist.exception.ControllerException;

@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler
    public ResponseEntity<String> handleControllerExpection(ControllerException controllerException, WebRequest webRequest) {
        return ResponseEntity.status(400).body(controllerException.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleBindException(BindException bindException, WebRequest webRequest) {
    List<String> errors = bindException
                            .getFieldErrors()
                            .stream()
                            .map(fieldError -> fieldError.getDefaultMessage())
                            .collect(Collectors.toList());
    return ResponseEntity.status(400).body(errors.get(0));
    }

}