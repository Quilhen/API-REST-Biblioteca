package com.davidgt.springboot.app.springboot_biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.davidgt.springboot.app.springboot_biblioteca.entity.Prestamo;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long>{


}
