package com.davidgt.springboot.app.springboot_biblioteca.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.davidgt.springboot.app.springboot_biblioteca.entity.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long>, JpaSpecificationExecutor<Libro>{

}
