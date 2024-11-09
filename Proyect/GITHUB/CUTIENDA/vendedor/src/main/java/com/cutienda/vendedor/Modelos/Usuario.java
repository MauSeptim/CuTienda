package com.cutienda.vendedor.Modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.Base64;
import java.util.Objects;

@Entity
public class Usuario {
    // Getters y Setters
    @Getter
    @Setter
    @Id
    private Long id;
    @Getter
    @Setter
    @Column(name = "nombre") // Nombre de la columna para el contenido
    private String nombre;
    @Getter
    @Setter
    @Column(name = "apellidos") // Nombre de la columna para el contenido
    private String apellidos;
    @Getter
    @Setter
    @Column(name = "correo_electronico") // Nombre de la columna para el contenido
    private String correo_electronico;
    @Getter
    @Setter
    @Column(name = "telefono") // Nombre de la columna para el contenido
    private String telefono;


}
