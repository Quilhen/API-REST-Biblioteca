package com.davidgt.springboot.app.springboot_biblioteca.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.davidgt.springboot.app.springboot_biblioteca.entity.Usuario;
import com.davidgt.springboot.app.springboot_biblioteca.repository.UsuarioRepository;


/**
 * Servicio para la gestión de usuarios en el contexto de Spring Security.
 * Esta clase implementa {@link UserDetailsService} para cargar los detalles del
 * usuario desde la base de datos utilizando JPA.
 * Es utilizada por Spring Security para realizar la autenticación basada en el
 * nombre de usuario.
 */
@Service
public class JpaUserDetailsService implements UserDetailsService{

    @Autowired
    private UsuarioRepository usuarioRepository;


     /**
     * Carga un usuario por su nombre de usuario desde la base de datos.
     * Este método es llamado por Spring Security durante el proceso de
     * autenticación para validar el usuario y sus roles.
     * 
     * @param username el nombre de usuario para buscar en la base de datos.
     * @return un objeto {@link UserDetails} que contiene la información del
     *         usuario.
     * @throws UsernameNotFoundException si el usuario no existe en la base de
     *                                   datos.
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Busca el usuario por nombre de usuario en la base de datos
        Optional<Usuario> userOptional = usuarioRepository.findByNombreUsuario(username);

        // Si no se encuentra el usuario, lanza una excepción
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Username %s no existe en el sistema!", username));
        }

        // Si el usuario existe, lo recupera
        Usuario usuario = userOptional.orElseThrow();

        // Convierte los roles del usuario en una lista de GrantedAuthority
        List<GrantedAuthority> authorities = usuario.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                .collect(Collectors.toList());

        // Retorna un objeto User de Spring Security con la información del usuario
        return new org.springframework.security.core.userdetails.User(usuario.getNombreUsuario(), 
        usuario.getPassword(), 
        usuario.isActivado(),
        true,
        true,
        true,
                authorities);
    }

}
