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

@Service
public class JpaUserDetailsService implements UserDetailsService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Usuario> userOptional = usuarioRepository.findByNombreUsuario(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Username %s no existe en el sistema!", username));
        }

        Usuario usuario = userOptional.orElseThrow();

        List<GrantedAuthority> authorities = usuario.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(usuario.getNombreUsuario(), 
        usuario.getPassword(), 
        usuario.isActivado(),
        true,
        true,
        true,
                authorities);
    }

}
