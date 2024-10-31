package com.davidgt.springboot.app.springboot_biblioteca.service;

import java.util.*;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davidgt.springboot.app.springboot_biblioteca.entity.EstadoReserva;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Libro;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Reserva;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Usuario;
import com.davidgt.springboot.app.springboot_biblioteca.exception.ResourceNotFoundException;
import com.davidgt.springboot.app.springboot_biblioteca.repository.LibroRepository;
import com.davidgt.springboot.app.springboot_biblioteca.repository.ReservaRepository;
import com.davidgt.springboot.app.springboot_biblioteca.repository.UsuarioRepository;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Reserva crearReserva(Long usuarioId, Long libroId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        Optional<Libro> libroOpt = libroRepository.findById(libroId);

        if (!usuarioOpt.isPresent()) {
            throw new ResourceNotFoundException("El usuario no existe");
        }

        if (!libroOpt.isPresent()) {
            throw new ResourceNotFoundException("El libro no existe");
        }

        Libro libro = libroOpt.get();
        if (libro.getCopiasDisponibles() > 0) {
            throw new IllegalStateException("El libro está disponible, no se puede reservar");
        }

        Reserva reserva = new Reserva();
        reserva.setUsuario(usuarioOpt.get());
        reserva.setLibro(libro);
        reserva.setFechaReserva(LocalDate.now());
        reserva.setEstadoReserva(EstadoReserva.PENDIENTE);

        return reservaRepository.save(reserva);

    }

    public List<Reserva> getReservasPendientesByLibro(Long libroId) {
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado"));

        return reservaRepository.findByLibroAndEstadoReserva(libro, EstadoReserva.PENDIENTE);
    }

    public void completarReserva(Reserva reserva) {
        reserva.setEstadoReserva(EstadoReserva.COMPLETADA);
        reservaRepository.save(reserva);
    }

}
