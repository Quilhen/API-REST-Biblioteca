package com.davidgt.springboot.app.springboot_biblioteca.security.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.davidgt.springboot.app.springboot_biblioteca.security.SimpleGrantedAuthorityJsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.davidgt.springboot.app.springboot_biblioteca.security.TokenJwtConfig.*;


/**
 * Filtro personalizado para validar el token JWT en cada solicitud HTTP.
 * Extiende {@link BasicAuthenticationFilter} para interceptar todas las solicitudes entrantes 
 * y verificar si incluyen un token JWT en el encabezado de autorización.
 * Si el token es válido, se extrae la información del usuario y sus
 * autorizaciones, configurando la autenticación en el contexto de seguridad.
 */
public class JwtValidationFilter extends BasicAuthenticationFilter {


     /**
     * Constructor que inicializa el filtro con el {@link AuthenticationManager}.
     * 
     * @param authenticationManager el administrador de autenticación utilizado para el proceso de autenticación.
     */
    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


    /**
     * Método que intercepta cada solicitud HTTP para validar el token JWT si está
     * presente en el encabezado.
     * Si el token es válido, se extraen las autoridades del usuario y se configura
     * la autenticación en el contexto de seguridad.
     * 
     * @param request  la solicitud HTTP.
     * @param response la respuesta HTTP.
     * @param chain    el filtro de la cadena para continuar el procesamiento de la
     *                 solicitud.
     * @throws IOException      si ocurre un error de entrada/salida.
     * @throws ServletException si ocurre un error de servlet.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Extrae el encabezado Authorization
        String header = request.getHeader(HEADER_AUTHORIZATION);

        // Si no hay token en el encabezado, continua con la cadena de filtros
        if (header == null || !header.startsWith(PREFIX_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }

        // Extrae el token JWT del encabezado
        String token = header.replace(PREFIX_TOKEN, "");

        try {
            // Valida el token JWT y extrae las Claims (información) del token
            Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();

            // Extrae el nombre de usuario del token
            String usename = claims.getSubject();

            // Extrae las autoridades (roles) del usuario del token
            Object authoritiesClaims = claims.get("authorities");

            // Convierte las autoridades en una colección de GrantedAuthority
            Collection<? extends GrantedAuthority> authorities = Arrays.asList(
                    new ObjectMapper()
                            .addMixIn(SimpleGrantedAuthority.class,
                                    SimpleGrantedAuthorityJsonCreator.class)
                            .readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class));

            // Crea un token de autenticación con las autoridades extraídas
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usename,
                    null, authorities);
            
            // Configura el contexto de seguridad con el token de autenticación
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // Continua con el procesamiento de la solicitud
            chain.doFilter(request, response);

        } catch (JwtException e) {
            // Si el token es inválido, devuelve un mensaje de error con estado 401
            Map<String, String> body = new HashMap<>();
            body.put("error", e.getMessage());
            body.put("message", "El token JWT es invalido!");

            // Envia el error en formato JSON
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(CONTENT_TYPE);
        }
    }

}
