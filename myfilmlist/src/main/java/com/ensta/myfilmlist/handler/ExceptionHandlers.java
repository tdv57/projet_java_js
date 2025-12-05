package com.ensta.myfilmlist.handler;

import com.ensta.myfilmlist.exception.*;

import java.net.BindException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.ensta.myfilmlist.exception.ControllerException;

@RestControllerAdvice
public class ExceptionHandlers {

    // 8.4 à faire
    @ExceptionHandler
    public ResponseEntity<String> handleControllerExpection(ControllerException controllerException, WebRequest webRequest) {
        return ResponseEntity.status(400).body("erreur");
    }

    @ExceptionHandler
    public ResponseEntity<String> handleBindException(BindException bindException, WebRequest webRequest) {
        return ResponseEntity.status(400).body("requete incomplète");
    }

}