package com.davidgt.springboot.app.springboot_biblioteca.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.davidgt.springboot.app.springboot_biblioteca.config.SecurityConfigTest;
import com.davidgt.springboot.app.springboot_biblioteca.dto.LibroDto;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Libro;
import com.davidgt.springboot.app.springboot_biblioteca.service.JpaUserDetailsService;
import com.davidgt.springboot.app.springboot_biblioteca.service.JwtUtil;
import com.davidgt.springboot.app.springboot_biblioteca.service.LibroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@WebMvcTest(LibroController.class)
@Import(SecurityConfigTest.class)
public class LibroControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private LibroService libroService;

        @MockBean
        private JpaUserDetailsService userDetailsService;

        @MockBean
        private JwtUtil jwtUtil;

        @Autowired
        private ObjectMapper objectMapper;

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);
        }

        @Test
        @WithMockUser(roles = { "USER" })
        void testGetAllLibros() throws Exception {
                LibroDto libroDto = new LibroDto();
                libroDto.setTitulo("Libro de prueba");

                Page<LibroDto> libroPage = new PageImpl<>(List.of(libroDto));

                when(libroService.getAllLibros(0, 10)).thenReturn(libroPage);

                mockMvc.perform(get("/api/libros")
                                .param("page", "0")
                                .param("size", "10"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content[0].titulo").value("Libro de prueba"));

                verify(libroService, times(1)).getAllLibros(0, 10);
        }

        @Test
        @WithMockUser(roles = { "USER" })
        void testObtenerLibrosConFiltros() throws Exception {
                Libro libro = new Libro();
                libro.setTitulo("Libro Filtrado");

                Page<Libro> libroPage = new PageImpl<>(List.of(libro));

                when(libroService.obtenerLibrosConFiltros("Libro Filtrado", null, null, null, PageRequest.of(0, 10)))
                                .thenReturn(libroPage);

                mockMvc.perform(get("/api/libros/librosFiltros")
                                .param("titulo", "Libro Filtrado")
                                .param("page", "0")
                                .param("size", "10"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content[0].titulo").value("Libro Filtrado"));

                verify(libroService, times(1)).obtenerLibrosConFiltros("Libro Filtrado", null, null, null,
                                PageRequest.of(0, 10));
        }

        @Test
        @WithMockUser(roles = { "USER" })
        void testGetLibroById() throws Exception {
                LibroDto libroDto = new LibroDto();
                libroDto.setId(1L);
                libroDto.setTitulo("Libro Específico");

                when(libroService.getLibroById(1L)).thenReturn(libroDto);

                mockMvc.perform(get("/api/libros/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.titulo").value("Libro Específico"));

                verify(libroService, times(1)).getLibroById(1L);
        }

        @Test
        @WithMockUser(roles = { "ADMIN" })
        void testCrearLibro() throws Exception {
                LibroDto libroDto = new LibroDto();
                libroDto.setTitulo("Nuevo Libro");
                libroDto.setAutor("Autor Ejemplo"); // Agregar el autor para cumplir con @NotBlank
                libroDto.setGenero("Ficción"); // Asegura que otros campos requeridos estén completos
                libroDto.setAñoPublicacion(LocalDate.now()); // Ag

                when(libroService.crearLibro(libroDto)).thenReturn(libroDto);

                mockMvc.perform(post("/api/libros")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(libroDto)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.titulo").value("Nuevo Libro"));

                verify(libroService, times(1)).crearLibro(libroDto);
        }

        @Test
        @WithMockUser(roles = { "ADMIN" })
        void testActualizarLibro() throws Exception {
                LibroDto libroDto = new LibroDto();
                libroDto.setId(1L);
                libroDto.setTitulo("Libro actualizado");

                when(libroService.actualizarLibro(libroDto, 1L)).thenReturn(libroDto);

                try {
                        mockMvc.perform(put("/api/libros/1")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(libroDto)))
                                        .andExpect(status().isOk())
                                        .andExpect(jsonPath("$.titulo").value("Libro actualizado"));
                } catch (Exception e) {
                        e.printStackTrace(); //
                }
                verify(libroService, times(1)).actualizarLibro(libroDto, 1L);
        }

        @Test
        @WithMockUser(roles = { "ADMIN" })
        void testEliminarLibro() throws Exception {
                mockMvc.perform(delete("/api/libros/1"))
                                .andExpect(status().isNoContent());

                verify(libroService, times(1)).eliminarLibro(1L);
        }

}
