package com.davidgt.springboot.app.springboot_biblioteca.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.davidgt.springboot.app.springboot_biblioteca.dto.PrestamoDto;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Prestamo;

public class PrestamoMapperTest {


    private PrestamoMapper prestamoMapper = Mappers.getMapper(PrestamoMapper.class);

    @Test
    void testPrestamoToPrestamoDto() {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucionPrevista(LocalDate.now().plusDays(7));

        PrestamoDto prestamoDto = prestamoMapper.prestamoToPrestamoDto(prestamo);

        assertNotNull(prestamoDto, "El mapeo no debería ser null");

        assertEquals(prestamo.getId(), prestamoDto.getId(), "El ID debería coincidir");
        assertEquals(prestamo.getFechaPrestamo(), prestamoDto.getFechaPrestamo(), "La fecha de préstamo debería coincidir");
        assertEquals(prestamo.getFechaDevolucionPrevista(), prestamoDto.getfechaDevolucionPrevista(), "La fecha de devolución debería coincidir");
    }

}
