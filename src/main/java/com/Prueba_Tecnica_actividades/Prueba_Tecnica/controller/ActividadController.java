package com.Prueba_Tecnica_actividades.Prueba_Tecnica.controller;

import com.Prueba_Tecnica_actividades.Prueba_Tecnica.entities.Actividad;
import com.Prueba_Tecnica_actividades.Prueba_Tecnica.service.ActividadService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/appActivitats")
public class ActividadController {

    @Autowired
    private ActividadService actividadService;

    // Crear una nueva actividad
    @PostMapping("/activitats")
    public ResponseEntity<?> crearActividad(@RequestBody Actividad actividad) {
        try {
            return actividadService.crearActividad(actividad);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>("HA OCURRIDO UN ERROR INESPERADO", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Consultar una actividad por ID
    @GetMapping("/activitats/{id}")
    public ResponseEntity<?> consultarActividadById(@PathVariable Long id) {
        try {
            Actividad actividad = actividadService.consultarActividadPorId(id);
            if (actividad == null) {
                return new ResponseEntity<>("no se encontro la actividad especificada", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(actividad);
        } catch (Exception e) {
            return new ResponseEntity<>("HA OCURRIDO UN ERROR INESPERADO", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Apuntarse a una actividad

    @PostMapping("/activitats/apuntarse/{usuarioId}/{actividadId}")
    public ResponseEntity<String> apuntarseActividad(@PathVariable Long usuarioId, @PathVariable Long actividadId) {
        try {
            return new ResponseEntity<>(actividadService.apuntarseActividad(usuarioId, actividadId),HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("HA OCURRIDO UN ERROR INESPERADO", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Importar actividades

    @PostMapping("/importar")
    public ResponseEntity<?> importarActividades() {
        try {
            return actividadService.cargarActividades();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>("HA OCURRIDO UN ERROR INESPERADO", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/exportarActividades/{usuario_id}")
    public ResponseEntity<?> exportarActividades(@PathVariable Long usuario_id){
        try {
            return actividadService.exportarListadoActividades(usuario_id);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>("HA OCURRIDO UN ERROR INESPERADO", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
