package com.Prueba_Tecnica_actividades.Prueba_Tecnica.controller;

import com.Prueba_Tecnica_actividades.Prueba_Tecnica.entities.Usuario;
import com.Prueba_Tecnica_actividades.Prueba_Tecnica.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appActivitats")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Crear un nuevo usuario
    @PostMapping("/user")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        try {
            return new ResponseEntity<>(usuarioService.crearUsuario(usuario), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>("Ha ocurrido un error inesperado", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar un usuario existente
    @PutMapping("/users/{id}")
    public ResponseEntity<?> actualizarUsuario(@RequestBody Usuario usuario, @PathVariable Long id) {
        try {
            return usuarioService.actualizarUsuario(usuario,id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Ha ocurrido un error inesperado", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Consultar un usuario por ID
    @GetMapping("/users/{id}")
    public ResponseEntity<?> consultarUsuarioById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(usuarioService.buscarUsuarioById(id),HttpStatus.FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Ha ocurrido un error inesperado",HttpStatus.BAD_REQUEST);
        }
    }

    // Listar todos los usuarios
    @GetMapping("/users")
    public ResponseEntity<?> listarUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.listarUsuarios();
            if (usuarios.isEmpty()) {
                return new ResponseEntity<>("No hay usuarios registradoso",HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(usuarios,HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Ha ocurrido un error inesperado",HttpStatus.BAD_REQUEST);
        }
    }

    // Borrar un usuario por ID
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> borrarUsuarioById(@PathVariable Long id) {
        try {
            String mensaje = usuarioService.deleteUsuarioById(id);
            return new ResponseEntity<>("El usuario se ha borrado correctamente",HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>("Ha ocurrido un error inesperado",HttpStatus.BAD_REQUEST);
        }
    }
}
