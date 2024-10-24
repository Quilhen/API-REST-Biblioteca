package com.davidgt.springboot.app.springboot_biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.davidgt.springboot.app.springboot_biblioteca.entity.EstadoReserva;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Libro;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Reserva;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Usuario;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long>{

    List<Reserva> findByLibroAndEstadoReserva(Libro libro , EstadoReserva estadoReserva);
    List<Reserva> findByUsuarioAndEstadoReserva(Usuario usuario , EstadoReserva estadoReserva);

}
