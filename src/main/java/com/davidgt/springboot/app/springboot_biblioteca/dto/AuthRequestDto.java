package com.davidgt.springboot.app.springboot_biblioteca.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthRequestDto {

    private String nombreUsuario;
    private String password;

}
