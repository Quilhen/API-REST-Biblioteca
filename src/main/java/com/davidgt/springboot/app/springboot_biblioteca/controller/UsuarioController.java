package com.davidgt.springboot.app.springboot_biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;


/**
 * Controlador REST para gestionar las operaciones relacionadas con usuarios.
 * Ofrece endpoints para listar usuarios, obtener préstamos activos y registrar nuevos usuarios.
 */
@RestController
@Tag(name = "Usuarios", description = "Operaciones relacionadas con los usuarios.")
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    /**
     * Endpoint para obtener una lista de todos los usuarios.
     * Requiere autenticación con rol ADMIN.
     * 
     * @return Lista de objetos UsuarioDto que representan los usuarios.
     */
    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve una lista de todos los usuarios registrados.", 
               security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida con éxito"),
        @ApiResponse(responseCode = "403", description = "No tienes permisos para acceder a este recurso")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UsuarioDto> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    
    /**
     * Endpoint para obtener los préstamos activos de un usuario específico por su ID.
     * Requiere autenticación con rol ADMIN.
     * 
     * @param id El ID del usuario.
     * @return Lista de préstamos activos del usuario.
     */
    @Operation(summary = "Obtener préstamos activos de un usuario", description = "Devuelve la lista de préstamos activos de un usuario específico.", 
               security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Préstamos activos obtenidos con éxito"),
        @ApiResponse(responseCode = "403", description = "No tienes permisos para acceder a este recurso"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/prestamos")
    public ResponseEntity<?> getUsuariosPrestamosActivos(@PathVariable Long id) {
        List<PrestamoDto> prestamos = usuarioService.getUsuariosPrestamos(id);
        return ResponseEntity.status(HttpStatus.OK).body(prestamos);

    }


     /**
     * Endpoint para crear un nuevo usuario con rol de admin.
     * Es accesible sin autenticación(solo para hacer el testeo mas rápido y facil).
     * 
     * @param usuario Objeto Usuario con los datos del nuevo usuario a crear.
     * @return El usuario recién creado.
     */
    @Operation(summary = "Crear un nuevo usuario con rol de admin", description = "Crea un nuevo usuario administrador en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario admin creado con éxito")
    })
    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }


     /**
     * Endpoint para registrar un nuevo usuario.
     * Este registro crea un usuario no administrador.
     * 
     * @param usuario Objeto Usuario con los datos del nuevo usuario a registrar.
     * @return El usuario registrado.
     */
    @Operation(summary = "Crear un nuevo usuario", description = "Crea un nuevo usuario no administrador en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario registrado con éxito")
    })
    @PostMapping("/registrarse")
    public ResponseEntity<?> registro(@RequestBody Usuario usuario) {
        usuario.setAdmin(false);
        return crearUsuario(usuario);
    }

}
