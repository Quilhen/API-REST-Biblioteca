package com.davidgt.springboot.app.springboot_biblioteca.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.davidgt.springboot.app.springboot_biblioteca.dto.PrestamoDto;
import com.davidgt.springboot.app.springboot_biblioteca.dto.UsuarioDto;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Prestamo;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Rol;
import com.davidgt.springboot.app.springboot_biblioteca.entity.Usuario;
import com.davidgt.springboot.app.springboot_biblioteca.exception.ResourceNotFoundException;
import com.davidgt.springboot.app.springboot_biblioteca.mapper.PrestamoMapper;
import com.davidgt.springboot.app.springboot_biblioteca.mapper.UsuarioMapper;
import com.davidgt.springboot.app.springboot_biblioteca.repository.RolRepository;
import com.davidgt.springboot.app.springboot_biblioteca.repository.UsuarioRepository;

/**
 * Pruebas unitarias para UsuarioService:
 * - Valida la correcta funcionalidad de operaciones CRUD de usuarios.
 * - Asegura que el servicio maneje correctamente roles, autenticación y
 * recuperación de préstamos.
 */
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private PrestamoMapper prestamoMapper;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba que valida la obtención de todos los usuarios en el sistema.
     */
    @Test
    void testGetAllUsuarios_DeberiaRetornarListaDeUsuarios() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        UsuarioDto usuarioDto = new UsuarioDto();

        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));
        when(usuarioMapper.toUsuarioDtoList(List.of(usuario))).thenReturn(List.of(usuarioDto));

        List<UsuarioDto> result = usuarioService.getAllUsuarios();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(usuarioRepository).findAll();
        verify(usuarioMapper).toUsuarioDtoList(List.of(usuario));
    }

    /**
     * Prueba que verifica que se retornen los préstamos de un usuario existente.
     */
    @Test
    void testGetUsuariosPrestamos_DeberiaRetornarPrestamosCuandoUsuarioExiste() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setPrestamos(new ArrayList<>());

        Prestamo prestamo = new Prestamo();
        usuario.getPrestamos().add(prestamo);

        PrestamoDto prestamoDto = new PrestamoDto();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(prestamoMapper.prestamoToPrestamoDto(prestamo)).thenReturn(prestamoDto);

        List<PrestamoDto> result = usuarioService.getUsuariosPrestamos(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(prestamoDto, result.get(0));
        verify(usuarioRepository).findById(1L);
        verify(prestamoMapper).prestamoToPrestamoDto(prestamo);
    }

    /**
     * Prueba que verifica la excepción lanzada al no encontrar un usuario.
     */
    @Test
    void testGetUsuariosPrestamos_DeberiaLanzarResourceNotFoundExceptionCuandoUsuarioNoExiste() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.getUsuariosPrestamos(1L));
        verify(usuarioRepository).findById(1L);
    }

    /**
     * Prueba que valida el guardado de un usuario con el rol "ROLE_USER".
     */
    @Test
    void testSave_DeberiaGuardarUsuarioConRolUsuario() {
        Usuario usuario = new Usuario();
        usuario.setPassword("password123");
        UsuarioDto usuarioDto = new UsuarioDto();

        Rol rolUsuario = new Rol();
        rolUsuario.setNombre("ROLE_USER");
        when(rolRepository.findByNombre("ROLE_USER")).thenReturn(Optional.of(rolUsuario));
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(usuarioMapper.usuarioToUsuarioDto(usuario)).thenReturn(usuarioDto);

        UsuarioDto result = usuarioService.save(usuario);

        assertNotNull(result);
        assertEquals("encodedPassword", usuario.getPassword());
        assertTrue(usuario.getRoles().contains(rolUsuario));
        verify(usuarioRepository).save(usuario);
        verify(passwordEncoder).encode("password123");
    }

    /**
     * Prueba que verifica si un nombre de usuario ya existe en el sistema.
     */
    @Test
    void testExistsByUsername_DeberiaRetornarTrueSiUsuarioExiste() {
        when(usuarioRepository.existsByNombreUsuario("usuarioExistente")).thenReturn(true);

        boolean result = usuarioService.existsByUsername("usuarioExistente");

        assertTrue(result);
        verify(usuarioRepository).existsByNombreUsuario("usuarioExistente");
    }

    /**
     * Prueba que verifica la actualización de un usuario existente.
     */
    @Test
    void testActualizarUsuario_DeberiaActualizarUsuarioCuandoExiste() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setNombreUsuario("nuevoNombre");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(usuarioMapper.usuarioToUsuarioDto(usuario)).thenReturn(usuarioDto);

        UsuarioDto result = usuarioService.actualizarUsuario(1L, usuarioDto);

        assertNotNull(result);
        assertEquals("nuevoNombre", usuario.getNombreUsuario());
        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).save(usuario);
    }

    /**
     * Prueba que verifica la excepción lanzada al intentar actualizar un usuario
     * inexistente.
     */
    @Test
    void testActualizarUsuario_DeberiaLanzarResourceNotFoundExceptionCuandoNoExiste() {
        UsuarioDto usuarioDto = new UsuarioDto();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.actualizarUsuario(1L, usuarioDto));
        verify(usuarioRepository).findById(1L);
    }

    /**
     * Prueba que verifica la eliminación de un usuario existente.
     */
    @Test
    void testEliminarUsuario_DeberiaEliminarUsuarioCuandoExiste() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        UsuarioDto usuarioDto = new UsuarioDto();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.usuarioToUsuarioDto(usuario)).thenReturn(usuarioDto);

        UsuarioDto result = usuarioService.eliminarUsuario(1L);

        assertNotNull(result);
        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).delete(usuario);
    }

    /**
     * Prueba que verifica la excepción lanzada al intentar eliminar un usuario
     * inexistente.
     */
    @Test
    void testEliminarUsuario_DeberiaLanzarResourceNotFoundExceptionCuandoNoExiste() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.eliminarUsuario(1L));
        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository, never()).delete(any());
    }

}
