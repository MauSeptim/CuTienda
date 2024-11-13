package com.cutienda.dto;

public class ProductoDTO {
    private Long idProducto;
    private String nombre;
    private String idUsuario;
    private String descripcion;
    private String foto; // Base64
    private double precio;
    private String tipo;

    public ProductoDTO(Long idProducto, String nombre, String idUsuario, String descripcion, String foto, double precio, String tipo) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.idUsuario = idUsuario;
        this.descripcion = descripcion;
        this.foto = foto;
        this.precio = precio;
        this.tipo = tipo;
    }

    // Getters y Setters
    public Long getIdProducto() { return idProducto; }
    public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
