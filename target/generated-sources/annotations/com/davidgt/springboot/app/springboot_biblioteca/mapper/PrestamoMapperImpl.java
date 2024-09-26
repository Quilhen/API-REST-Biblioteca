package com.davidgt.springboot.app.springboot_biblioteca.mapper;

import com.davidgt.springboot.app.springboot_biblioteca.dto.PrestamoDto;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Prestamo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-26T17:45:27+0200",
    comments = "version: 1.5.0.Final, compiler: Eclipse JDT (IDE) 3.39.0.v20240820-0604, environment: Java 17.0.12 (Eclipse Adoptium)"
)
@Component
public class PrestamoMapperImpl implements PrestamoMapper {

    @Override
    public Prestamo prestamoDtoToPrestamo(PrestamoDto prestamoDto) {
        if ( prestamoDto == null ) {
            return null;
        }

        Prestamo prestamo = new Prestamo();

        prestamo.setFechaDevolucion( prestamoDto.getFechaDevolucion() );
        prestamo.setFechaPrestamo( prestamoDto.getFechaPrestamo() );
        prestamo.setId( prestamoDto.getId() );
        prestamo.setLibro( prestamoDto.getLibro() );
        prestamo.setUsuario( prestamoDto.getUsuario() );

        return prestamo;
    }

    @Override
    public PrestamoDto prestamoToPrestamoDto(Prestamo prestamo) {
        if ( prestamo == null ) {
            return null;
        }

        PrestamoDto prestamoDto = new PrestamoDto();

        prestamoDto.setFechaDevolucion( prestamo.getFechaDevolucion() );
        prestamoDto.setFechaPrestamo( prestamo.getFechaPrestamo() );
        prestamoDto.setId( prestamo.getId() );
        prestamoDto.setLibro( prestamo.getLibro() );
        prestamoDto.setUsuario( prestamo.getUsuario() );

        return prestamoDto;
    }

    @Override
    public List<Prestamo> toPrestamoList(List<PrestamoDto> prestamoDto) {
        if ( prestamoDto == null ) {
            return null;
        }

        List<Prestamo> list = new ArrayList<Prestamo>( prestamoDto.size() );
        for ( PrestamoDto prestamoDto1 : prestamoDto ) {
            list.add( prestamoDtoToPrestamo( prestamoDto1 ) );
        }

        return list;
    }

    @Override
    public List<PrestamoDto> toPrestamoDtoList(List<Prestamo> prestamo) {
        if ( prestamo == null ) {
            return null;
        }

        List<PrestamoDto> list = new ArrayList<PrestamoDto>( prestamo.size() );
        for ( Prestamo prestamo1 : prestamo ) {
            list.add( prestamoToPrestamoDto( prestamo1 ) );
        }

        return list;
    }
}
