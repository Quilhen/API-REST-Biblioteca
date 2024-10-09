package com.davidgt.springboot.app.springboot_biblioteca.service;

import java.time.LocalDate;
import java.util.Date;
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

/**
 * Servicio que gestiona la lógica de negocio relacionada con los préstamos de libros.
 * Proporciona métodos para crear, obtener, devolver y listar préstamos.
 * 
 * Interactúa con los repositorios PrestamoRepository, UsuarioRepository y LibroRepository
 * para manejar las operaciones de préstamo en la base de datos.
 * Utiliza PrestamoMapper para convertir entre entidades y DTOs.
 * 
 * @author David GT
 */
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

    /**
     * Obtiene una lista de todos los préstamos en el sistema.
     * 
     * @return Lista de objetos PrestamoDto que representan los préstamos.
     */
    public List<PrestamoDto> getAllPrestamos() {
        return prestamoMapper.toPrestamoDtoList(prestamoRepository.findAll());
    }

    /**
     * Obtiene los detalles de un préstamo específico por su ID.
     * 
     * @param id El ID del préstamo que se desea obtener.
     * @return Un objeto PrestamoDto con los detalles del préstamo si es encontrado.
     * @throws ResourceNotFoundException Si el préstamo no es encontrado.
     */
    public PrestamoDto getPrestamoById(Long id) {
        Optional<Prestamo> prestamoOpt = prestamoRepository.findById(id);

        if (!prestamoOpt.isPresent()) {
            throw new ResourceNotFoundException("Prestamo no encontrado con el ID: " + id);
        }

        return prestamoMapper.prestamoToPrestamoDto(prestamoOpt.get());

    }

    /**
     * Crea un nuevo préstamo en el sistema.
     * 
     * @param prestamoDto El objeto PrestamoDto con los datos del préstamo a crear.
     * @return El préstamo recién creado en formato PrestamoDto.
     * @throws ResourceNotFoundException Si el usuario o el libro no existen, o si el usuario
     * ya tiene demasiados préstamos o el libro no está disponible.
     */
    public PrestamoDto crearPrestamo(PrestamoDto prestamoDto) {
        Prestamo prestamo = new Prestamo();

        Optional<Usuario> usuarioOpt = usuarioRepository.findById(prestamoDto.getUsuario().getId());
        if (!usuarioOpt.isPresent()) {
            throw new ResourceNotFoundException("El usuario no existe!");
        }

        if (usuarioOpt.get().getPrestamos().size() >= 3) {
            throw new ResourceNotFoundException("No puedes tener mas de 4 libros prestados!");
        }

        Optional<Libro> libroOpt = libroRepository.findById(prestamoDto.getLibro().getId());
        if (!libroOpt.isPresent()) {
            throw new ResourceNotFoundException("El libro no existe!");
        }

        if (!libroOpt.get().isDisponibilidad()) {
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

    /**
     * Marca un préstamo como devuelto.
     * 
     * @param id El ID del préstamo que se desea marcar como devuelto.
     * @return El préstamo actualizado en formato PrestamoDto.
     * @throws ResourceNotFoundException Si el préstamo no es encontrado.
     */
    public PrestamoDto devolverPrestamo(Long id) {
        Optional<Prestamo> prestamoOpt = prestamoRepository.findById(id);

        if (!prestamoOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe el prestamo con el id: " + id);
        }

        Prestamo prestamo = prestamoOpt.get();
        prestamo.getLibro().setDisponibilidad(true);
        prestamo.getLibro().setUsuario(null);
        prestamo.setFechaDevolucion(LocalDate.now());
        prestamoRepository.save(prestamo);

        return prestamoMapper.prestamoToPrestamoDto(prestamo);

    }

}
