package com.davidgt.springboot.app.springboot_biblioteca.dto;

import java.time.LocalDate;
import java.util.List;

import com.davidgt.springboot.app.springboot_biblioteca.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
public class LibroDto {


    private Long id;

    @NotNull
    private String titulo;

    @NotNull
    private String autor;

    private LocalDate añoPublicacion;
    private String genero;

    @NotNull
    private boolean disponibilidad;

    @JsonIgnore
    private Usuario usuario;

    @JsonIgnore
    private List<PrestamoDto> prestamos;

}
