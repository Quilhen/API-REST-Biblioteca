package com.davidgt.springboot.app.springboot_biblioteca.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentCaptor.forClass;

import com.davidgt.springboot.app.springboot_biblioteca.entity.EstadoPrestamo;
import com.davidgt.springboot.app.springboot_biblioteca.entity.HistorialPrestamo;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Prestamo;
import com.davidgt.springboot.app.springboot_biblioteca.repository.HistorialPrestamosRepository;

public class HistorialPrestamoServiceTest {

    @Mock
    private HistorialPrestamosRepository historialPrestamosRepository;

    @InjectMocks
    private HistorialPrestamoService historialPrestamoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test para verificar que se recuperan todos los préstamos del historial.
     */
    @Test
    void testGetAllHistorialPrestamos_DeberiaRetornarListaHistorial() {
        HistorialPrestamo historial1 = new HistorialPrestamo();
        HistorialPrestamo historial2 = new HistorialPrestamo();
        when(historialPrestamosRepository.findAll()).thenReturn(List.of(historial1, historial2));

        List<HistorialPrestamo> result = historialPrestamoService.getAllHistorialPrestamos();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(historialPrestamosRepository).findAll();
    }

    /**
     * Test para verificar que se crea un historial de préstamo con estado DEVUELTO
     * y sin multa cuando el préstamo está en estado ACTIVO y no está vencido.
     */
    @Test
    void testCrearHistorialPrestamo_DeberiaCrearHistorialDevueltoSinMulta() {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setFechaPrestamo(LocalDate.now().minusDays(5));
        prestamo.setFechaDevolucionPrevista(LocalDate.now().plusDays(5));
        prestamo.setEstado(EstadoPrestamo.ACTIVO);

        historialPrestamoService.crearHistorialPrestamo(prestamo);

        ArgumentCaptor<HistorialPrestamo> captor = forClass(HistorialPrestamo.class);
        verify(historialPrestamosRepository).save(captor.capture());

        HistorialPrestamo historialCapturado = captor.getValue();
        assertNotNull(historialCapturado);
        assertEquals(EstadoPrestamo.DEVUELTO, historialCapturado.getEstadoFinal());
        assertEquals(0.0, historialCapturado.getMulta());
    }

    /**
     * Test para verificar que se crea un historial con multa cuando el préstamo
     * está vencido.
     */
    @Test
    void testCrearHistorialPrestamo_DeberiaCrearHistorialConMultaSiVencido() {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setFechaPrestamo(LocalDate.now().minusDays(10));
        prestamo.setFechaDevolucionPrevista(LocalDate.now().minusDays(1));
        prestamo.setEstado(EstadoPrestamo.ACTIVO);

        historialPrestamoService.crearHistorialPrestamo(prestamo);

        ArgumentCaptor<HistorialPrestamo> captor = forClass(HistorialPrestamo.class);
        verify(historialPrestamosRepository).save(captor.capture());

        HistorialPrestamo historialCapturado = captor.getValue();
        assertNotNull(historialCapturado);
        assertEquals(EstadoPrestamo.DEVUELTO, historialCapturado.getEstadoFinal());
        assertEquals(2.30, historialCapturado.getMulta());
    }

    /**
     * Test para verificar que se crea un historial de préstamo con el estado final
     * correcto según el estado del préstamo al momento de su devolución.
     */
    @Test
    void testCrearHistorialPrestamo_DeberiaCrearHistorialConEstadoFinalCorrecto() {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setFechaPrestamo(LocalDate.now().minusDays(5));
        prestamo.setFechaDevolucionPrevista(LocalDate.now().plusDays(5));
        prestamo.setEstado(EstadoPrestamo.PERDIDO);

        historialPrestamoService.crearHistorialPrestamo(prestamo);

        ArgumentCaptor<HistorialPrestamo> captor = forClass(HistorialPrestamo.class);
        verify(historialPrestamosRepository).save(captor.capture());

        HistorialPrestamo historialCapturado = captor.getValue();
        assertNotNull(historialCapturado);
        assertEquals(EstadoPrestamo.PERDIDO, historialCapturado.getEstadoFinal()); 
        assertEquals(0.0, historialCapturado.getMulta());
    }

}
