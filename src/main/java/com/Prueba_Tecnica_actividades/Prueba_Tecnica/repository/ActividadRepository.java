package com.Prueba_Tecnica_actividades.Prueba_Tecnica.repository;

import com.Prueba_Tecnica_actividades.Prueba_Tecnica.entities.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad,Long> {

    // Método para verificar si existe una actividad por nombre
    boolean existsByNombre(String nombre);

    // Método opcional para buscar actividad por nombre
    Optional<Actividad> findByNombre(String nombre);
}
