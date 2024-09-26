package com.davidgt.springboot.app.springboot_biblioteca.mapper;

import com.davidgt.springboot.app.springboot_biblioteca.dto.LibroDto;
import com.davidgt.springboot.app.springboot_biblioteca.dto.PrestamoDto;
import com.davidgt.springboot.app.springboot_biblioteca.dto.UsuarioDto;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Libro;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Prestamo;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Usuario;
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
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public UsuarioDto usuarioToUsuarioDto(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        UsuarioDto usuarioDto = new UsuarioDto();

        usuarioDto.setEmail( usuario.getEmail() );
        usuarioDto.setId( usuario.getId() );
        usuarioDto.setLibros( libroListToLibroDtoList( usuario.getLibros() ) );
        usuarioDto.setNombre( usuario.getNombre() );
        usuarioDto.setPrestamos( prestamoListToPrestamoDtoList( usuario.getPrestamos() ) );

        return usuarioDto;
    }

    @Override
    public Usuario usuarioDtoToUsuario(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        Usuario usuario1 = new Usuario();

        usuario1.setEmail( usuario.getEmail() );
        usuario1.setId( usuario.getId() );
        List<Libro> list = usuario.getLibros();
        if ( list != null ) {
            usuario1.setLibros( new ArrayList<Libro>( list ) );
        }
        usuario1.setNombre( usuario.getNombre() );
        List<Prestamo> list1 = usuario.getPrestamos();
        if ( list1 != null ) {
            usuario1.setPrestamos( new ArrayList<Prestamo>( list1 ) );
        }

        return usuario1;
    }

    @Override
    public List<UsuarioDto> toUsuarioDtoList(List<Usuario> usuario) {
        if ( usuario == null ) {
            return null;
        }

        List<UsuarioDto> list = new ArrayList<UsuarioDto>( usuario.size() );
        for ( Usuario usuario1 : usuario ) {
            list.add( usuarioToUsuarioDto( usuario1 ) );
        }

        return list;
    }

    @Override
    public List<Usuario> toUsuarioList(List<UsuarioDto> usuarioDto) {
        if ( usuarioDto == null ) {
            return null;
        }

        List<Usuario> list = new ArrayList<Usuario>( usuarioDto.size() );
        for ( UsuarioDto usuarioDto1 : usuarioDto ) {
            list.add( usuarioDtoToUsuario1( usuarioDto1 ) );
        }

        return list;
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
        prestamoDto.setUsuario( usuarioDtoToUsuario( prestamo.getUsuario() ) );

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

    protected LibroDto libroToLibroDto(Libro libro) {
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
        libroDto.setUsuario( usuarioDtoToUsuario( libro.getUsuario() ) );

        return libroDto;
    }

    protected List<LibroDto> libroListToLibroDtoList(List<Libro> list) {
        if ( list == null ) {
            return null;
        }

        List<LibroDto> list1 = new ArrayList<LibroDto>( list.size() );
        for ( Libro libro : list ) {
            list1.add( libroToLibroDto( libro ) );
        }

        return list1;
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
        prestamo.setUsuario( usuarioDtoToUsuario( prestamoDto.getUsuario() ) );

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

    protected Libro libroDtoToLibro(LibroDto libroDto) {
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
        libro.setUsuario( usuarioDtoToUsuario( libroDto.getUsuario() ) );

        return libro;
    }

    protected List<Libro> libroDtoListToLibroList(List<LibroDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Libro> list1 = new ArrayList<Libro>( list.size() );
        for ( LibroDto libroDto : list ) {
            list1.add( libroDtoToLibro( libroDto ) );
        }

        return list1;
    }

    protected Usuario usuarioDtoToUsuario1(UsuarioDto usuarioDto) {
        if ( usuarioDto == null ) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setEmail( usuarioDto.getEmail() );
        usuario.setId( usuarioDto.getId() );
        usuario.setLibros( libroDtoListToLibroList( usuarioDto.getLibros() ) );
        usuario.setNombre( usuarioDto.getNombre() );
        usuario.setPrestamos( prestamoDtoListToPrestamoList( usuarioDto.getPrestamos() ) );

        return usuario;
    }
}
