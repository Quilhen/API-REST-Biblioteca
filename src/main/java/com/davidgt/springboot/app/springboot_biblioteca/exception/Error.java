package com.davidgt.springboot.app.springboot_biblioteca.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;


/**
 * Clase que representa un objeto de error personalizado para la API.
 * 
 * Se utiliza para devolver detalles estructurados sobre los errores
 * que ocurren en la aplicación, como códigos de estado HTTP, mensajes y descripciones.
 * 
 * Incluye detalles como el código de error, la fecha y hora en que ocurrió el error,
 * un mensaje descriptivo, y una descripción adicional del error.
 * 
 * Utilizada por el ControllerExceptionHandler para construir las respuestas de error.
 * 
 * @see ControllerExceptionHandler
 * @author David GT
 */
@Getter @Setter 
@NoArgsConstructor
public class Error {

    /**
     * El código de estado HTTP asociado con el error (ej. 404, 500).
     */
    private int codigo;

    /**
     * La fecha y hora en que ocurrió el error.
     */
    private Date timestamp;

     /**
     * Mensaje descriptivo del error.
     */
    private String mensaje;

    /**
     * Descripción adicional del error, como detalles específicos de la solicitud.
     */
    private String descripcion;
    

}
