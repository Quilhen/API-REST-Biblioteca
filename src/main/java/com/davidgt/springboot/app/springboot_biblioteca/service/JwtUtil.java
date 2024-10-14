package com.davidgt.springboot.app.springboot_biblioteca.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.ServletException;

import static com.davidgt.springboot.app.springboot_biblioteca.security.TokenJwtConfig.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;


/**
 * Componente de utilidad para gestionar la creación de tokens JWT en la
 * aplicación.
 * Esta clase proporciona métodos para generar tokens JWT con información de
 * autenticación del usuario.
 */
@Component
public class JwtUtil {


    /**
     * Genera un token JWT basado en la autenticación del usuario.
     * El token incluirá el nombre de usuario y sus roles en el campo de claims
     * (reclamaciones), junto con una fecha de expiración de 1 hora.
     * 
     * @param autenticacion la autenticación del usuario actual.
     * @return un token JWT firmado que contiene la información del usuario.
     * @throws IOException     si ocurre un error al serializar los roles del
     *                         usuario.
     * @throws ServletException si ocurre algún error durante el procesamiento del
     *                         token.
     */
    public String generateToken(Authentication autenticacion)throws IOException, ServletException{

        // Obtiene los detalles del usuario autenticado
        User user = (User) autenticacion.getPrincipal();
        String username = user.getUsername();
        Collection<? extends GrantedAuthority> roles = autenticacion.getAuthorities();

        // Crea las Claims que contendrán la información del token
        Claims claims = Jwts.claims()
                .add("authorities", new ObjectMapper().writeValueAsString(roles))
                .add("username", username)
                .build();

        // Construye el token JWT
        return  Jwts.builder()
                .subject(autenticacion.getName())
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 3600000)) // Duracion del token 1H.
                .issuedAt(new Date()) // Fecha actual.
                .signWith(SECRET_KEY)
                .compact();
    }
}
