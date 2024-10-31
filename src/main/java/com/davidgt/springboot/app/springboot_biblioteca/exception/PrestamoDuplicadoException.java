package com.davidgt.springboot.app.springboot_biblioteca.exception;

public class PrestamoDuplicadoException extends RuntimeException{
    public PrestamoDuplicadoException(String message){
        super(message);
    }

}
