package com.davidgt.springboot.app.springboot_biblioteca.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.davidgt.springboot.app.springboot_biblioteca.dto.UsuarioDto;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDto usuarioToUsuarioDto(Usuario usuario);

     @Mapping(target = "password", ignore = true)
     @Mapping(target = "roles", ignore = true)
     @Mapping(target = "activado", ignore = true)
     @Mapping(target = "admin", ignore = true)
    Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto);

    List<UsuarioDto> toUsuarioDtoList(List<Usuario> usuario);
    List<Usuario> toUsuarioList(List<UsuarioDto> usuarioDto);

}
