package com.Prueba_Tecnica_actividades.Prueba_Tecnica.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String nombre;
    private String apellidos;
    private Integer edad;

    @Column(unique = true, nullable = false)
    private String email;

    @ManyToMany
    @JoinTable(name = "usuario_actividad",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "actividad_id"))
    @JsonIgnore
    private List<Actividad> actividades;
}
