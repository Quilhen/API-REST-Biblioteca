package com.davidgt.springboot.app.springboot_biblioteca.mapper;


import java.util.List;

import org.mapstruct.Mapper;

import com.davidgt.springboot.app.springboot_biblioteca.dto.PrestamoDto;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Prestamo;;

@Mapper(componentModel = "spring")
public interface PrestamoMapper {

    Prestamo prestamoDtoToPrestamo(PrestamoDto prestamoDto);
    PrestamoDto prestamoToPrestamoDto(Prestamo prestamo);

    List<Prestamo> toPrestamoList(List<PrestamoDto> prestamoDto);
    List<PrestamoDto> toPrestamoDtoList(List<Prestamo> prestamo);
    
}
