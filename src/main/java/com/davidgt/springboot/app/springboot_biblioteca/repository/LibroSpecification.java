package com.davidgt.springboot.app.springboot_biblioteca.repository;

import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.davidgt.springboot.app.springboot_biblioteca.entity.Libro;

import jakarta.persistence.criteria.Predicate;

public class LibroSpecification {

    public static Specification<Libro> filtrarPorParametros(String titulo, String autor, LocalDate añoPublicacion, String genero) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (titulo != null && !titulo.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("titulo"), "%" + titulo + "%"));
            }

            if (autor != null && !autor.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("autor"), "%" + autor + "%"));
            }

            if (añoPublicacion != null) {
                predicates.add(criteriaBuilder.equal(root.get("añoPublicacion"), añoPublicacion ));
            }

            if (genero != null && !genero.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("genero"), "%" + genero + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }

}
