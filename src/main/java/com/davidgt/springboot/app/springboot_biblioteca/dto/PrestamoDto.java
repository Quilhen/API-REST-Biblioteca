package com.davidgt.springboot.app.springboot_biblioteca.dto;

import java.time.LocalDate;

import com.davidgt.springboot.app.springboot_biblioteca.entity.Libro;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Usuario;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
public class PrestamoDto {


    private Long id;
    private Usuario usuario;
    private Libro libro;

    @NotNull
    private LocalDate fechaPrestamo;

    @NotNull
    private LocalDate fechaDevolucion;


}
