package com.cutienda.Notificaciones.Modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones") // Nombre de la tabla en la base de datos
public class Notificacion {

    // Getters y Setters
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera un ID autoincremental
    private Long id;

    @Setter
    @Getter
    private int id_usuario;
    @Setter
    @Getter
    private String mensaje;
    @Setter
    @Getter
    private String categoria;
    @Setter
    @Getter
    private LocalDateTime fecha;
    @Setter
    @Getter
    private boolean leido;

    // Constructor
    public Notificacion(int id_usuario,String mensaje, String categoria, LocalDateTime fecha, boolean leido) {
        this.id_usuario = id_usuario;
        this.mensaje = mensaje;
        this.categoria = categoria;
        this.fecha = fecha;
        this.leido = leido;
    }

    public Notificacion() {
    }

    // toString (opcional, puede ser útil para depuración)
    @Override
    public String toString() {
        return "Notificacion{" +
                "id=" + id +
                ", mensaje='" + mensaje + '\'' +
                ", categoria='" + categoria + '\'' +
                ", fecha=" + fecha +
                ", leido=" + leido +
                ", id_usuario=" + id_usuario +
                '}';
    }
}

