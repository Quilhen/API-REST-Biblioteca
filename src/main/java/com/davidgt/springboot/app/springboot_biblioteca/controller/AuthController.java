package com.davidgt.springboot.app.springboot_biblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davidgt.springboot.app.springboot_biblioteca.dto.AuthRequestDto;
import com.davidgt.springboot.app.springboot_biblioteca.dto.AuthResponseDto;
import com.davidgt.springboot.app.springboot_biblioteca.service.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


/**
 * Controlador REST para la autenticación de usuarios.
 * Este controlador gestiona el inicio de sesión de los usuarios y genera el token JWT.
 * 
 * @author David GT
 */
@RestController
@Tag(name = "Login", description = "Aquí se realiza el login.")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


    /**
     * Endpoint para realizar el login de un usuario.
     * El usuario debe proporcionar un nombre de usuario y contraseña válidos para obtener un token JWT.
     * 
     * @param authRequestDto Objeto que contiene las credenciales del usuario (nombre de usuario y contraseña).
     * @return Una respuesta que contiene el token JWT si las credenciales son válidas.
     */
    @Operation(summary = "Login de usuario", description = "Autentica al usuario y genera un token JWT.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login exitoso, se devuelve el token JWT"),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequestDto authRequestDto) {
        try {
            // Autentica al usuario con su nombre de usuario y contraseña
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDto.getNombreUsuario(),
                            authRequestDto.getPassword()));

            // Establece la autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Genera el token JWT
            String jwtToken = jwtUtil.generateToken(authentication);

            // Devuelve el token en la respuesta
            return ResponseEntity.ok(new AuthResponseDto(jwtToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

}
