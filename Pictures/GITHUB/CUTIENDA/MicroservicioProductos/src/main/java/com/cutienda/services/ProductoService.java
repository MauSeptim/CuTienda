package com.cutienda.services;


import com.cutienda.models.Producto;

import java.util.List;

public interface ProductoService {
    public Producto guardar(Producto producto);
    public Producto eliminarPorId(String id);
    public Producto conseguirPorId(String id);
    public List<Producto> conseguirTodos();
    public void actualizarPorId(String id, Producto producto);
    public List<Producto> obtenerPorNombreCoincidente(String nombre);
}
