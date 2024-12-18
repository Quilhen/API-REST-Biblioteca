package com.davidgt.springboot.app.springboot_biblioteca.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.davidgt.springboot.app.springboot_biblioteca.entity.Rol;

@Repository
public interface RolRepository extends CrudRepository<Rol, Long> {
    Optional<Rol> findByNombre(String name);
}
