package com.davidgt.springboot.app.springboot_biblioteca.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davidgt.springboot.app.springboot_biblioteca.dto.PrestamoDto;
import com.davidgt.springboot.app.springboot_biblioteca.entity.*;
import com.davidgt.springboot.app.springboot_biblioteca.exception.ResourceNotFoundException;
import com.davidgt.springboot.app.springboot_biblioteca.mapper.PrestamoMapper;
import com.davidgt.springboot.app.springboot_biblioteca.repository.LibroRepository;
import com.davidgt.springboot.app.springboot_biblioteca.repository.PrestamoRepository;
import com.davidgt.springboot.app.springboot_biblioteca.repository.UsuarioRepository;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoMapper prestamoMapper;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    public List<PrestamoDto> getAllPrestamos() {
        return prestamoMapper.toPrestamoDtoList(prestamoRepository.findAll());
    }

    public PrestamoDto getPrestamoById(Long id) {
        Optional<Prestamo> prestamoOpt = prestamoRepository.findById(id);

        if (!prestamoOpt.isPresent()) {
            throw new ResourceNotFoundException("Prestamo no encontrado con el ID: " + id);
        }

        return prestamoMapper.prestamoToPrestamoDto(prestamoOpt.get());

    }

    public PrestamoDto crearPrestamo(PrestamoDto prestamoDto) {
        Prestamo prestamo = new Prestamo();

        Optional<Usuario> usuarioOpt = usuarioRepository.findById(prestamoDto.getUsuario().getId());
        if (!usuarioOpt.isPresent()) {
            throw new ResourceNotFoundException("El usuario no existe!");
        }

        if(usuarioOpt.get().getPrestamos().size() >= 3){
            throw new ResourceNotFoundException("No puedes tener mas de 4 libros prestados!");
        }

        Optional<Libro> libroOpt = libroRepository.findById(prestamoDto.getLibro().getId());
        if (!libroOpt.isPresent()) {
            throw new ResourceNotFoundException("El libro no existe!");
        }

        if(!libroOpt.get().isDisponibilidad()){
            throw new ResourceNotFoundException("El libro ya esta prestado!");
        }

        Usuario usuario = usuarioOpt.get();
        Libro libro = libroOpt.get();

        libro.setDisponibilidad(false);
        libro.setUsuario(usuario);
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucion(LocalDate.now().plusDays(7));

        prestamoRepository.save(prestamo);
        return prestamoMapper.prestamoToPrestamoDto(prestamo);
    }

    public PrestamoDto devolverPrestamo(Long id) {
        Optional<Prestamo> prestamoOpt = prestamoRepository.findById(id);

        if (!prestamoOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe el prestamo con el id: " + id);
        }

        Prestamo prestamo = prestamoOpt.get();
        prestamo.getLibro().setDisponibilidad(true);
        prestamo.getLibro().setUsuario(null);
        prestamoRepository.save(prestamo);

        return prestamoMapper.prestamoToPrestamoDto(prestamo);

    }

}
