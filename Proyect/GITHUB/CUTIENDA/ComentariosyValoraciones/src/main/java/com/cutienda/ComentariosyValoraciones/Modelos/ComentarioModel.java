package com.cutienda.ComentariosyValoraciones.Modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "comentarios") // Nombre de la tabla en MySQL
public class ComentarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    @Column(name = "id_comentario") // Nombre de la columna para el ID
    private Long id; // ID del comentario, se generará automáticamente

    @Column(name = "id_vendedor") // Nombre de la columna para el vendedor
    private Long vendedor; // ID del vendedor al que se refiere el comentario

    @Column(name = "id_usuario") // Nombre de la columna para el usuario
    private Long usuario; // ID del usuario que realiza el comentario

    @Column(name = "comentario") // Nombre de la columna para el contenido
    private String contenido; // Contenido del comentario

    @Column(name = "puntuacion") // Nombre de la columna para la calificación
    private int calificacion; // Calificación del comentario (por ejemplo, del 1 al 5)

    @Column(name = "fecha_comentario") // Nombre de la columna para la fecha
    private String fechaCreacion; // Fecha de creación del comentario

    // Constructor
    public ComentarioModel() {
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVendedor() {
        return vendedor;
    }

    public void setVendedor(Long vendedor) {
        this.vendedor = vendedor;
    }

    public Long getUsuario() {
        return usuario;
    }

    public void setUsuario(Long usuario) {
        this.usuario = usuario;
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

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    }
