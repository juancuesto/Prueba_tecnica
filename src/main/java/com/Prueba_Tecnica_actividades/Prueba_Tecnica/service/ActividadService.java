package com.Prueba_Tecnica_actividades.Prueba_Tecnica.service;

import com.Prueba_Tecnica_actividades.Prueba_Tecnica.entities.Actividad;
import com.Prueba_Tecnica_actividades.Prueba_Tecnica.entities.Usuario;
import com.Prueba_Tecnica_actividades.Prueba_Tecnica.repository.ActividadRepository;
import com.Prueba_Tecnica_actividades.Prueba_Tecnica.repository.UsuarioRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ActividadService {
    @Autowired
    private ActividadRepository actividadRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Crear una nueva actividad
    public ResponseEntity<?> crearActividad(Actividad actividad) {
        if (actividad.getNombre() == null || actividad.getNombre().isEmpty()) {
            return new ResponseEntity<>("el nombre de la actividad es obligatorio",HttpStatus.BAD_REQUEST);
        }
        if (actividad.getDescripcion() == null || actividad.getDescripcion().isEmpty()) {
            return new ResponseEntity<>("la descripcion es obligatoria",HttpStatus.BAD_REQUEST);
        }

        if (actividadRepository.existsByNombre(actividad.getNombre())) {
            return new ResponseEntity<>("esta actividad ya existe",HttpStatus.BAD_REQUEST);
        }

        ;
        return new ResponseEntity<>(actividadRepository.save(actividad),HttpStatus.CREATED);
    }

    // Consultar todas las actividades disponibles
    public List<Actividad> listarActividades() {
        return actividadRepository.findAll();
    }

    // Consultar una actividad por su ID
    public Actividad consultarActividadPorId(Long actividadId) {
        return actividadRepository.findById(actividadId).orElse(null);
    }

    // Apuntarse a una actividad
    public String apuntarseActividad(Long usuarioId, Long actividadId) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        if (usuarioOptional.isEmpty()) {
            return "No se ha encontrado el usuario con ID: " + usuarioId;
        }

        Optional<Actividad> actividadOptional = actividadRepository.findById(actividadId);
        if (actividadOptional.isEmpty()) {
            return "No se ha encontrado la actividad con ID: " + actividadId;
        }

        Actividad actividad = actividadOptional.get();
        if (actividad.getNumUsuarios() >= actividad.getCapacidadMaxima()) {
            return "La actividad no tiene plazas disponibles";
        }

        // Incrementar el número de usuarios en la actividad
        actividad.setNumUsuarios(actividad.getNumUsuarios() + 1);

        // Asociar el usuario a la actividad
        Usuario usuario = usuarioOptional.get();
        usuario.getActividades().add(actividad);

        // Guardar los cambios
        usuarioRepository.save(usuario);
        actividadRepository.save(actividad);

        return "El usuario " + usuario.getNombre() + " se ha apuntado a la actividad " + actividad.getNombre() + " correctamente";
    }

    // Importar actividades

    public List<Actividad> listadoActividadesDisponibles() {
        log.info("estamos dentro de listado de skins disponibles");
        String ruta = ".\\src\\main\\resources\\data\\activitats.json";
        List<Actividad> listadoActividades = new ArrayList<>();
        try {
            String contenidoJson = new String(Files.readAllBytes(Paths.get(ruta)));

            ObjectMapper objectMapper = new ObjectMapper();

            TypeReference<List<Actividad>> jacksonTypeReference = new TypeReference<List<Actividad>>() {
            };
            listadoActividades = objectMapper.readValue(contenidoJson, jacksonTypeReference);
        } catch (IOException exception) {
            System.out.println("error: " + exception.getMessage());
        }
        return listadoActividades;
    }

    public ResponseEntity<?> cargarActividades() {
        try {
            log.info("estamos cargando actividades");
            List<Actividad> listadoActividades = this.listadoActividadesDisponibles();
            for (Actividad ele : listadoActividades) {
                System.out.println("la actividad cargada es: " + ele);
                this.crearActividad(ele);
            }
            return new ResponseEntity<>("El listado de actividades se ha cargado correctamente", HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>("No se ha podido cargar el listado de actividades en base de datos", HttpStatus.NOT_ACCEPTABLE);
    }

    public ResponseEntity<?> exportarListadoActividades(Long usuario_id) {
        log.info("estamos exportando actividades");
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuario_id);
        if (usuarioOptional.isEmpty()) {
            log.info("No se encontro el uuuusususususususurioooooooo");
            return new ResponseEntity<>("No se ha encontrado el usuario buscado", HttpStatus.NOT_FOUND);
        } else {

            log.info("Estamos dentro del else vamos a crear el JSONObject");
            JSONObject actvividadJSON = new JSONObject();
            List<Actividad> listado = usuarioOptional.get().getActividades();
            JSONArray listadoJson = new JSONArray();

            for (Actividad ele : listado) {
                //creo un objeto JSON
                JSONObject aux = new JSONObject();
                aux.put("id", ele.getId());
                aux.put("nombre", ele.getNombre());
                aux.put("descripcion", ele.getDescripcion());
                aux.put("numUsuarios", ele.getNumUsuarios());
                aux.put("capacidadMaxima", ele.getCapacidadMaxima());

                log.info("Estamos dentro del for hemos creado el JSONObject");

                listadoJson.add(aux);
            }
            actvividadJSON.put("lista", listadoJson);
            try {
                log.info("Estamos dentro del try vamos a crear el fichero");
                FileWriter file = new FileWriter("C:\\Users\\juanc\\Desktop\\java_ejemplo\\actividad.json");
                System.out.println("el tamaño del array es:"+listadoJson.size());
                for (Object ele:listadoJson) {
                    file.write(ele.toString());
                    file.flush();
                }


                return new ResponseEntity<>("El fichero se ha exportado correctamente", HttpStatus.OK);
            } catch (JsonProcessingException e) {
                System.out.println("error procesando el JSON------------------------");
                throw new RuntimeException(e.getMessage());
            } catch (IOException e) {
                System.out.println("Error al escribir en el fichero********************************");
            }
        }
        return null;
    }

}
