package com.cutienda.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "productos")
public class Producto {
    public Producto(String contentType, String descripcion, byte[] foto, String nombre, double precio, String vendedor) {
        this.contentType = contentType;
        this.descripcion = descripcion;
        this.foto = foto;
        this.nombre = nombre;
        this.precio = precio;
        this.vendedor = vendedor;
    }

    @Id
    private String id;
    private String nombre;
    private String vendedor;
    private String descripcion;
    private String contentType;
    private byte[] foto;
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

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
