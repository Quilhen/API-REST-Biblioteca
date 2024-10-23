package com.davidgt.springboot.app.springboot_biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davidgt.springboot.app.springboot_biblioteca.entity.HistorialPrestamo;
import com.davidgt.springboot.app.springboot_biblioteca.service.HistorialPrestamoService;

import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@Tag(name = "Historial de prestamos", description = "Muestra el historial de todos los prestamos hechos.")
@RequestMapping("/api/historialPrestamos")
public class HistorialPrestamoController {


    @Autowired
    private HistorialPrestamoService historialPrestamoService;

    @GetMapping
    public List<HistorialPrestamo> getAllHistorial(){
        return historialPrestamoService.getAllHistorialPrestamos();
    } 

}
