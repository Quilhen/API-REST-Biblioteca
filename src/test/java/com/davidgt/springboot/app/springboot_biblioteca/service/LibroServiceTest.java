package com.davidgt.springboot.app.springboot_biblioteca.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.davidgt.springboot.app.springboot_biblioteca.dto.LibroDto;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Libro;
import com.davidgt.springboot.app.springboot_biblioteca.exception.ResourceNotFoundException;
import com.davidgt.springboot.app.springboot_biblioteca.mapper.LibroMapper;
import com.davidgt.springboot.app.springboot_biblioteca.repository.LibroRepository;

/**
 * Pruebas unitarias para la clase LibroService:
 * - Valida la gestión de libros en la base de datos.
 * - Asegura el correcto funcionamiento en operaciones de creación, lectura,
 * actualización y eliminación de libros.
 */
public class LibroServiceTest {

    @Mock
    private LibroRepository libroRepository;
    @Mock
    private LibroMapper libroMapper;
    @InjectMocks
    private LibroService libroService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Métodos de ayuda para construir entidades de prueba
    private Libro crearLibro(Long id, String titulo, int copias) {
        Libro libro = new Libro();
        libro.setId(id);
        libro.setTitulo(titulo);
        libro.setCopiasDisponibles(copias);
        return libro;
    }

    private LibroDto crearLibroDto(Long id, String titulo, int copias) {
        LibroDto libroDto = new LibroDto();
        libroDto.setId(id);
        libroDto.setTitulo(titulo);
        libroDto.setCopiasDisponibles(copias);
        return libroDto;
    }


     /**
     * Prueba que verifica la obtención de una lista paginada de libros.
     */
    @Test
    void testGetAllLibros_DeberiaRetornarListaDeLibrosPaginada() {
        Pageable pageable = PageRequest.of(0, 10);
        Libro libro = crearLibro(1L, "Ejemplo de Libro", 5);
        LibroDto libroDto = crearLibroDto(1L, "Ejemplo de Libro", 5);
        Page<Libro> libroPage = new PageImpl<>(List.of(libro));

        when(libroRepository.findAll(pageable)).thenReturn(libroPage);
        when(libroMapper.libroToLibroDto(libro)).thenReturn(libroDto);

        Page<LibroDto> result = libroService.getAllLibros(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Ejemplo de Libro", result.getContent().get(0).getTitulo());
        verify(libroRepository, times(1)).findAll(pageable);
        verify(libroMapper, times(1)).libroToLibroDto(libro);
        verifyNoMoreInteractions(libroRepository, libroMapper);
    }


    /**
     * Prueba que verifica la obtención de un libro específico por ID cuando existe.
     */
    @Test
    void testGetLibroById_DeberiaRetornarLibroCuandoExiste() {
        Libro libro = crearLibro(1L, "Libro Encontrado", 3);
        LibroDto libroDto = crearLibroDto(1L, "Libro Encontrado", 3);

        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));
        when(libroMapper.libroToLibroDto(libro)).thenReturn(libroDto);

        LibroDto result = libroService.getLibroById(1L);

        assertNotNull(result);
        assertEquals("Libro Encontrado", result.getTitulo());
        assertEquals(3, result.getCopiasDisponibles());
        verify(libroRepository).findById(1L);
        verify(libroMapper).libroToLibroDto(libro);
        verifyNoMoreInteractions(libroRepository, libroMapper);
    }

    /**
     * Prueba que verifica la excepción lanzada cuando se intenta obtener un libro inexistente.
     */
    @Test
    void testGetLibroById_DeberiaLanzarResourceNotFoundExceptionCuandoNoExiste() {
        when(libroRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> libroService.getLibroById(1L));
        verify(libroRepository).findById(1L);
        verifyNoMoreInteractions(libroRepository, libroMapper);
    }

    /**
     * Prueba que verifica la creación y almacenamiento de un nuevo libro.
     */
    @Test
    void testCrearLibro_DeberiaGuardarYRetornarLibro() {
        LibroDto libroDto = crearLibroDto(null, "Nuevo Libro", 4);
        Libro libro = crearLibro(null, "Nuevo Libro", 4);

        when(libroMapper.libroDtoToLibro(libroDto)).thenReturn(libro);
        when(libroRepository.save(libro)).thenReturn(libro);
        when(libroMapper.libroToLibroDto(libro)).thenReturn(libroDto);

        LibroDto result = libroService.crearLibro(libroDto);

        ArgumentCaptor<Libro> libroCaptor = ArgumentCaptor.forClass(Libro.class);
        verify(libroRepository).save(libroCaptor.capture());
        assertEquals("Nuevo Libro", libroCaptor.getValue().getTitulo());
        assertEquals(4, libroCaptor.getValue().getCopiasDisponibles());

        assertNotNull(result);
        assertEquals("Nuevo Libro", result.getTitulo());
        assertEquals(4, result.getCopiasDisponibles());
        verify(libroMapper).libroDtoToLibro(libroDto);
        verify(libroMapper).libroToLibroDto(libro);
        verifyNoMoreInteractions(libroRepository, libroMapper);
    }

    /**
     * Prueba que verifica la actualización de un libro existente.
     */
    @Test
    void testActualizarLibro_DeberiaActualizarYRetornarLibroCuandoExiste() {
        LibroDto libroDto = crearLibroDto(1L, "Libro Actualizado", 2);
        Libro libro = crearLibro(1L, "Libro Original", 1);

        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));
        when(libroRepository.save(libro)).thenReturn(libro);
        when(libroMapper.libroToLibroDto(libro)).thenReturn(libroDto);

        LibroDto result = libroService.actualizarLibro(libroDto, 1L);

        ArgumentCaptor<Libro> libroCaptor = ArgumentCaptor.forClass(Libro.class);
        verify(libroRepository).save(libroCaptor.capture());
        assertEquals("Libro Actualizado", libroCaptor.getValue().getTitulo());
        assertEquals(2, libroCaptor.getValue().getCopiasDisponibles());

        assertNotNull(result);
        assertEquals("Libro Actualizado", result.getTitulo());
        assertEquals(2, result.getCopiasDisponibles());

        verify(libroRepository).findById(1L);
        verify(libroMapper).libroToLibroDto(libro);
        verifyNoMoreInteractions(libroRepository);
    }


     /**
     * Prueba que verifica la excepción lanzada al intentar actualizar un libro inexistente.
     */
    @Test
    void testActualizarLibro_DeberiaLanzarResourceNotFoundExceptionCuandoNoExiste() {
        LibroDto libroDto = crearLibroDto(1L, "Libro Inexistente", 0);
        when(libroRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> libroService.actualizarLibro(libroDto, 1L));
        verify(libroRepository).findById(1L);
        verifyNoMoreInteractions(libroRepository, libroMapper);
    }

     /**
     * Prueba que verifica la eliminación de un libro existente.
     */
    @Test
    void testEliminarLibro_DeberiaEliminarLibroCuandoExiste() {
        Libro libro = crearLibro(1L, "Libro a Eliminar", 1);

        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));
        doNothing().when(libroRepository).delete(libro);

        libroService.eliminarLibro(1L);

        verify(libroRepository).findById(1L);
        verify(libroRepository).delete(libro);
        verifyNoMoreInteractions(libroRepository, libroMapper);
    }

    /**
     * Prueba que verifica la excepción lanzada al intentar eliminar un libro inexistente.
     */
    @Test
    void testEliminarLibro_DeberiaLanzarResourceNotFoundExceptionCuandoNoExiste() {
        when(libroRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> libroService.eliminarLibro(1L));
        verify(libroRepository).findById(1L);
        verify(libroRepository, never()).delete(any(Libro.class));
        verifyNoMoreInteractions(libroRepository, libroMapper);
    }

}
