package com.davidgt.springboot.app.springboot_biblioteca.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;


/**
 * Manejador global de excepciones para la aplicación.
 * 
 * Proporciona manejo personalizado para excepciones comunes como ResourceNotFoundException
 * y otras excepciones genéricas del sistema.
 * 
 * Utiliza @ControllerAdvice para capturar las excepciones lanzadas por los controladores
 * y devolver respuestas de error estructuradas.
 * 
 * @see ResourceNotFoundException
 * @see Error
 * @author David GT
 */
@ControllerAdvice
public class ControllerExceptionHandler {


     /**
     * Maneja la excepción ResourceNotFoundException.
     * 
     * Este método devuelve un objeto de error con un código de estado 404 (NOT FOUND)
     * cuando una entidad solicitada no se encuentra en el sistema.
     * 
     * @param ex La excepción lanzada cuando un recurso no es encontrado.
     * @param request La solicitud web que generó la excepción.
     * @return Un objeto ResponseEntity que contiene el error y un código HTTP 404.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> resourceResponseEntity(ResourceNotFoundException ex, WebRequest request){
        Error error = new Error();
        error.setCodigo(HttpStatus.NOT_FOUND.value());
        error.setMensaje(ex.getMessage());
        error.setDescripcion(request.getDescription(false));
        error.setTimestamp(new Date());

        return new ResponseEntity<Error>(error,HttpStatus.NOT_FOUND);
    }


     /**
     * Maneja cualquier excepción no controlada (Exception).
     * 
     * Este método devuelve un objeto de error con un código de estado 500 (INTERNAL SERVER ERROR)
     * para excepciones genéricas no previstas.
     * 
     * @param ex La excepción no controlada.
     * @param request La solicitud web que generó la excepción.
     * @return Un objeto ResponseEntity que contiene el error y un código HTTP 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> globalExceptionHandler(Exception ex, WebRequest request){
        Error error = new Error();
        error.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMensaje(ex.getMessage());
        error.setDescripcion(request.getDescription(false));
        error.setTimestamp(new Date());

        return new ResponseEntity<Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(PrestamoDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Error> prestamoDuplicadoException(Exception ex, WebRequest request){
        Error error = new Error();
        error.setCodigo(HttpStatus.CONFLICT.value());
        error.setMensaje(ex.getMessage());
        error.setDescripcion(request.getDescription(false));
        error.setTimestamp(new Date());

        return new ResponseEntity<Error>(error, HttpStatus.CONFLICT);
    }
}
