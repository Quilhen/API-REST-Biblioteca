package com.davidgt.springboot.app.springboot_biblioteca.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.davidgt.springboot.app.springboot_biblioteca.entity.EstadoReserva;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Libro;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Reserva;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Usuario;
import com.davidgt.springboot.app.springboot_biblioteca.exception.ResourceNotFoundException;
import com.davidgt.springboot.app.springboot_biblioteca.repository.LibroRepository;
import com.davidgt.springboot.app.springboot_biblioteca.repository.ReservaRepository;
import com.davidgt.springboot.app.springboot_biblioteca.repository.UsuarioRepository;

/**
 * Pruebas unitarias para la clase ReservaService:
 * - Valida la lógica de reservas en el sistema de biblioteca.
 * - Asegura que el servicio maneje correctamente la creación, obtención y
 * actualización de reservas, incluyendo casos excepcionales.
 */
public class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private LibroRepository libroRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ReservaService reservaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba que verifica la creación de una reserva cuando no hay copias
     * disponibles.
     */
    @Test
    void testCrearReserva_DeberiaCrearReservaCuandoNoHayCopiasDisponibles() {
        Long usuarioId = 1L;
        Long libroId = 1L;

        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);

        Libro libro = new Libro();
        libro.setId(libroId);
        libro.setCopiasDisponibles(0); // Sin copias disponibles para permitir la reserva

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(libroRepository.findById(libroId)).thenReturn(Optional.of(libro));
        when(reservaRepository.save(any(Reserva.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Reserva reserva = reservaService.crearReserva(usuarioId, libroId);

        assertNotNull(reserva);
        assertEquals(usuario, reserva.getUsuario());
        assertEquals(libro, reserva.getLibro());
        assertEquals(EstadoReserva.PENDIENTE, reserva.getEstadoReserva());
        verify(reservaRepository).save(reserva);
    }

    /**
     * Prueba que verifica que se lance una excepción si el usuario no existe.
     */
    @Test
    void testCrearReserva_DeberiaLanzarResourceNotFoundExceptionCuandoUsuarioNoExiste() {
        Long usuarioId = 1L;
        Long libroId = 1L;

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> reservaService.crearReserva(usuarioId, libroId));

        verify(usuarioRepository).findById(usuarioId);
    }

    /**
     * Prueba que verifica que se lance una excepción si el libro no existe.
     */
    @Test
    void testCrearReserva_DeberiaLanzarResourceNotFoundExceptionCuandoLibroNoExiste() {
        Long usuarioId = 1L;
        Long libroId = 1L;

        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(libroRepository.findById(libroId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> reservaService.crearReserva(usuarioId, libroId));
        verify(usuarioRepository).findById(usuarioId);
        verify(libroRepository).findById(libroId);
        verifyNoInteractions(reservaRepository);
    }

    /**
     * Prueba que verifica que no se permita la reserva cuando hay copias
     * disponibles.
     */
    @Test
    void testCrearReserva_DeberiaLanzarIllegalStateExceptionCuandoHayCopiasDisponibles() {
        Long usuarioId = 1L;
        Long libroId = 1L;

        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);

        Libro libro = new Libro();
        libro.setId(libroId);
        libro.setCopiasDisponibles(1); // Con copias disponibles, lo que no permite reservar

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(libroRepository.findById(libroId)).thenReturn(Optional.of(libro));

        assertThrows(IllegalStateException.class, () -> reservaService.crearReserva(usuarioId, libroId));
        verify(usuarioRepository).findById(usuarioId);
        verify(libroRepository).findById(libroId);
        verifyNoInteractions(reservaRepository);
    }

    /**
     * Prueba que verifica la obtención de reservas pendientes para un libro
     * específico.
     */
    @Test
    void testGetReservasPendientesByLibro_DeberiaRetornarReservasPendientes() {
        Long libroId = 1L;

        Libro libro = new Libro();
        libro.setId(libroId);

        Reserva reservaPendiente = new Reserva();
        reservaPendiente.setEstadoReserva(EstadoReserva.PENDIENTE);

        when(libroRepository.findById(libroId)).thenReturn(Optional.of(libro));
        when(reservaRepository.findByLibroAndEstadoReserva(libro, EstadoReserva.PENDIENTE))
                .thenReturn(List.of(reservaPendiente));

        List<Reserva> reservasPendientes = reservaService.getReservasPendientesByLibro(libroId);

        assertNotNull(reservasPendientes);
        assertEquals(1, reservasPendientes.size());
        assertEquals(EstadoReserva.PENDIENTE, reservasPendientes.get(0).getEstadoReserva());
        verify(libroRepository).findById(libroId);
        verify(reservaRepository).findByLibroAndEstadoReserva(libro, EstadoReserva.PENDIENTE);
    }

    /**
     * Prueba que verifica la excepción lanzada cuando se busca un libro inexistente
     * para obtener reservas pendientes.
     */
    @Test
    void testGetReservasPendientesByLibro_DeberiaLanzarResourceNotFoundExceptionCuandoLibroNoExiste() {
        Long libroId = 1L;

        when(libroRepository.findById(libroId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> reservaService.getReservasPendientesByLibro(libroId));
        verify(libroRepository).findById(libroId);
        verifyNoInteractions(reservaRepository);
    }

    /**
     * Prueba que verifica que el estado de una reserva pase a COMPLETADA.
     */
    @Test
    void testCompletarReserva_DeberiaCambiarEstadoReservaACompletada() {
        Reserva reserva = new Reserva();
        reserva.setEstadoReserva(EstadoReserva.PENDIENTE);

        reservaService.completarReserva(reserva);

        assertEquals(EstadoReserva.COMPLETADA, reserva.getEstadoReserva());
        verify(reservaRepository).save(reserva);
    }

}
