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

/**
 * Servicio que gestiona la lógica de negocio relacionada con los libros.
 * Proporciona métodos para crear, obtener, actualizar, eliminar y filtrar libros.
 * 
 * Interactúa con el repositorio LibroRepository y utiliza el LibroMapper para
 * convertir entre entidades y DTOs.
 * 
 * @author David GT
 */

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private LibroMapper libroMapper;


     /**
     * Obtiene una lista paginada de todos los libros en el sistema.
     * 
     * @param page Número de la página que se desea obtener.
     * @param size Tamaño de la página (cantidad de libros por página).
     * @return Una página con los objetos LibroDto.
     */
    public Page<LibroDto> getAllLibros(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Libro> librosPage = libroRepository.findAll(pageable);
        Page<LibroDto> dtoPage = librosPage.map(libro -> libroMapper.libroToLibroDto(libro));

        return dtoPage;
    }


    /**
     * Obtiene una lista paginada de libros que cumplen con los filtros especificados.
     * 
     * @param titulo El título del libro (opcional).
     * @param autor El autor del libro (opcional).
     * @param fechaPublicacion  La fecha de publicación del libro (opcional).
     * @param genero El género del libro (opcional).
     * @param pageable Parámetros de paginación.
     * @return Una página de libros filtrados según los parámetros.
     */
    public Page<Libro> obtenerLibrosConFiltros(String titulo, String autor, LocalDate fechaPublicacion, String genero, Pageable pageable) {
        return libroRepository.findAll(LibroSpecification.filtrarPorParametros(titulo, autor, fechaPublicacion, genero),pageable);
    }

     /**
     * Obtiene los detalles de un libro específico por su ID.
     * 
     * @param id El ID del libro que se desea obtener.
     * @return Un objeto LibroDto que representa el libro si es encontrado.
     * @throws ResourceNotFoundException Si el libro no es encontrado en la base de datos.
     */
    public LibroDto getLibroById(Long id) {
        Optional<Libro> libroOpt = libroRepository.findById(id);

        if (!libroOpt.isPresent()) {
            throw new ResourceNotFoundException("Libro no encontrado con el ID: " + id);
        }

        return libroMapper.libroToLibroDto(libroOpt.get());

    }

     /**
     * Crea un nuevo libro en el sistema.
     * 
     * @param libroDto El objeto LibroDto con los datos del libro a crear.
     * @return El libro recién creado en formato LibroDto.
     */
    public LibroDto crearLibro(LibroDto libroDto) {
        Libro libro = libroMapper.libroDtoToLibro(libroDto);
        libroRepository.save(libro);

        return libroMapper.libroToLibroDto(libro);
    }

    /**
     * Actualiza los datos de un libro existente en el sistema.
     * 
     * @param libroDto El objeto LibroDto con los datos actualizados del libro.
     * @param id       El ID del libro que se desea actualizar.
     * @return El libro actualizado en formato LibroDto.
     * @throws ResourceNotFoundException Si el libro no es encontrado en la base de datos.
     */
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

        libroRepository.save(libro);
        return libroMapper.libroToLibroDto(libro);
    }


    /**
     * Elimina un libro del sistema por su ID.
     * 
     * @param id El ID del libro que se desea eliminar.
     * @return El libro eliminado en formato LibroDto.
     * @throws ResourceNotFoundException Si el libro no es encontrado en la base de datos.
     */
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
