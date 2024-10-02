package com.davidgt.springboot.app.springboot_biblioteca.exception;


/**
 * Excepción personalizada lanzada cuando un recurso solicitado no se encuentra.
 * 
 * Esta excepción se utiliza en los servicios cuando no se puede encontrar una
 * entidad en la base de datos, y es manejada por ControllerExceptionHandler.
 * 
 * @see ControllerExceptionHandler
 * @author David GT
 */
public class ResourceNotFoundException extends RuntimeException{

      /**
     * Constructor que crea una nueva instancia de ResourceNotFoundException con un mensaje específico.
     * 
     * @param msg El mensaje que describe la causa del error.
     */
    public ResourceNotFoundException(String msg){
        super(msg);
    }

}
