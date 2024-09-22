package com.davidgt.springboot.app.springboot_biblioteca.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter @Setter 
@NoArgsConstructor
public class Error {

    private int codigo;
    private Date timestamp;
    private String mensaje;
    private String descripcion;
    

}
