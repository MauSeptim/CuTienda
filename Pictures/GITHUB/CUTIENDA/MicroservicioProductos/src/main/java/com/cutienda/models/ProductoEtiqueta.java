package com.cutienda.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "productos-etiqueta")
public class ProductoEtiqueta {
    public ProductoEtiqueta() { }

    public ProductoEtiqueta(String idProducto, String idEtiqueta) {
        this.idProducto = idProducto;
        this.idEtiqueta = idEtiqueta;
    }

    @Id
    private String id;
    private String idProducto;
    private String idEtiqueta;

    public String getId() {
        return id;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public String getIdEtiqueta() {
        return idEtiqueta;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public void setIdEtiqueta(String idEtiqueta) {
        this.idEtiqueta = idEtiqueta;
    }
}
