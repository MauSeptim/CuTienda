package com.cutienda.Notificaciones.Modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones") // Nombre de la tabla en la base de datos
@Getter
@Setter
public class Notificacion {

    // Getters y Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera un ID autoincremental
    private Long id;
    private int idUsuario;
    private String mensaje;
    private String categoria;
    private LocalDateTime fecha;
    private boolean leido;

    // Constructor
    public Notificacion(int idUsuario, String mensaje, String categoria, LocalDateTime fecha, boolean leido) {
        this.idUsuario = idUsuario;
        this.mensaje = mensaje;
        this.categoria = categoria;
        this.fecha = fecha;
        this.leido = leido;
    }

    public void setLeido(boolean leido) {
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
                ", id_usuario=" + idUsuario +
                '}';
    }
}

