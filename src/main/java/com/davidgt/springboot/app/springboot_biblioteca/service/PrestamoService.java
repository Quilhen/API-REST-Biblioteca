package com.davidgt.springboot.app.springboot_biblioteca.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.davidgt.springboot.app.springboot_biblioteca.dto.PrestamoDto;
import com.davidgt.springboot.app.springboot_biblioteca.entity.*;
import com.davidgt.springboot.app.springboot_biblioteca.exception.PrestamoDuplicadoException;
import com.davidgt.springboot.app.springboot_biblioteca.exception.ResourceNotFoundException;
import com.davidgt.springboot.app.springboot_biblioteca.mapper.PrestamoMapper;
import com.davidgt.springboot.app.springboot_biblioteca.repository.LibroRepository;
import com.davidgt.springboot.app.springboot_biblioteca.repository.PrestamoRepository;
import com.davidgt.springboot.app.springboot_biblioteca.repository.UsuarioRepository;

/**
 * Servicio que gestiona la lógica de negocio relacionada con los préstamos de libros.
 * Proporciona métodos para crear, obtener, devolver y gestionar el estado de los préstamos.
 * 
 * Este servicio interactúa con los repositorios de Prestamo, Usuario y Libro para 
 * manejar las operaciones de préstamo en la base de datos.
 * También utiliza servicios de Historial, Reserva y Notificación de Correo.
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

    @Autowired
    private HistorialPrestamoService historialPrestamoService;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private EmailService emailService;

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
     * Crea un nuevo préstamo para un usuario y un libro en específicos, si cumplen
     * con las reglas de negocio.
     * El usuario no puede tener más de 3 préstamos activos.
     * 
     * @param prestamoDto El objeto PrestamoDto con los datos del préstamo a crear.
     * @return El préstamo recién creado en formato PrestamoDto.
     * @throws ResourceNotFoundException Si el usuario o el libro no existen o si 
     *                                   se exceden los límites de préstamo.
     * @throws PrestamoDuplicadoException Si el usuario ya tiene un préstamo para el mismo libro.
     */
    @Transactional
    public PrestamoDto gestionarPrestamo(PrestamoDto prestamoDto) {

        Optional<Usuario> usuarioOpt = usuarioRepository.findById(prestamoDto.getUsuario().getId());
        if (!usuarioOpt.isPresent()) {
            throw new ResourceNotFoundException("El usuario no existe!");
        }

        if (usuarioOpt.get().getPrestamos().size() >= 3) {
            throw new ResourceNotFoundException("No puedes tener mas de 3 libros prestados!");
        }

        Optional<Libro> libroOpt = libroRepository.findById(prestamoDto.getLibro().getId());
        if (!libroOpt.isPresent()) {
            throw new ResourceNotFoundException("El libro no existe!");
        }

        if (libroOpt.get().getCopiasDisponibles() < 1) {
            throw new ResourceNotFoundException("No hay copias disponibles!");
        }

        prestamoRepetido(prestamoDto);
        Usuario usuario = usuarioOpt.get();
        Libro libro = libroOpt.get();
        Prestamo prestamo = crearPrestamo(usuario, libro);

        return prestamoMapper.prestamoToPrestamoDto(prestamo);

    }


     /**
     * Crea un préstamo nuevo para el usuario y el libro especificados.
     *
     * @param usuario El usuario que solicita el préstamo.
     * @param libro   El libro que se quiere prestar.
     * @return El préstamo creado.
     * @throws RuntimeException En caso de error al crear el préstamo.
     */
    @Transactional
    public Prestamo crearPrestamo(Usuario usuario, Libro libro) {
        Prestamo prestamo = new Prestamo();
        try {
            prestamo.setUsuario(usuario);
            prestamo.setLibro(libro);
            prestamo.setFechaPrestamo(LocalDate.now());
            prestamo.setFechaDevolucionPrevista(LocalDate.now().plusDays(7));
            prestamoRepository.save(prestamo);
            libro.decrementarCopiasDisponibles();
            libroRepository.save(libro);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el préstamo", e);
        }

        return prestamo;

    }

   /**
     * Marca un préstamo como devuelto y actualiza el inventario de copias disponibles.
     * También gestiona reservas pendientes si existen.
     * 
     * @param id El ID del préstamo que se desea marcar como devuelto.
     * @return El préstamo actualizado en formato PrestamoDto.
     * @throws ResourceNotFoundException Si el préstamo no es encontrado.
     */
    @Transactional
    public PrestamoDto devolverPrestamo(Long id) {
        Prestamo prestamo = obtenerPrestamoPorId(id);
        Libro libro = obtenerLibroPorId(prestamo.getLibro().getId());

        procesarDevolucion(prestamo, libro);
        gestionarReservasPendientes(libro);

        return prestamoMapper.prestamoToPrestamoDto(prestamo);

    }


      /**
     * Recupera un préstamo por su ID o lanza una excepción si no existe.
     * 
     */
    private Prestamo obtenerPrestamoPorId(Long prestamoId) {
        return prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el préstamo con el id: " + prestamoId));
    }


     /**
     * Recupera un libro por su ID o lanza una excepción si no existe.
     * 
     */
    private Libro obtenerLibroPorId(Long libroId) {
        return libroRepository.findById(libroId)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el libro con el id: " + libroId));
    }


     /**
     * Procesa la devolución de un préstamo, guardando el historial y
     * actualizando el inventario de libros.
     * 
     * @param prestamo El préstamo a devolver.
     * @param libro    El libro que se devuelve.
     */
    private void procesarDevolucion(Prestamo prestamo, Libro libro) {
        historialPrestamoService.crearHistorialPrestamo(prestamo);
        prestamoRepository.delete(prestamo);
        libro.incrementarCopiasDisponibles();
        libroRepository.save(libro);
    }


    /**
     * Gestiona las reservas pendientes de un libro devuelto. Completa la primera reserva en cola,
     * crea un préstamo y envía una notificación al usuario correspondiente.
     * 
     * @param libroDevuelto El libro devuelto que puede tener reservas pendientes.
     * @throws RuntimeException En caso de error al completar la reserva.
     */
    private void gestionarReservasPendientes(Libro libroDevuelto) {
        List<Reserva> reservasPendientes = reservaService.getReservasPendientesByLibro(libroDevuelto.getId());
        if (!reservasPendientes.isEmpty()) {

            try {
                Reserva primeraReserva = reservasPendientes.get(0);
                reservaService.completarReserva(primeraReserva);

                Libro libro = primeraReserva.getLibro();
                Usuario usuario = primeraReserva.getUsuario();

                Prestamo prestamo = crearPrestamo(usuario, libro);
                prestamoRepository.save(prestamo);

                String emailUsuario = primeraReserva.getUsuario().getEmail();
                String tituloLibro = libro.getTitulo();
                String asunto = "Tu reserva esta lista.";
                String text = String.format("Hola %s, el libro '%s' que reservastes ya esta disponible.",
                        usuario.getNombreUsuario(), tituloLibro);

                emailService.enviarMensaje(emailUsuario, asunto, text);
            } catch (Exception e) {
                throw new RuntimeException("Error al crear el préstamo mediante la reserva!", e);
            }

        }

    }

  /**
     * Marca un préstamo como perdido y lo guarda en el historial antes de eliminarlo.
     * 
     * @param id El ID del préstamo que se desea marcar como perdido.
     * @return El préstamo actualizado en formato PrestamoDto.
     * @throws ResourceNotFoundException Si el préstamo no es encontrado.
     */
    @Transactional
    public PrestamoDto cambiarEstado(Long id) {
        Optional<Prestamo> prestamoOpt = prestamoRepository.findById(id);

        if (!prestamoOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe el prestamo con el id: " + id);
        }

        Prestamo prestamo = prestamoOpt.get();
        prestamo.setEstado(EstadoPrestamo.PERDIDO);
        historialPrestamoService.crearHistorialPrestamo(prestamo);
        prestamoRepository.delete(prestamo);
        return prestamoMapper.prestamoToPrestamoDto(prestamo);

    }


     /**
     * Verifica si el usuario ya tiene un préstamo para el libro solicitado.
     * 
     * @param prestamoDto Objeto PrestamoDto que contiene información del préstamo.
     * @throws PrestamoDuplicadoException Si el usuario ya tiene un préstamo para el mismo libro.
     */
    public void prestamoRepetido(PrestamoDto prestamoDto) {
        Libro libro = prestamoDto.getLibro();
        Usuario usuario = prestamoDto.getUsuario();
        Boolean existe = prestamoRepository.existsByUsuarioAndLibro(usuario, libro);

        if (existe) {
            throw new PrestamoDuplicadoException("No puedes hacer un prestamo del mismo libro!");
        }
    }

}
