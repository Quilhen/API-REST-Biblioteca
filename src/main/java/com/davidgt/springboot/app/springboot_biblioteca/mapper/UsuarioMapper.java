package com.davidgt.springboot.app.springboot_biblioteca.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.davidgt.springboot.app.springboot_biblioteca.dto.UsuarioDto;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDto usuarioToUsuarioDto(Usuario usuario);
    Usuario usuarioDtoToUsuario(Usuario usuario);

    List<UsuarioDto> toUsuarioDtoList(List<Usuario> usuario);
    List<Usuario> toUsuarioList(List<UsuarioDto> usuarioDto);

}
