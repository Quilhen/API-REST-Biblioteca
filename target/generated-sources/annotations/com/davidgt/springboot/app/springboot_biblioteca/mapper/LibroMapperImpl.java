package com.davidgt.springboot.app.springboot_biblioteca.mapper;

import com.davidgt.springboot.app.springboot_biblioteca.dto.LibroDto;
import com.davidgt.springboot.app.springboot_biblioteca.dto.PrestamoDto;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Libro;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Prestamo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-26T17:45:27+0200",
    comments = "version: 1.5.0.Final, compiler: Eclipse JDT (IDE) 3.39.0.v20240820-0604, environment: Java 17.0.12 (Eclipse Adoptium)"
)
@Component
public class LibroMapperImpl implements LibroMapper {

    @Override
    public Libro libroDtoToLibro(LibroDto libroDto) {
        if ( libroDto == null ) {
            return null;
        }

        Libro libro = new Libro();

        libro.setAutor( libroDto.getAutor() );
        libro.setAñoPublicacion( libroDto.getAñoPublicacion() );
        libro.setDisponibilidad( libroDto.isDisponibilidad() );
        libro.setGenero( libroDto.getGenero() );
        libro.setId( libroDto.getId() );
        libro.setPrestamos( prestamoDtoListToPrestamoList( libroDto.getPrestamos() ) );
        libro.setTitulo( libroDto.getTitulo() );
        libro.setUsuario( libroDto.getUsuario() );

        return libro;
    }

    @Override
    public LibroDto libroToLibroDto(Libro libro) {
        if ( libro == null ) {
            return null;
        }

        LibroDto libroDto = new LibroDto();

        libroDto.setAutor( libro.getAutor() );
        libroDto.setAñoPublicacion( libro.getAñoPublicacion() );
        libroDto.setDisponibilidad( libro.isDisponibilidad() );
        libroDto.setGenero( libro.getGenero() );
        libroDto.setId( libro.getId() );
        libroDto.setPrestamos( prestamoListToPrestamoDtoList( libro.getPrestamos() ) );
        libroDto.setTitulo( libro.getTitulo() );
        libroDto.setUsuario( libro.getUsuario() );

        return libroDto;
    }

    @Override
    public List<LibroDto> toLibroDtoList(List<Libro> libros) {
        if ( libros == null ) {
            return null;
        }

        List<LibroDto> list = new ArrayList<LibroDto>( libros.size() );
        for ( Libro libro : libros ) {
            list.add( libroToLibroDto( libro ) );
        }

        return list;
    }

    @Override
    public List<Libro> toLibroList(List<LibroDto> librosDto) {
        if ( librosDto == null ) {
            return null;
        }

        List<Libro> list = new ArrayList<Libro>( librosDto.size() );
        for ( LibroDto libroDto : librosDto ) {
            list.add( libroDtoToLibro( libroDto ) );
        }

        return list;
    }

    protected Prestamo prestamoDtoToPrestamo(PrestamoDto prestamoDto) {
        if ( prestamoDto == null ) {
            return null;
        }

        Prestamo prestamo = new Prestamo();

        prestamo.setFechaDevolucion( prestamoDto.getFechaDevolucion() );
        prestamo.setFechaPrestamo( prestamoDto.getFechaPrestamo() );
        prestamo.setId( prestamoDto.getId() );
        prestamo.setLibro( prestamoDto.getLibro() );
        prestamo.setUsuario( prestamoDto.getUsuario() );

        return prestamo;
    }

    protected List<Prestamo> prestamoDtoListToPrestamoList(List<PrestamoDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Prestamo> list1 = new ArrayList<Prestamo>( list.size() );
        for ( PrestamoDto prestamoDto : list ) {
            list1.add( prestamoDtoToPrestamo( prestamoDto ) );
        }

        return list1;
    }

    protected PrestamoDto prestamoToPrestamoDto(Prestamo prestamo) {
        if ( prestamo == null ) {
            return null;
        }

        PrestamoDto prestamoDto = new PrestamoDto();

        prestamoDto.setFechaDevolucion( prestamo.getFechaDevolucion() );
        prestamoDto.setFechaPrestamo( prestamo.getFechaPrestamo() );
        prestamoDto.setId( prestamo.getId() );
        prestamoDto.setLibro( prestamo.getLibro() );
        prestamoDto.setUsuario( prestamo.getUsuario() );

        return prestamoDto;
    }

    protected List<PrestamoDto> prestamoListToPrestamoDtoList(List<Prestamo> list) {
        if ( list == null ) {
            return null;
        }

        List<PrestamoDto> list1 = new ArrayList<PrestamoDto>( list.size() );
        for ( Prestamo prestamo : list ) {
            list1.add( prestamoToPrestamoDto( prestamo ) );
        }

        return list1;
    }
}
