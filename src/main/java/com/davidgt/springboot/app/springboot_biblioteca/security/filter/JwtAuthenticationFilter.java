package com.davidgt.springboot.app.springboot_biblioteca.security.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.davidgt.springboot.app.springboot_biblioteca.entity.Usuario;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.davidgt.springboot.app.springboot_biblioteca.security.TokenJwtConfig.*;


/**
 * Filtro de autenticación JWT que extiende de {@link UsernamePasswordAuthenticationFilter}.
 * Este filtro se encarga de gestionar el proceso de autenticación basado en JWT, verificando 
 * las credenciales del usuario, generando el token y manejando tanto las autenticaciones exitosas 
 * como las fallidas.
 * 
 * @author David GT
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;


    /**
     * Constructor que inicializa el filtro con el {@link AuthenticationManager}.
     * 
     * @param authenticationManager El gestor de autenticación que maneja la lógica para validar las credenciales del usuario.
     */
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    
     /**
     * Método que intenta autenticar a un usuario a partir de los datos enviados en la petición HTTP.
     * 
     * Extrae el nombre de usuario y la contraseña de la solicitud y genera un 
     * {@link UsernamePasswordAuthenticationToken} que será utilizado por el {@link AuthenticationManager}.
     * 
     * @param request La solicitud HTTP que contiene los datos de autenticación.
     * @param response La respuesta HTTP.
     * @return Un objeto de autenticación si las credenciales son válidas.
     * @throws AuthenticationException Si la autenticación falla.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        Usuario usuario = null;
        String nombreUsuario = null;
        String contraseña = null;

        try {
            // Intentamos leer el cuerpo de la solicitud y mapearlo a un objeto Usuario.
            usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
            nombreUsuario = usuario.getNombreUsuario();
            contraseña = usuario.getPassword();
        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Creamos un token de autenticación con el nombre de usuario y la contraseña proporcionados.
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(nombreUsuario,
                contraseña);

        // Intentamos autenticar al usuario con el AuthenticationManager.
        return authenticationManager.authenticate(authenticationToken);

    }


    /**
     * Método que se ejecuta cuando la autenticación es exitosa.
     * 
     * Se genera un token JWT, se agrega en la cabecera de la respuesta HTTP y se devuelve un mensaje 
     * personalizado al usuario.
     * 
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @param chain El filtro de cadena.
     * @param authResult El resultado de la autenticación.
     * @throws IOException Si ocurre un error de entrada/salida.
     * @throws ServletException Si ocurre un error relacionado con el servlet.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        User user = (User) authResult.getPrincipal(); // Se obtiene el usuario autenticado.
        String username = user.getUsername();
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

        // Se construyen los "claims" del JWT, incluyendo el nombre de usuario y los roles.
        Claims claims = Jwts.claims()
                .add("authorities", new ObjectMapper().writeValueAsString(roles))
                .add("username", username)
                .build();

        // Se genera el token JWT con los claims y se configura la duración (1 hora).
        String token = Jwts.builder()
                .subject(username)
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 3600000)) // Duracion del token de 1 hora.
                .issuedAt(new Date()) // Fecha actual.
                .signWith(SECRET_KEY) // Se firma el token con la clave secreta.
                .compact();

        // Se agrega el token en la cabecera de la respuesta.
        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);

        // Se envía una respuesta JSON con el token, nombre de usuario y un mensaje de bienvenida.
        Map<String, String> body = new HashMap<>();
        body.put("token", token);
        body.put("username", username);
        body.put("message", String.format("Has iniciado sesion. Buenos dias  %s !", username));

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(CONTENT_TYPE);
        response.setStatus(200);

    }

     /**
     * Método que se ejecuta cuando la autenticación falla.
     * 
     * En caso de error en la autenticación (credenciales inválidas), se devuelve una respuesta con 
     * un mensaje de error y un código de estado 401.
     * 
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @param failed La excepción lanzada durante el proceso de autenticación.
     * @throws IOException Si ocurre un error de entrada/salida.
     * @throws ServletException Si ocurre un error relacionado con el servlet.
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {

        // Se construye el cuerpo de la respuesta de error.
        Map<String, String> body = new HashMap<>();
        body.put("message", "Error en la autenticacion username o password incorrectos!");
        body.put("error", failed.getMessage());

        // Se devuelve un código de estado 401 junto con el mensaje de error.
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType(CONTENT_TYPE);
    }

}
