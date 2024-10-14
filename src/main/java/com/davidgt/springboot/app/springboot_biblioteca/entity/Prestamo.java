package com.davidgt.springboot.app.springboot_biblioteca.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;



@Entity
@Table(name="prestamos")

public class Prestamo {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;

    private LocalDate fechaPrestamo;

    private LocalDate fechaDevolucion;

    
    public Prestamo() {
    }

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

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
        result = prime * result + ((libro == null) ? 0 : libro.hashCode());
        result = prime * result + ((fechaPrestamo == null) ? 0 : fechaPrestamo.hashCode());
        result = prime * result + ((fechaDevolucion == null) ? 0 : fechaDevolucion.hashCode());
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
        Prestamo other = (Prestamo) obj;
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
        if (fechaPrestamo == null) {
            if (other.fechaPrestamo != null)
                return false;
        } else if (!fechaPrestamo.equals(other.fechaPrestamo))
            return false;
        if (fechaDevolucion == null) {
            if (other.fechaDevolucion != null)
                return false;
        } else if (!fechaDevolucion.equals(other.fechaDevolucion))
            return false;
        return true;
    }
    
    


}
