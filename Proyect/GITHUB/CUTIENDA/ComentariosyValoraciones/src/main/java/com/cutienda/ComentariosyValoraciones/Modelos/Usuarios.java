package com.cutienda.ComentariosyValoraciones.Modelos;

import jakarta.persistence.*;

import java.util.Base64;

@Entity
public class Usuarios {
    @Id
    private Long id;

    @Lob
    @Column(name = "foto_url", columnDefinition = "LONGBLOB") // Especifica que debe ser LONGBLOB
    private byte[] foto_url;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFoto() {
        return foto_url;
    }

    public void setFoto(byte[] foto_url) {
        this.foto_url = foto_url;
    }

    public String getFotoUrlBase64() {
        if (foto_url != null) {
            String tipoContenido;

            // Verificar el encabezado de la imagen
            if (foto_url.length > 1 && foto_url[0] == (byte) 0xFF && foto_url[1] == (byte) 0xD8) {
                tipoContenido = "image/jpeg"; // JPEG
            } else if (foto_url.length > 1 && foto_url[0] == (byte) 0x89 && foto_url[1] == (byte) 0x50) {
                tipoContenido = "image/png"; // PNG
            } else {
                tipoContenido = "image/jpeg"; // Por defecto
            }

            // Codificación a Base64
            return "data:" + tipoContenido + ";base64," + Base64.getEncoder().encodeToString(foto_url);
        }
        return "/path/to/default/image.png"; // Imagen por defecto si no hay foto
    }

}
