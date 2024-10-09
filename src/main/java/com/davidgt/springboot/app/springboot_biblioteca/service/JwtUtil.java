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

@Component
public class JwtUtil {

    public String generateToken(Authentication autenticacion)throws IOException, ServletException{

        User user = (User) autenticacion.getPrincipal();
        String username = user.getUsername();
        Collection<? extends GrantedAuthority> roles = autenticacion.getAuthorities();

        Claims claims = Jwts.claims()
                .add("authorities", new ObjectMapper().writeValueAsString(roles))
                .add("username", username)
                .build();


        return  Jwts.builder()
                .subject(autenticacion.getName())
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 3600000)) // Duracion del token 1H.
                .issuedAt(new Date()) // Fecha actual.
                .signWith(SECRET_KEY)
                .compact();
    }
}
