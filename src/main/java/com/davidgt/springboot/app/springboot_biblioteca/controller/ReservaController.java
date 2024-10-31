package com.davidgt.springboot.app.springboot_biblioteca.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.davidgt.springboot.app.springboot_biblioteca.entity.Reserva;
import com.davidgt.springboot.app.springboot_biblioteca.service.ReservaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Reservas", description = "Operaciones relacionadas con las reservas.")
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    /**
     * Permite a un usuario reservar un libro.
     * 
     * @param libroId   El ID del libro que se desea reservar.
     * @param usuarioId El ID del usuario que hace la reserva.
     * @return Reserva creada con detalles del libro y usuario.
     */
    @Operation(summary = "Reservar un libro", description = "Permite a un usuario hacer una reserva de un libro.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva creada con éxito"),
            @ApiResponse(responseCode = "404", description = "Libro o usuario no encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/libros/{libroId}/reservar")
    public ResponseEntity<?> reservarLibro(@PathVariable Long libroId, @RequestParam Long usuarioId) {
        Reserva reserva = reservaService.crearReserva(usuarioId, libroId);
        return ResponseEntity.status(HttpStatus.CREATED).body(reserva);
    }

    /**
     * Obtiene las reservas pendientes para un libro específico.
     *
     * @param libroId El ID del libro para el cual se buscan las reservas
     *                pendientes.
     * @return Lista de reservas pendientes para el libro.
     */
    @Operation(summary = "Obtener reservas pendientes", description = "Recupera una lista de reservas pendientes de un libro en especifico.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservas pendientes obtenidas con éxito"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/libros/{libroId}/pendientes")
    public ResponseEntity<List<Reserva>> getReservasPendientes(@PathVariable Long libroId) {
        List<Reserva> reservas = reservaService.getReservasPendientesByLibro(libroId);
        return ResponseEntity.ok(reservas);
    }

}
