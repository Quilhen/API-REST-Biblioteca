package com.davidgt.springboot.app.springboot_biblioteca.service;

import java.util.List;
import java.time.LocalDate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davidgt.springboot.app.springboot_biblioteca.entity.EstadoPrestamo;
import com.davidgt.springboot.app.springboot_biblioteca.entity.HistorialPrestamo;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Prestamo;
import com.davidgt.springboot.app.springboot_biblioteca.repository.HistorialPrestamosRepository;

@Service
public class HistorialPrestamoService {


    @Autowired
    private HistorialPrestamosRepository historialPrestamosRepository;


    public List<HistorialPrestamo> getAllHistorialPrestamos(){
        return historialPrestamosRepository.findAll();
    }
    
    public void crearHistorialPrestamo(Prestamo prestamo){
        HistorialPrestamo historialPrestamo = new HistorialPrestamo();
        historialPrestamo.setId(prestamo.getId());
        historialPrestamo.setUsuario(prestamo.getUsuario());
        historialPrestamo.setLibro(prestamo.getLibro());
        historialPrestamo.setFechaPrestamo(prestamo.getFechaPrestamo());
        historialPrestamo.setFechaDevolucion(LocalDate.now());
        historialPrestamo.setFechaDevolucionPrevista(prestamo.getFechaDevolucionPrevista());

        if(prestamo.getEstado() == EstadoPrestamo.ACTIVO){
            historialPrestamo.setEstadoFinal(EstadoPrestamo.DEVUELTO);
        }else{
            historialPrestamo.setEstadoFinal(prestamo.getEstado());
        }
        
       if(LocalDate.now().isAfter(prestamo.getFechaDevolucionPrevista())){
        historialPrestamo.setMulta(2.30);
       }
        
        historialPrestamosRepository.save(historialPrestamo);
    }


}
