package com.cutienda.vendedor.Modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")  // Nombre de la columna en la base de datos
    private Long id;

    @Column(name = "id_usuario")  // Nombre de la columna en la base de datos
    private int id_usuario;

    @Column(name = "latitud")  // Nombre de la columna en la base de datos
    private Double latitud;

    @Column(name = "longitud")  // Nombre de la columna en la base de datos
    private Double longitud;

    // Nueva propiedad: dirección
    @Column(name = "direccion", length = 255)  // Puedes especificar una longitud o cualquier otra propiedad
    private String direccion;

    // Constructores
    public producto() {}

    public producto(Long id, int id_usuario, Double latitud, Double longitud, String direccion) {
        this.id = id;
        this.id_usuario = id_usuario;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = direccion;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
