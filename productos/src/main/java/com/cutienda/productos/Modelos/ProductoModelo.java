package com.cutienda.productos.Modelos;

import jakarta.persistence.*;


import java.math.BigDecimal;
import java.util.Base64;

@Entity
@Table(name = "productos")
public class ProductoModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_producto;

    private int id_usuario;
    private String nombreProducto;
    private String descripcion;
    private String tipo;

    @Column(precision = 10, scale = 2)
    private BigDecimal precio;

    @Lob
    @Column(name = "foto", columnDefinition = "LONGBLOB") // Especifica que debe ser LONGBLOB
    private byte[] foto;
    // Nuevas propiedades para latitud y longitud
    private Double latitud;
    private Double longitud;

    // Constructores
    public ProductoModelo() {}

    public ProductoModelo(Long id_producto, int id_usuario, String nombreProducto, String descripcion, String tipo, BigDecimal precio, byte[] foto, Double latitud, Double longitud) {
        this.id_producto = id_producto;
        this.id_usuario = id_usuario;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.precio = precio;
        this.foto = foto;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    // Getters y Setters
    public Long getId_producto() {
        return id_producto;
    }

    public void setId_producto(Long id_producto) {
        this.id_producto = id_producto;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getFotoBase64() {
        if (foto != null && foto.length > 1) {
            String tipoContenido;

            // Verificar el tipo de imagen
            if (foto[0] == (byte) 0xFF && foto[1] == (byte) 0xD8) {
                tipoContenido = "image/jpeg"; // JPEG
            } else if (foto[0] == (byte) 0x89 && foto[1] == (byte) 0x50) {
                tipoContenido = "image/png"; // PNG
            } else if (foto[0] == (byte) 0x47 && foto[1] == (byte) 0x49) {
                tipoContenido = "image/gif"; // GIF
            } else {
                tipoContenido = "application/octet-stream"; // Tipo por defecto
            }

            // Codificar a Base64
            return "data:" + tipoContenido + ";base64," + Base64.getEncoder().encodeToString(foto);
        }
        // Imagen por defecto
        return "data:image/png;base64,";
    }

}


