package com.cutienda.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "productos")
public class Producto {
    @Id
    private String id;
    private String nombre;
    private String vendedor;
    private String descripcion;
    private String contentType;
    private byte[] foto;
    private double precio;
}
