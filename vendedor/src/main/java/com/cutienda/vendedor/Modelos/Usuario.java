package com.cutienda.vendedor.Modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.Base64;
import java.util.Objects;

@Entity
@Table(name = "usuarios")
public class Usuario {
    // Getters y Setters
    @Getter
    @Setter
    @Id
    private Long id;
    @Getter
    @Setter
    @Column(name = "nombre") // Nombre de la columna para el contenido
    private String nombre;
    @Getter
    @Setter
    @Column(name = "apellidos") // Nombre de la columna para el contenido
    private String apellidos;
    @Getter
    @Setter
    @Column(name = "correo_electronico") // Nombre de la columna para el contenido
    private String correo_electronico;
    @Getter
    @Setter
    @Column(name = "telefono") // Nombre de la columna para el contenido
    private String telefono;
    @Lob
    @Column(name = "foto_url", columnDefinition = "LONGBLOB") // Especifica que debe ser LONGBLOB
    private byte[] fotoUrl;

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


}
