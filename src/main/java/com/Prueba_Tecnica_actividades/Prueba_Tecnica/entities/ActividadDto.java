package com.Prueba_Tecnica_actividades.Prueba_Tecnica.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActividadDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private Integer numUsuarios;

    private Integer capacidadMaxima;

    private List<Usuario> usuarios=new ArrayList<>();
}
