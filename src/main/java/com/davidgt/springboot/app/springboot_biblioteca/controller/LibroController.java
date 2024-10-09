package com.davidgt.springboot.app.springboot_biblioteca.controller;

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
import com.davidgt.springboot.app.springboot_biblioteca.entity.Libro;
import com.davidgt.springboot.app.springboot_biblioteca.service.LibroService;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

/**
 * Controlador REST para gestionar las operacion relacionadas con libros.
 * Ofrece endpoints para crear, obtener, actualizar y eliminar libros en la biblioteca.
 * 
 * Esta clase interactua con el servicio LibroService para manejar la logica de negocio.
 * 
 * @author David GT
 */

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    /**
     * Obtiene una lista de todos los libros paginada.
     * 
     * @return Una página con una lista de objetos LibroDto que representa los libros disponibles.
     */
    @GetMapping
    public Page<LibroDto> getAllLibros(
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") int size){
        return libroService.getAllLibros(page, size);
    }

    /**
     * Obtiene una lista paginada de libros que cumplen con ciertos filtros.
     * 
     * @param titulo Titulo del libro (opcional).
     * @param autor Autor del libro (opcional).
     * @param fechaPublicacion Fecha de publicación del libro en formato ISO (yyyy-MM-dd) (opcional).
     * @param genero Genero del libro (opcional).
     * @param page Número de página para la paginación (por defecto es 0)
     * @param size Tamaño de la página (por defecto es 10).
     * @return Una pagina con una lista de libros filtados.
     */
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

    /**
     * Obtiene un libro por su ID.
     * 
     * @param id El id del libro que se quiere buscar.
     * @return Un objeto LibroDto que representa el libro si es encontrado.
     * 
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getLibroById(@PathVariable Long id) {
        LibroDto libro = libroService.getLibroById(id);
        return ResponseEntity.status(HttpStatus.OK).body(libro);
    }

    /**
     * Crea un nuevo libro en la biblioteca.
     * 
     * @param libroDto El objeto LibroDto con los datos del libro a crear.
     * @return El libro recién creado en formato LibroDto.
     */
    @PostMapping
    public ResponseEntity<?> crearLibro(@Valid @RequestBody LibroDto libroDto) {
        LibroDto libro = libroService.crearLibro(libroDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(libro);
    }

    /**
     * Actualiza los datos de un libro existente.
     * 
     * @param libroDto El objeto LibroDto con los datos actualizados.
     * @param id El ID del libro a actualizar.
     * @return El libro actualizado en formato LibroDto.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarLibro(@Valid @RequestBody LibroDto libroDto, @PathVariable Long id) {
        LibroDto libro = libroService.actualizarLibro(libroDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(libro);
    }

    /**
     * Elimina un libro de la biblioteca por su ID.
     * 
     * @param id El ID del libro a eliminar.
     * @return El libro eliminado en formato LibroDto.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminearLibro(@PathVariable Long id) {
        LibroDto libro = libroService.eliminarLibro(id);
        return ResponseEntity.status(HttpStatusCode.valueOf(204)).body(libro);
    }

}
