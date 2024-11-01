package com.davidgt.springboot.app.springboot_biblioteca.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controlador REST para gestionar las operacion relacionadas con libros.
 * Ofrece endpoints para crear, obtener, actualizar y eliminar libros en la
 * biblioteca.
 * 
 * @author David GT
 */
@RestController
@Tag(name = "Libros", description = "Operaciones relacionadas con los libros.")
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    /**
     * Endpoint para obtener una lista de todos los libros paginada.
     * Requiere autenticación con roles ADMIN o USER.
     * 
     * @param page El número de página (por defecto 0).
     * @param size El tamaño de la página (por defecto 10).
     * @return Una página con una lista de objetos LibroDto que representan los  libros disponibles.
     */
    @Operation(summary = "Obtener todos los libros", description = "Devuelve una lista paginada de todos los libros disponibles.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de libros obtenida con éxito"),
            @ApiResponse(responseCode = "403", description = "No tienes permisos para acceder a este recurso"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public Page<LibroDto> getAllLibros(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return libroService.getAllLibros(page, size);
    }

    /**
     * Endpoint para obtener una lista paginada de libros que cumplen con ciertos filtros.
     * Requiere autenticación con roles ADMIN o USER.
     *
     * @param titulo El título del libro (opcional).
     * @param autor El autor del libro (opcional).
     * @param fechaPublicacion La fecha de publicación en formato ISO (yyyy-MM-dd) (opcional).
     * @param genero El género del libro (opcional).
     * @param page El número de página (por defecto 0).
     * @param size El tamaño de la página (por defecto 10).
     * @return Una página con una lista de libros filtrados.
     */
    @Operation(summary = "Filtrar libros", description = "Devuelve una lista de libros que cumplen con los filtros especificados.", 
               security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Libros filtrados obtenidos con éxito"),
        @ApiResponse(responseCode = "403", description = "No tienes permisos para acceder a este recurso"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/librosFiltros")
    public Page<Libro> obtenerLibrosConFiltros(
            @Parameter(description = "Titulo del libro (opcional)") @RequestParam(required = false) String titulo,
            @Parameter(description = "Autor del libro (opcional)") @RequestParam(required = false) String autor,
            @Parameter(description = "Fecha de la publicación (opcional)") @RequestParam(required = false)
            // Asegura que el parámetro de fecha en Swagger sea interpretado como una fecha en formato ISO (yyyy-MM-dd).
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaPublicacion,
            @Parameter(description = "Genero del libro (opcional)") @RequestParam(required = false) String genero,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return libroService.obtenerLibrosConFiltros(titulo, autor, fechaPublicacion, genero, pageable);

    }

   /**
     * Endpoint para obtener un libro por su ID.
     * Requiere autenticación con roles ADMIN o USER.
     *
     * @param id El ID del libro.
     * @return Un objeto LibroDto si el libro es encontrado.
     */
    @Operation(summary = "Obtener un libro por ID", description = "Devuelve un libro específico por su ID.", 
               security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Libro obtenido con éxito"),
        @ApiResponse(responseCode = "404", description = "Libro no encontrado"),
        @ApiResponse(responseCode = "403", description = "No tienes permisos para acceder a este recurso"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getLibroById(@PathVariable Long id) {
        LibroDto libro = libroService.getLibroById(id);
        return ResponseEntity.status(HttpStatus.OK).body(libro);
    }

    /**
     * Endpoint para crear un nuevo libro en la biblioteca.
     * Requiere autenticación con rol ADMIN.
     *
     * @param libroDto El objeto LibroDto con los datos del libro a crear.
     * @return El libro recién creado.
     */
    @Operation(summary = "Crear un libro", description = "Crea un nuevo libro en la biblioteca.", 
               security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Libro creado con éxito"),
        @ApiResponse(responseCode = "403", description = "No tienes permisos para crear un libro"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> crearLibro(@Valid @RequestBody LibroDto libroDto) {
        LibroDto libro = libroService.crearLibro(libroDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(libro);
    }

    /**
     * Endpoint para actualizar los datos de un libro existente.
     * Requiere autenticación con rol ADMIN.
     *
     * @param libroDto El objeto LibroDto con los datos actualizados.
     * @param id El ID del libro a actualizar.
     * @return El libro actualizado.
     */
    @Operation(summary = "Actualizar un libro", description = "Actualiza los datos de un libro existente.", 
               security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Libro actualizado con éxito"),
        @ApiResponse(responseCode = "403", description = "No tienes permisos para actualizar un libro"),
        @ApiResponse(responseCode = "404", description = "Libro no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarLibro(@Valid @RequestBody LibroDto libroDto, @PathVariable Long id) {
        LibroDto libro = libroService.actualizarLibro(libroDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(libro);
    }

  
     /**
     * Endpoint para eliminar un libro de la biblioteca por su ID.
     * Requiere autenticación con rol ADMIN.
     *
     * @param id El ID del libro a eliminar.
     * @return Respuesta de éxito si el libro ha sido eliminado.
     */
    @Operation(summary = "Eliminar un libro", description = "Elimina un libro de la biblioteca por su ID.", 
               security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Libro eliminado con éxito"),
        @ApiResponse(responseCode = "403", description = "No tienes permisos para eliminar un libro"),
        @ApiResponse(responseCode = "404", description = "Libro no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarLibro(@PathVariable Long id) {
        libroService.eliminarLibro(id);
        return ResponseEntity.noContent().build();
    }

}
