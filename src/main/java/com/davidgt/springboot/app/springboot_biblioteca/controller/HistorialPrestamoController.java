package com.davidgt.springboot.app.springboot_biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davidgt.springboot.app.springboot_biblioteca.entity.HistorialPrestamo;
import com.davidgt.springboot.app.springboot_biblioteca.service.HistorialPrestamoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@Tag(name = "Historial de prestamos", description = "Muestra el historial de todos los prestamos hechos.")
@RequestMapping("/api/historialPrestamos")
public class HistorialPrestamoController {


    @Autowired
    private HistorialPrestamoService historialPrestamoService;


    /**
     * Endpoint para obtener una lista de todos los préstamos registrados en el historial.
     * 
     * @return Lista de objetos `HistorialPrestamo` que representan los préstamos históricos.
     */
    @Operation(summary = "Obtener todos los préstamos del historial", 
               description = "Devuelve una lista completa de todos los préstamos registrados en el historial.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial obtenido con éxito"),
            @ApiResponse(responseCode = "403", description = "No tienes permisos para acceder a este recurso"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<HistorialPrestamo> getAllHistorial(){
        return historialPrestamoService.getAllHistorialPrestamos();
    } 

}
