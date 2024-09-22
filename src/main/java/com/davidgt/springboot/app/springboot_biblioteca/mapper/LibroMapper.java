package com.davidgt.springboot.app.springboot_biblioteca.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.davidgt.springboot.app.springboot_biblioteca.dto.LibroDto;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Libro;

@Mapper(componentModel = "spring")
public interface LibroMapper {

    Libro libroDtoToLibro(LibroDto libroDto);
    LibroDto libroToLibroDto(Libro libro);

    List<LibroDto> toLibroDtoList(List<Libro> libros);
    List<Libro> toLibroList(List<LibroDto>librosDto);

}
