package com.cutienda.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "etiquetas")
public class Etiqueta {
    @Id
    private String id;
    private String nombre;

    public String getId() {
        return id;
    }

    public Etiqueta(){}

    public Etiqueta(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
