package com.davidgt.springboot.app.springboot_biblioteca.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;


import com.davidgt.springboot.app.springboot_biblioteca.dto.LibroDto;
import com.davidgt.springboot.app.springboot_biblioteca.dto.PrestamoDto;
import com.davidgt.springboot.app.springboot_biblioteca.dto.UsuarioDto;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Libro;
import com.davidgt.springboot.app.springboot_biblioteca.service.LibroService;
import com.davidgt.springboot.app.springboot_biblioteca.service.PrestamoService;
import com.davidgt.springboot.app.springboot_biblioteca.service.UsuarioService;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AppController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping("/libros")
    public Page<LibroDto> getAllLibros() {
        return libroService.getAllLibros(0, 10);
    }

    @GetMapping("/librosFiltros")
    public Page<Libro> obtenerLibrosConFiltros(
        @Parameter(description = "Titulo del libro")
        @RequestParam(required = false) String titulo,

        @Parameter(description = "Autor del libro")
        @RequestParam(required = false) String autor,

        @Parameter(description = "Fecha de la publicación")
        @RequestParam(required = false)
        //Asegura que el parámetro de fecha en Swagger sea interpretado como una fecha en formato ISO (yyyy-MM-dd).
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaPublicacion,

        @Parameter(description = "Genero del libro")
        @RequestParam(required = false) String genero,

        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") int size

    ){
        Pageable pageable = PageRequest.of(page, size);
        return libroService.obtenerLibrosConFiltros(titulo, autor, fechaPublicacion, genero, pageable);

    }

    @GetMapping("/libros/{id}")
    public ResponseEntity<?> getLibroById(@PathVariable Long id) {
        LibroDto libro = libroService.getLibroById(id);
        return ResponseEntity.status(HttpStatus.OK).body(libro);
    }

    @PostMapping("/libros")
    public ResponseEntity<?> crearLibro(@Valid @RequestBody LibroDto libroDto) {
        LibroDto libro = libroService.crearLibro(libroDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(libro);
    }

    @PutMapping("/libros/{id}")
    public ResponseEntity<?> actualizarLibro(@Valid @RequestBody LibroDto libroDto, @PathVariable Long id) {
        LibroDto libro = libroService.actualizarLibro(libroDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(libro);
    }

    @DeleteMapping("/libros/{id}")
    public ResponseEntity<?> eliminearLibro(@PathVariable Long id) {
        LibroDto libro = libroService.eliminarLibro(id);
        return ResponseEntity.status(HttpStatusCode.valueOf(204)).body(libro);
    }

    @GetMapping("/usuarios")
    public List<UsuarioDto> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable Long id) {
        UsuarioDto usuario =  usuarioService.getUsuarioById(id);
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    @PostMapping("/usuarios")
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioDto usuarioDto) {
        UsuarioDto usuario =  usuarioService.crearUsuario(usuarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @GetMapping("/usuarios/{id}/prestamos")
    public ResponseEntity<?> getUsuariosPrestamosActivos(@PathVariable Long id) {
        List<PrestamoDto> prestamos = usuarioService.getUsuariosPrestamos(id);
        return ResponseEntity.status(HttpStatus.OK).body(prestamos);
 
    }

    @GetMapping("/prestamos")
    public List<PrestamoDto> getAllPrestamos() {
        return prestamoService.getAllPrestamos();
    }

    @GetMapping("/prestamos/{id}")
    public ResponseEntity<?> getPrestamoById(@PathVariable Long id) {
        PrestamoDto prestamo = prestamoService.getPrestamoById(id);
        return ResponseEntity.status(HttpStatus.OK).body(prestamo);
    }

    @PostMapping("/prestamos")
    public ResponseEntity<?> crearPrestamo(@Valid @RequestBody PrestamoDto prestamoDto) {
        PrestamoDto prestamo = prestamoService.crearPrestamo(prestamoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(prestamo);
    }

    @PutMapping("/prestamos/{id}/devolver")
    public ResponseEntity<?> devolverPrestamo(@Valid @PathVariable Long id) {
        PrestamoDto prestamo = prestamoService.devolverPrestamo(id);
        return ResponseEntity.status(HttpStatus.OK).body(prestamo);
    }

}
