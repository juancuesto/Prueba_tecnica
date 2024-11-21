package com.Prueba_Tecnica_actividades.Prueba_Tecnica.service;

import com.Prueba_Tecnica_actividades.Prueba_Tecnica.entities.Usuario;
import com.Prueba_Tecnica_actividades.Prueba_Tecnica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Crear un nuevo usuario
    public ResponseEntity<?> crearUsuario(Usuario usuario) {
        if (usuario.getNombre().isEmpty()){
            return new ResponseEntity<>("Debes introducir un nombre de usuario", HttpStatus.BAD_REQUEST);
        } else if (usuario.getEmail().isEmpty()) {
            return new ResponseEntity<>("Debes introducir un email",HttpStatus.BAD_REQUEST);
        }
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            return new ResponseEntity<>("El email ya está registrado.",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(usuarioRepository.save(usuario),HttpStatus.CREATED);
    }

    // Listar todos los usuarios
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Buscar un usuario por su ID
    public ResponseEntity<?> buscarUsuarioById(Long id) {
        Optional<Usuario> usuarioOptional=usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()){
            return new ResponseEntity<>("No se ha encontrado el usuario con id: "+id,HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(usuarioOptional.get(),HttpStatus.OK);
        }
    }
    // Actualizar un usuario existente
    public ResponseEntity<?> actualizarUsuario(Usuario usuario, Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()) {
            return new ResponseEntity<>("No se ha encontrado el usuario a actualizar",HttpStatus.NOT_FOUND);
        }

        Usuario usuarioExistente = usuarioOptional.get();
        if (usuario.getNombre() != null) {
            usuarioExistente.setNombre(usuario.getNombre());
        }
        if (usuario.getEmail() != null) {
            usuarioExistente.setEmail(usuario.getEmail());
        }
        if (usuario.getApellidos() != null) {
            usuarioExistente.setApellidos(usuario.getApellidos());
        }
        if (usuario.getEdad() != null) {
            usuarioExistente.setEdad(usuario.getEdad());
        }
        if (usuario.getActividades() != null) {
            usuarioExistente.setActividades(usuario.getActividades());
        }
        ;
        return new ResponseEntity<>(usuarioRepository.save(usuarioExistente),HttpStatus.OK);
    }

    // Eliminar un usuario por su ID
    public String deleteUsuarioById(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()) {
            return "No se ha encontrado el usuario a borrar.";
        }
        usuarioRepository.deleteById(id);
        return "El usuario con ID: " + id + " ha sido borrado correctamente.";
    }
}
