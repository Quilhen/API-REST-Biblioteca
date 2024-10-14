package com.davidgt.springboot.app.springboot_biblioteca.exception;

import java.util.Date;

/**
 * Clase que representa un objeto de error personalizado para la API.
 * 
 * Se utiliza para devolver detalles estructurados sobre los errores
 * que ocurren en la aplicación, como códigos de estado HTTP, mensajes y
 * descripciones.
 * 
 * Incluye detalles como el código de error, la fecha y hora en que ocurrió el
 * error,
 * un mensaje descriptivo, y una descripción adicional del error.
 * 
 * Utilizada por el ControllerExceptionHandler para construir las respuestas de
 * error.
 * 
 * @see ControllerExceptionHandler
 * @author David GT
 */
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

    public Error() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + codigo;
        result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
        result = prime * result + ((mensaje == null) ? 0 : mensaje.hashCode());
        result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Error other = (Error) obj;
        if (codigo != other.codigo)
            return false;
        if (timestamp == null) {
            if (other.timestamp != null)
                return false;
        } else if (!timestamp.equals(other.timestamp))
            return false;
        if (mensaje == null) {
            if (other.mensaje != null)
                return false;
        } else if (!mensaje.equals(other.mensaje))
            return false;
        if (descripcion == null) {
            if (other.descripcion != null)
                return false;
        } else if (!descripcion.equals(other.descripcion))
            return false;
        return true;
    }

}
