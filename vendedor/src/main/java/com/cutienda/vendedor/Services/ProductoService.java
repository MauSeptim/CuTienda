package com.cutienda.vendedor.Services;

import com.cutienda.vendedor.Modelos.producto;
import com.cutienda.vendedor.Repositorios.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductoService {

    // Inyección de dependencias del repositorio de productos
    @Autowired
    private ProductoRepositorio productoRepository;

    public producto obtenerProductoPorId(Long id) {
        // Optional para manejar el caso de que el producto no exista
        Optional<producto> producto = productoRepository.findById(id);
        return producto.orElse(null); // Retorna null si no encuentra el producto
    }

}