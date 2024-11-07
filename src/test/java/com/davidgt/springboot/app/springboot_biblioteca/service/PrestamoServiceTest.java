package com.davidgt.springboot.app.springboot_biblioteca.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.davidgt.springboot.app.springboot_biblioteca.dto.PrestamoDto;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Libro;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Prestamo;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Usuario;
import com.davidgt.springboot.app.springboot_biblioteca.exception.PrestamoDuplicadoException;
import com.davidgt.springboot.app.springboot_biblioteca.exception.ResourceNotFoundException;
import com.davidgt.springboot.app.springboot_biblioteca.mapper.PrestamoMapper;
import com.davidgt.springboot.app.springboot_biblioteca.repository.LibroRepository;
import com.davidgt.springboot.app.springboot_biblioteca.repository.PrestamoRepository;
import com.davidgt.springboot.app.springboot_biblioteca.repository.UsuarioRepository;


/**
 * Pruebas unitarias de PrestamoService:
 * - Valida la creación de préstamo cuando los datos y condiciones son válidas.
 * - Verifica el manejo de errores en escenarios de datos faltantes o inválidos.
 * - Asegura el comportamiento esperado al gestionar préstamos duplicados.
 */
public class PrestamoServiceTest {

    @Mock
    private PrestamoMapper prestamoMapper;
    @Mock
    private PrestamoRepository prestamoRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private LibroRepository libroRepository;
    @InjectMocks
    private PrestamoService prestamoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Utilidades para construir datos de prueba
    private Usuario crearUsuarioConPrestamos(int cantidadPrestamos) {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setPrestamos(
                new ArrayList<>(List.of(new Prestamo(), new Prestamo(), new Prestamo()).subList(0, cantidadPrestamos)));
        return usuario;
    }

    private Libro crearLibroConCopias(int copiasDisponibles) {
        Libro libro = new Libro();
        libro.setId(1L);
        libro.setCopiasDisponibles(copiasDisponibles);
        return libro;
    }

    private PrestamoDto crearPrestamoDto(Usuario usuario, Libro libro) {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setUsuario(usuario);
        prestamoDto.setLibro(libro);
        prestamoDto.setFechaPrestamo(LocalDate.now());
        prestamoDto.setfechaDevolucionPrevista(LocalDate.now().plusDays(7));
        return prestamoDto;
    }

     /**
     * Prueba que verifica la excepción lanzada cuando el usuario no existe.
     */
    @Test
    void testGestionarPrestamo_DeberiaLanzarExcepcionCuandoUsuarioNoExiste() {
        Long usuarioId = 1L;
        PrestamoDto prestamoDto = new PrestamoDto();
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        prestamoDto.setUsuario(usuario);

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> prestamoService.gestionarPrestamo(prestamoDto));
        verify(usuarioRepository).findById(usuarioId);
    }

     /**
     * Prueba que verifica la excepción lanzada cuando se excede el límite de préstamos por usuario.
     */
    @Test
    void testGestionarPrestamo_DeberiaLanzarExcepcionCuandoLimitePrestamosExcedido() {
        Usuario usuario = crearUsuarioConPrestamos(3);
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setUsuario(usuario);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        assertThrows(ResourceNotFoundException.class, () -> prestamoService.gestionarPrestamo(prestamoDto));
        verify(usuarioRepository).findById(1L);
    }


    /**
     * Prueba que valida la excepción lanzada cuando el libro no existe o no tiene copias disponibles.
     */
    @Test
    void testGestionarPrestamo_DeberiaLanzarExcepcionCuandoLibroNoExisteOEstaNoDisponible() {
        Usuario usuario = crearUsuarioConPrestamos(0);
        Libro libro = new Libro();
        libro.setId(1L);
        libro.setCopiasDisponibles(0);

        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setUsuario(usuario);
        prestamoDto.setLibro(libro);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(libroRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> prestamoService.gestionarPrestamo(prestamoDto));
        verify(libroRepository).findById(1L);
    }

     /**
     * Prueba que verifica la excepción lanzada cuando ya existe un préstamo duplicado.
     */
    @Test
    void testGestionarPrestamo_DeberiaLanzarExcepcionCuandoPrestamoDuplicado() {
        Usuario usuario = crearUsuarioConPrestamos(1); // Usuario existente con préstamos
        Libro libro = crearLibroConCopias(1); // Libro con al menos una copia disponible
        PrestamoDto prestamoDto = crearPrestamoDto(usuario, libro);

        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        when(libroRepository.findById(libro.getId())).thenReturn(Optional.of(libro));
        when(prestamoRepository.existsByUsuarioAndLibro(usuario, libro)).thenReturn(true); // Prestamo duplicado

        assertThrows(PrestamoDuplicadoException.class, () -> prestamoService.gestionarPrestamo(prestamoDto));

        verify(prestamoRepository).existsByUsuarioAndLibro(usuario, libro);
    }


    /**
     * Prueba que asegura que el préstamo se guarda en el repositorio si es válido.
     */
    @Test
    void testGestionarPrestamo_DeberiaGuardarPrestamoSiTodoEsValido() {
        Long usuarioId = 1L;
        Long libroId = 1L;

        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        usuario.setPrestamos(new ArrayList<>());
        Libro libro = new Libro();
        libro.setId(libroId);
        libro.setCopiasDisponibles(1);

        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setUsuario(usuario);
        prestamoDto.setLibro(libro);

        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucionPrevista(LocalDate.now().plusDays(7));

         // Configuración de mocks para los valores válidos
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(libroRepository.findById(libroId)).thenReturn(Optional.of(libro));
        when(prestamoRepository.existsByUsuarioAndLibro(usuario, libro)).thenReturn(false);
        when(prestamoRepository.save(any(Prestamo.class))).thenReturn(prestamo);
        when(prestamoMapper.prestamoToPrestamoDto(prestamo)).thenReturn(prestamoDto);

        // Ejecución y validación
        PrestamoDto resultado = prestamoService.gestionarPrestamo(prestamoDto);
        assertNotNull(resultado, "El préstamo debería guardarse correctamente y no ser null");
        assertEquals(prestamoDto, resultado, "El DTO resultante debería coincidir con el DTO esperado");

       // Verifica que se guarden correctamente los datos en el repositorio
        ArgumentCaptor<Prestamo> prestamoCaptor = ArgumentCaptor.forClass(Prestamo.class);
        verify(prestamoRepository).save(prestamoCaptor.capture());
        Prestamo prestamoCapturado = prestamoCaptor.getValue();
        assertNotNull(prestamoCapturado, "El préstamo capturado no debería ser null");
        assertEquals(usuario, prestamoCapturado.getUsuario(), "El usuario en el préstamo debería coincidir");
        assertEquals(libro, prestamoCapturado.getLibro(), "El libro en el préstamo debería coincidir");

        // Verifica el decremento de copias
        verify(libroRepository).save(libro);

    }

}
