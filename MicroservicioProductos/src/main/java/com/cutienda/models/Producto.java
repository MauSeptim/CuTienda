package com.cutienda.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "productos")
public class Producto {
    public Producto(String nombre, String vendedor, String descripcion, String foto, double precio) {
        this.nombre = nombre;
        this.vendedor = vendedor;
        this.descripcion = descripcion;
        this.foto = foto;
        this.precio = precio;
    }

    @Id
    private String id;
    private String nombre;
    private String vendedor;
    private String descripcion;
    private String foto;
    private double precio;

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
