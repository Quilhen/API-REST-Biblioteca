package com.davidgt.springboot.app.springboot_biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davidgt.springboot.app.springboot_biblioteca.dto.PrestamoDto;
import com.davidgt.springboot.app.springboot_biblioteca.dto.UsuarioDto;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Usuario;
import com.davidgt.springboot.app.springboot_biblioteca.service.UsuarioService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Usuarios", description = "Operaciones relacionadas con los usuarios.")
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioDto> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/{id}/prestamos")
    public ResponseEntity<?> getUsuariosPrestamosActivos(@PathVariable Long id) {
        List<PrestamoDto> prestamos = usuarioService.getUsuariosPrestamos(id);
        return ResponseEntity.status(HttpStatus.OK).body(prestamos);

    }

    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }

    @PostMapping("/registrarse")
    public ResponseEntity<?> registro(@RequestBody Usuario usuario) {
        usuario.setAdmin(false);
        return crearUsuario(usuario);
    }

}
