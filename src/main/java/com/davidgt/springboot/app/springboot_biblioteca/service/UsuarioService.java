package com.davidgt.springboot.app.springboot_biblioteca.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davidgt.springboot.app.springboot_biblioteca.dto.PrestamoDto;
import com.davidgt.springboot.app.springboot_biblioteca.dto.UsuarioDto;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Usuario;
import com.davidgt.springboot.app.springboot_biblioteca.exception.ResourceNotFoundException;
import com.davidgt.springboot.app.springboot_biblioteca.mapper.PrestamoMapper;
import com.davidgt.springboot.app.springboot_biblioteca.mapper.UsuarioMapper;
import com.davidgt.springboot.app.springboot_biblioteca.repository.UsuarioRepository;


@Service
public class UsuarioService {


    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PrestamoMapper prestamoMapper;


    public List<UsuarioDto> getAllUsuarios(){
        return usuarioMapper.toUsuarioDtoList(usuarioRepository.findAll());
    }

    public UsuarioDto getUsuarioById(Long id){
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if(!usuarioOpt.isPresent()){
             throw new ResourceNotFoundException("Usuario no encontrado con el ID: " + id);
        }

        return usuarioMapper.usuarioToUsuarioDto(usuarioOpt.get());

    }

    public UsuarioDto crearUsuario(UsuarioDto usuarioDto){
        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setNombre(usuarioDto.getNombre());
        usuarioRepository.save(usuario);

        return usuarioMapper.usuarioToUsuarioDto(usuario);
    }

    public List<PrestamoDto> getUsuariosPrestamos(Long id){
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if(!usuarioOpt.isPresent()){
            throw new ResourceNotFoundException("Usuario no encontrado con el ID: " + id);
        }

        return usuarioOpt.get().getPrestamos().stream()
        .map(p -> prestamoMapper.prestamoToPrestamoDto(p))
        .collect(Collectors.toList());
        
    }

}
