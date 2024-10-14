package com.davidgt.springboot.app.springboot_biblioteca.repository;

import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.davidgt.springboot.app.springboot_biblioteca.entity.Libro;

import jakarta.persistence.criteria.Predicate;


/**
 * La clase LibroSpecification proporciona métodos estáticos para generar
 * especificaciones de búsqueda en la entidad Libro.
 * 
 * Las especificaciones se utilizan para filtrar libros en función de parámetros
 * como título, autor, año de publicación y género.
 */
public class LibroSpecification {



     /**
     * Genera una especificación de búsqueda que permite filtrar libros
     * según varios parámetros. Si un parámetro es null o vacío, no se incluirá en el filtro.
     * 
     * @param titulo        el título del libro a buscar (puede ser una coincidencia parcial).
     * @param autor         el autor del libro a buscar (puede ser una coincidencia parcial).
     * @param añoPublicacion el año de publicación del libro a buscar.
     * @param genero        el género del libro a buscar (puede ser una coincidencia parcial).
     * @return una especificación que filtra los libros en base a los parámetros proporcionados.
     */
    public static Specification<Libro> filtrarPorParametros(String titulo, String autor, LocalDate añoPublicacion, String genero) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por título si no es nulo ni vacío
            if (titulo != null && !titulo.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("titulo"), "%" + titulo + "%"));
            }

            // Filtro por autor si no es nulo ni vacío
            if (autor != null && !autor.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("autor"), "%" + autor + "%"));
            }

            // Filtro por año de publicación si está definido
            if (añoPublicacion != null) {
                predicates.add(criteriaBuilder.equal(root.get("añoPublicacion"), añoPublicacion ));
            }

            // Filtro por género si no es nulo ni vacío
            if (genero != null && !genero.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("genero"), "%" + genero + "%"));
            }

            // Combina todos los predicados con un AND lógico
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }

}
