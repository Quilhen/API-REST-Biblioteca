package com.davidgt.springboot.app.springboot_biblioteca.dto;

import java.time.LocalDate;
import java.util.List;

import com.davidgt.springboot.app.springboot_biblioteca.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotNull;

public class LibroDto {

    private Long id;

    @NotNull
    private String titulo;

    @NotNull
    private String autor;

    private LocalDate añoPublicacion;
    private String genero;

    @NotNull
    private boolean disponibilidad;

    @JsonIgnore
    private Usuario usuario;

    @JsonIgnore
    private List<PrestamoDto> prestamos;

    public LibroDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public LocalDate getAñoPublicacion() {
        return añoPublicacion;
    }

    public void setAñoPublicacion(LocalDate añoPublicacion) {
        this.añoPublicacion = añoPublicacion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<PrestamoDto> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<PrestamoDto> prestamos) {
        this.prestamos = prestamos;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
        result = prime * result + ((autor == null) ? 0 : autor.hashCode());
        result = prime * result + ((añoPublicacion == null) ? 0 : añoPublicacion.hashCode());
        result = prime * result + ((genero == null) ? 0 : genero.hashCode());
        result = prime * result + (disponibilidad ? 1231 : 1237);
        result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
        result = prime * result + ((prestamos == null) ? 0 : prestamos.hashCode());
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
        LibroDto other = (LibroDto) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (titulo == null) {
            if (other.titulo != null)
                return false;
        } else if (!titulo.equals(other.titulo))
            return false;
        if (autor == null) {
            if (other.autor != null)
                return false;
        } else if (!autor.equals(other.autor))
            return false;
        if (añoPublicacion == null) {
            if (other.añoPublicacion != null)
                return false;
        } else if (!añoPublicacion.equals(other.añoPublicacion))
            return false;
        if (genero == null) {
            if (other.genero != null)
                return false;
        } else if (!genero.equals(other.genero))
            return false;
        if (disponibilidad != other.disponibilidad)
            return false;
        if (usuario == null) {
            if (other.usuario != null)
                return false;
        } else if (!usuario.equals(other.usuario))
            return false;
        if (prestamos == null) {
            if (other.prestamos != null)
                return false;
        } else if (!prestamos.equals(other.prestamos))
            return false;
        return true;
    }

}
