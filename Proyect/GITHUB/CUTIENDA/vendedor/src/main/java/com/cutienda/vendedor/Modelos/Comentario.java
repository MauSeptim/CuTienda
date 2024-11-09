package com.cutienda.vendedor.Modelos;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario")
    private Long id;

    @Column(name = "id_vendedor") // Nombre de la columna en la base de datos sigue igual
    private Long idVendedor; // Cambia el nombre del campo a `idVendedor`

    @Column(name = "comentario")
    private String contenido;

    @Column(name = "puntuacion")
    private int calificacion;

    // Constructor
    public Comentario() {}

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdVendedor() {  // Cambia el getter a `getIdVendedor`
        return idVendedor;
    }

    public void setIdVendedor(Long idVendedor) {  // Cambia el setter a `setIdVendedor`
        this.idVendedor = idVendedor;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }
}
