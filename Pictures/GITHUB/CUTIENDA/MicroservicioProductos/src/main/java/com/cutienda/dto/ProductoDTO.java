package com.cutienda.dto;

public class ProductoDTO {
    private String id;
    private String nombre;
    private String vendedor;
    private String descripcion;
    private String foto; // Base64
    private double precio;

    public ProductoDTO(String id, String nombre, String vendedor, String descripcion, String foto, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.vendedor = vendedor;
        this.descripcion = descripcion;
        this.foto = foto;
        this.precio = precio;
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getVendedor() { return vendedor; }
    public String getDescripcion() { return descripcion; }
    public String getFoto() { return foto; }
    public double getPrecio() { return precio; }
}