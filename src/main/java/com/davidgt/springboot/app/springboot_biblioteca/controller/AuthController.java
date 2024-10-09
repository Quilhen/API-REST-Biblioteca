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

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Login", description = "Aqui se realiza el login.")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto authRequestDto) {
        try {
            // Autenticamos al usuario con su nombre de usuario y contraseña
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDto.getNombreUsuario(),
                            authRequestDto.getPassword()));

            // Establecer la autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generar el token JWT
            String jwtToken = jwtUtil.generateToken(authentication);

            // Devolver el token en la respuesta
            return ResponseEntity.ok(new AuthResponseDto(jwtToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

}
