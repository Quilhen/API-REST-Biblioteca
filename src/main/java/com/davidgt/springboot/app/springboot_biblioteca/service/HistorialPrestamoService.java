package com.davidgt.springboot.app.springboot_biblioteca.service;

import java.util.List;
import java.time.LocalDate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.davidgt.springboot.app.springboot_biblioteca.entity.EstadoPrestamo;
import com.davidgt.springboot.app.springboot_biblioteca.entity.HistorialPrestamo;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Prestamo;
import com.davidgt.springboot.app.springboot_biblioteca.repository.HistorialPrestamosRepository;

@Service
public class HistorialPrestamoService {


    @Autowired
    private HistorialPrestamosRepository historialPrestamosRepository;

    /**
     * Obtiene una lista de todos los prestamos guardados en el historial
     * 
     * @return Una lista con los prestamos almacenados en el historial
     */
    public List<HistorialPrestamo> getAllHistorialPrestamos(){
        return historialPrestamosRepository.findAll();
    }
    
    
    /**
     * Crea un registro de historial a partir de un préstamo dado. 
     * Este método genera un objeto de historial copiando los detalles 
     * del préstamo, incluyendo el usuario, libro, fechas y estado.
     * 
     *  Si el préstamo está vencido, asigna una multa al historial.
     * 
     * @param prestamo El prestamo que se va a guardar en el historial
     */
    @Transactional
    public void crearHistorialPrestamo(Prestamo prestamo){
        HistorialPrestamo historialPrestamo = new HistorialPrestamo();
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
