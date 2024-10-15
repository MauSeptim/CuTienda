package com.cutienda.ComentariosyValoraciones.Modelos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comentarios") // Nombre de la colección en MongoDB
public class ComentarioModel {

    @Id
    private String id; // ID del comentario, se generará automáticamente

    private String usuarioId; // ID del usuario que hizo el comentario
    private String vendedorId; // ID del vendedor al que se refiere el comentario
    private String contenido; // Contenido del comentario
    private int calificacion; // Calificación del comentario (por ejemplo, del 1 al 5)
    private String fechaCreacion; // Fecha de creación del comentario

    // Constructor
    public ComentarioModel() {
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(String vendedorId) {
        this.vendedorId = vendedorId;
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
