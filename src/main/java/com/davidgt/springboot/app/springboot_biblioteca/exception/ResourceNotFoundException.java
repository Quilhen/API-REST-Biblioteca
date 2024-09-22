package com.davidgt.springboot.app.springboot_biblioteca.exception;


public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String msg){
        super(msg);
    }

}
