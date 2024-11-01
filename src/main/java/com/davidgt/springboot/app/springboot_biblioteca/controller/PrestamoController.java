package com.davidgt.springboot.app.springboot_biblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.davidgt.springboot.app.springboot_biblioteca.dto.PrestamoDto;
import com.davidgt.springboot.app.springboot_biblioteca.service.PrestamoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controlador REST para gestionar las operaciones relacionadas con préstamos.
 * Ofrece endpoints para crear, obtener, devolver y listar todos los préstamos.
 * Esta clase interactúa con el servicio PrestamoService para manejar la lógica
 * de negocio.
 * 
 * @author David GT
 */
@RestController
@RequestMapping("/api/prestamos")
@Tag(name = "Prestamos", description = "Operaciones relacionadas con los prestamos.")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    /**
     * Endpoint para obtener una lista de todos los préstamos en el sistema.
     * Requiere autenticación con rol ADMIN.
     * 
     * @return Lista de objetos PrestamoDto que representan los préstamos.
     */
    @Operation(summary = "Obtener todos los préstamos", description = "Devuelve una lista de todos los préstamos registrados.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de préstamos obtenida con éxito"),
            @ApiResponse(responseCode = "403", description = "No tienes permisos para acceder a este recurso"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<PrestamoDto> getAllPrestamos() {
        return prestamoService.getAllPrestamos();
    }

    /**
     * Endpoint para obtener los detalles de un préstamo específico por su ID.
     * Requiere autenticación con rol ADMIN.
     * 
     * @param id El ID del préstamo que se desea buscar.
     * @return Un objeto PrestamoDto con los detalles del préstamo si es encontrado.
     */
    @Operation(summary = "Obtener un préstamo por ID", description = "Devuelve los detalles de un préstamo específico por su ID.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo obtenido con éxito"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado"),
            @ApiResponse(responseCode = "403", description = "No tienes permisos para acceder a este recurso"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getPrestamoById(@PathVariable Long id) {
        PrestamoDto prestamo = prestamoService.getPrestamoById(id);
        return ResponseEntity.status(HttpStatus.OK).body(prestamo);
    }

    /**
     * Endpoint para crear un nuevo préstamo en el sistema.
     * Requiere autenticación con rol ADMIN.
     * 
     * @param prestamoDto El objeto PrestamoDto con los datos del préstamo a crear.
     * @return El préstamo recién creado en formato PrestamoDto.
     */
    @Operation(summary = "Crear un nuevo préstamo", description = "Crea un nuevo préstamo en el sistema.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Préstamo creado con éxito"),
            @ApiResponse(responseCode = "403", description = "No tienes permisos para crear un préstamo"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> crearPrestamo(@Valid @RequestBody PrestamoDto prestamoDto) {
        PrestamoDto prestamo = prestamoService.gestionarPrestamo(prestamoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(prestamo);
    }

    /**
     * Endpoint para marcar un préstamo como devuelto.
     * Requiere autenticación con rol ADMIN.
     * 
     * @param id El ID del préstamo que se desea marcar como devuelto.
     * @return Respuesta de éxito si el prestamo de ha devuelto.
     */
    @Operation(summary = "Marcar préstamo como devuelto", description = "Marca un préstamo existente como devuelto.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo devuelto con éxito"),
            @ApiResponse(responseCode = "403", description = "No tienes permisos para devolver un préstamo"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/devolver")
    public ResponseEntity<?> devolverPrestamo(@PathVariable Long id) {
         prestamoService.devolverPrestamo(id);
        return ResponseEntity.noContent().build();
    }


    /**
     * Endpoint para marcar un préstamo como perdido.
     * Requiere autenticación con rol ADMIN.
     * 
     * @param id El ID del préstamo que se desea marcar como perdido.
     * @return El préstamo actualizado en formato PrestamoDto.
     */
    @Operation(summary = "Marcar préstamo como perdido", description = "Marca un préstamo existente como perdido.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado del préstamo cambiado con éxito"),
            @ApiResponse(responseCode = "403", description = "No tienes permisos para cambiar el estado del préstamo"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/perdido")
    public ResponseEntity<?> cambiarEstado( @PathVariable Long id) {
        PrestamoDto prestamo = prestamoService.cambiarEstado(id);
        return ResponseEntity.status(HttpStatus.OK).body(prestamo);
    }

}
