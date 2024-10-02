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

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping
    public List<PrestamoDto> getAllPrestamos() {
        return prestamoService.getAllPrestamos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPrestamoById(@PathVariable Long id) {
        PrestamoDto prestamo = prestamoService.getPrestamoById(id);
        return ResponseEntity.status(HttpStatus.OK).body(prestamo);
    }

    @PostMapping
    public ResponseEntity<?> crearPrestamo(@Valid @RequestBody PrestamoDto prestamoDto) {
        PrestamoDto prestamo = prestamoService.crearPrestamo(prestamoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(prestamo);
    }

    @PutMapping("/{id}/devolver")
    public ResponseEntity<?> devolverPrestamo(@Valid @PathVariable Long id) {
        PrestamoDto prestamo = prestamoService.devolverPrestamo(id);
        return ResponseEntity.status(HttpStatus.OK).body(prestamo);
    }

}
