package com.davidgt.springboot.app.springboot_biblioteca.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
public class UsuarioDto {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    @Email
    private String email;

    @JsonIgnore
    private List<LibroDto> libros;

    @JsonIgnore
    private List<PrestamoDto> prestamos;

}
