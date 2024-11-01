package com.davidgt.springboot.app.springboot_biblioteca.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.davidgt.springboot.app.springboot_biblioteca.entity.Rol;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Usuario;
import com.davidgt.springboot.app.springboot_biblioteca.exception.ResourceNotFoundException;
import com.davidgt.springboot.app.springboot_biblioteca.dto.PrestamoDto;
import com.davidgt.springboot.app.springboot_biblioteca.dto.UsuarioDto;
import com.davidgt.springboot.app.springboot_biblioteca.mapper.PrestamoMapper;
import com.davidgt.springboot.app.springboot_biblioteca.mapper.UsuarioMapper;
import com.davidgt.springboot.app.springboot_biblioteca.repository.RolRepository;
import com.davidgt.springboot.app.springboot_biblioteca.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PrestamoMapper prestamoMapper;

    public List<UsuarioDto> getAllUsuarios() {
        return usuarioMapper.toUsuarioDtoList(usuarioRepository.findAll());
    }

    public List<PrestamoDto> getUsuariosPrestamos(Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if (!usuarioOpt.isPresent()) {
            throw new ResourceNotFoundException("Usuario no encontrado con el ID: " + id);
        }

        return usuarioOpt.get().getPrestamos().stream()
                .map(p -> prestamoMapper.prestamoToPrestamoDto(p))
                .collect(Collectors.toList());

    }

    @Transactional
    public UsuarioDto save(Usuario usuario) {
        // Antes de crear el usuario añadimos el rol ROLE_USER y comprobamos si es
        // admin.
        Optional<Rol> optionalRoleUser = rolRepository.findByNombre("ROLE_USER");
        List<Rol> roles = new ArrayList<>();

        optionalRoleUser.ifPresent(roles::add);

        if (usuario.isAdmin()) {
            Optional<Rol> optionalRoleAdmin = rolRepository.findByNombre("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }

        usuario.setRoles(roles);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        UsuarioDto usuarioDto = usuarioMapper.usuarioToUsuarioDto(usuarioRepository.save(usuario));
        return usuarioDto;
    }

    public boolean existsByUsername(String username) {
        return usuarioRepository.existsByNombreUsuario(username);
    }

    @Transactional
    public UsuarioDto actualizarUsuario(Long id, UsuarioDto usuarioDto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if (!usuarioOpt.isPresent()) {
            throw new ResourceNotFoundException("Usuario no encontrado con el ID: " + id);

        }

        Usuario usuario = usuarioOpt.get();
        usuario.setNombreUsuario(usuarioDto.getNombreUsuario());
        usuario.setEmail(usuarioDto.getEmail());
        usuarioRepository.save(usuario);
        return usuarioMapper.usuarioToUsuarioDto(usuario);
    }

    @Transactional
    public UsuarioDto eliminarUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (!usuario.isPresent()) {
            throw new ResourceNotFoundException("Usuario no encontrado con el ID: " + id);
        }

        usuarioRepository.delete(usuario.get());
        return usuarioMapper.usuarioToUsuarioDto(usuario.get());
    }

}
