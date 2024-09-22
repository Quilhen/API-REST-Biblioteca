package com.davidgt.springboot.app.springboot_biblioteca.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> resourcResponseEntity(ResourceNotFoundException ex, WebRequest request){
        Error error = new Error();
        error.setCodigo(HttpStatus.NOT_FOUND.value());
        error.setMensaje(ex.getMessage());
        error.setDescripcion(request.getDescription(false));
        error.setTimestamp(new Date());

        return new ResponseEntity<Error>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> globalExceptionHandler(Exception ex, WebRequest request){
        Error error = new Error();
        error.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMensaje(ex.getMessage());
        error.setDescripcion(request.getDescription(false));
        error.setTimestamp(new Date());

        return new ResponseEntity<Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
