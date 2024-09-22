package com.davidgt.springboot.app.springboot_biblioteca.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

import com.davidgt.springboot.app.springboot_biblioteca.dto.LibroDto;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Libro;
import com.davidgt.springboot.app.springboot_biblioteca.exception.ResourceNotFoundException;
import com.davidgt.springboot.app.springboot_biblioteca.mapper.LibroMapper;
import com.davidgt.springboot.app.springboot_biblioteca.repository.LibroRepository;
import com.davidgt.springboot.app.springboot_biblioteca.repository.LibroSpecification;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private LibroMapper libroMapper;

    public Page<LibroDto> getAllLibros(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Libro> librosPage = libroRepository.findAll(pageable);
        Page<LibroDto> dtoPage = librosPage.map(libro -> libroMapper.libroToLibroDto(libro));

        return dtoPage;
    }

    public Page<Libro> obtenerLibrosConFiltros(String titulo, String autor, LocalDate fechaPublicacion, String genero, Pageable pageable) {
        return libroRepository.findAll(LibroSpecification.filtrarPorParametros(titulo, autor, fechaPublicacion, genero),pageable);
    }

    public LibroDto getLibroById(Long id) {
        Optional<Libro> libroOpt = libroRepository.findById(id);

        if (!libroOpt.isPresent()) {
            throw new ResourceNotFoundException("Libro no encontrado con el ID: " + id);
        }

        return libroMapper.libroToLibroDto(libroOpt.get());

    }

    public LibroDto crearLibro(LibroDto libroDto) {
        Libro libro = libroMapper.libroDtoToLibro(libroDto);
        libroRepository.save(libro);

        return libroMapper.libroToLibroDto(libro);
    }

    public LibroDto actualizarLibro(LibroDto libroDto, Long id) {
        Optional<Libro> libroOpt = libroRepository.findById(id);

        if (!libroOpt.isPresent()) {
            throw new ResourceNotFoundException("Libro no encontrado con el ID: " + id);
        }

        Libro libro = libroOpt.get();
        libro.setTitulo(libroDto.getTitulo());
        libro.setAutor(libroDto.getAutor());
        libro.setAñoPublicacion(libroDto.getAñoPublicacion());
        libro.setGenero(libroDto.getGenero());
        libro.setDisponibilidad(libroDto.isDisponibilidad());
        libro.setUsuario(libroDto.getUsuario());

        // libro.setPrestamos(libroDto.getPrestamos().stream()
        // .map(p -> prestamoMapper.prestamoDtoToPrestamo(p))
        // .collect(Collectors.toList()));

        libroRepository.save(libro);
        return libroMapper.libroToLibroDto(libro);
    }

    public LibroDto eliminarLibro(Long id) {
        Optional<Libro> libroOpt = libroRepository.findById(id);

        if (!libroOpt.isPresent()) {
            throw new ResourceNotFoundException("Libro no encontrado con el ID: " + id);
        }

        Libro libro = libroOpt.get();
        libroRepository.delete(libro);
        return libroMapper.libroToLibroDto(libro);
    }

}
