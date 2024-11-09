package com.cutienda.usuarios.models;

import jakarta.persistence.*;

import java.util.Base64;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Cambiar a Long para MySQL
    private String nombre;
    private String apellidos;
    private String correoElectronico;
    private String telefono;
    private String tipo; // admin o client
    private String contraseña;
    @Lob
    @Column(name = "foto_url", columnDefinition = "LONGBLOB") // Especifica que debe ser LONGBLOB
    private byte[] fotoUrl;
    private String confirmacionContraseña;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public byte[] getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(byte[] fotoUrl) {
        this.fotoUrl = fotoUrl;
    }


    public String getFotoUrlBase64() {
        if (fotoUrl != null && fotoUrl.length > 1) {
            String tipoContenido;

            // Verificar el tipo de imagen
            if (fotoUrl[0] == (byte) 0xFF && fotoUrl[1] == (byte) 0xD8) {
                tipoContenido = "image/jpeg"; // JPEG
            } else if (fotoUrl[0] == (byte) 0x89 && fotoUrl[1] == (byte) 0x50) {
                tipoContenido = "image/png"; // PNG
            } else if (fotoUrl[0] == (byte) 0x47 && fotoUrl[1] == (byte) 0x49) {
                tipoContenido = "image/gif"; // GIF
            } else {
                tipoContenido = "application/octet-stream"; // Tipo por defecto
            }

            // Codificar a Base64
            return "data:" + tipoContenido + ";base64," + Base64.getEncoder().encodeToString(fotoUrl);
        }
        // Imagen por defecto
        return "data:image/png;base64,";
    }



    public String getConfirmacionContraseña() {
        return confirmacionContraseña;
    }

    public void setConfirmacionContraseña(String confirmacionContraseña) {
        this.confirmacionContraseña = confirmacionContraseña;
    }
}
