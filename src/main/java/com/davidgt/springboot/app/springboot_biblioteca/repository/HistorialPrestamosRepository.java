package com.davidgt.springboot.app.springboot_biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.davidgt.springboot.app.springboot_biblioteca.entity.HistorialPrestamo;

@Repository
public interface HistorialPrestamosRepository extends JpaRepository<HistorialPrestamo, Long>{

}
