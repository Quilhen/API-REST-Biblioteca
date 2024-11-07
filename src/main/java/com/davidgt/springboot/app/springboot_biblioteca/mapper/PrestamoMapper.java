package com.davidgt.springboot.app.springboot_biblioteca.mapper;


import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.davidgt.springboot.app.springboot_biblioteca.dto.PrestamoDto;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Prestamo;;

@Mapper(componentModel = "spring")
public interface PrestamoMapper {
    
     Prestamo prestamoDtoToPrestamo(PrestamoDto prestamoDto);

     @Mappings({
         @Mapping(source = "usuario", target = "usuario"),
         @Mapping(source = "libro", target = "libro"),
         @Mapping(source = "fechaPrestamo", target = "fechaPrestamo"),
         @Mapping(source = "fechaDevolucionPrevista", target = "fechaDevolucionPrevista"),
         @Mapping(source = "estado", target = "estado")
     })
     PrestamoDto prestamoToPrestamoDto(Prestamo prestamo);
 
     List<Prestamo> toPrestamoList(List<PrestamoDto> prestamoDtos);
 
     List<PrestamoDto> toPrestamoDtoList(List<Prestamo> prestamos);
    
}
