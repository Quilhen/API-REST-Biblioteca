package com.davidgt.springboot.app.springboot_biblioteca.dto;

import java.time.LocalDate;

import com.davidgt.springboot.app.springboot_biblioteca.entity.EstadoReserva;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Libro;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Usuario;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class ReservaDto {


    private Long id;
    private Usuario usuario;
    private Libro libro;
    private LocalDate fechaReserva;

    @Enumerated(EnumType.STRING)
    private EstadoReserva estadoReserva;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Libro getLibro() {
        return libro;
    }
    public void setLibro(Libro libro) {
        this.libro = libro;
    }
    public LocalDate getFechaReserva() {
        return fechaReserva;
    }
    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }
    public EstadoReserva getEstadoReserva() {
        return estadoReserva;
    }
    public void setEstadoReserva(EstadoReserva estadoReserva) {
        this.estadoReserva = estadoReserva;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
        result = prime * result + ((libro == null) ? 0 : libro.hashCode());
        result = prime * result + ((fechaReserva == null) ? 0 : fechaReserva.hashCode());
        result = prime * result + ((estadoReserva == null) ? 0 : estadoReserva.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ReservaDto other = (ReservaDto) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (usuario == null) {
            if (other.usuario != null)
                return false;
        } else if (!usuario.equals(other.usuario))
            return false;
        if (libro == null) {
            if (other.libro != null)
                return false;
        } else if (!libro.equals(other.libro))
            return false;
        if (fechaReserva == null) {
            if (other.fechaReserva != null)
                return false;
        } else if (!fechaReserva.equals(other.fechaReserva))
            return false;
        if (estadoReserva != other.estadoReserva)
            return false;
        return true;
    }

    

}
