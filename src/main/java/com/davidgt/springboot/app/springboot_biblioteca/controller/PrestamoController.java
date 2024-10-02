package com.davidgt.springboot.app.springboot_biblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import jakarta.validation.Valid;

/**
 * Controlador REST para gestionar las operaciones relacionadas con préstamos.
 * Ofrece endpoints para crear, obtener, devolver y listar todos los préstamos.
 * 
 * Esta clase interactúa con el servicio PrestamoService para manejar la lógica de negocio.
 * 
 * @author David GT
 */

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;


    /**
     * Obtiene una lista de todos los préstamos en el sistema.
     * 
     * @return Lista de objetos PrestamoDto que representan los préstamos.
     */
    @GetMapping
    public List<PrestamoDto> getAllPrestamos() {
        return prestamoService.getAllPrestamos();
    }

    /**
     * Obtiene los detalles de un préstamo especifico por su ID.
     * 
     * @param id El ID del préstamo que se desea buscar.
     * @return Un objeto PrestamoDto con los detalles del prestamos si es encontrado.
     */        
    @GetMapping("/{id}")
    public ResponseEntity<?> getPrestamoById(@PathVariable Long id) {
        PrestamoDto prestamo = prestamoService.getPrestamoById(id);
        return ResponseEntity.status(HttpStatus.OK).body(prestamo);
    }

    /**
     * Crea un nuevo préstamo en el sistema.
     * 
     * @param prestamoDto El objeto PrestamoDto con los datos del préstamo a crear.
     * @return El préstamo recién creado en formato PrestamoDto.
     */
    @PostMapping
    public ResponseEntity<?> crearPrestamo(@Valid @RequestBody PrestamoDto prestamoDto) {
        PrestamoDto prestamo = prestamoService.crearPrestamo(prestamoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(prestamo);
    }

    /**
     * Marca un préstamo como devuelto.
     * 
     * @param id El ID del préstamo que se desea marcar como devuelto.
     * @return El préstamo actualizado en formato PrestamoDto.
     */
    @PutMapping("/{id}/devolver")
    public ResponseEntity<?> devolverPrestamo(@Valid @PathVariable Long id) {
        PrestamoDto prestamo = prestamoService.devolverPrestamo(id);
        return ResponseEntity.status(HttpStatus.OK).body(prestamo);
    }

}
