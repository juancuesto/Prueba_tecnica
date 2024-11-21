package com.Prueba_Tecnica_actividades.Prueba_Tecnica.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "actividades")
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    @Column(name = "numero_usuarios", nullable = false)
    private Integer numUsuarios;

    @Column(name = "capacidad_maxima", nullable = false)
    private Integer capacidadMaxima;

    @ManyToMany(mappedBy = "actividades",cascade = CascadeType.PERSIST)
    private List<Usuario> usuarios=new ArrayList<>();
}
