package com.cutienda.productos.Services;

import com.cutienda.productos.Modelos.ProductoModelo;
import com.cutienda.productos.Repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Método para guardar un nuevo producto
    public ProductoModelo guardarProducto(ProductoModelo producto) {
        return productoRepository.save(producto);
    }

    // Método para conseguir todos los productos
    public List<ProductoModelo> obtenerTodosProductos() {
        return productoRepository.findAll();
    }

    // Método para conseguir un producto por su ID
    public ProductoModelo obtenerProductoPorId(Long id_producto) {
        Optional<ProductoModelo> producto = productoRepository.findById(id_producto);
        return producto.orElse(null); // Retorna null si no encuentra el producto
    }

    // Método para actualizar un producto
    public ProductoModelo actualizarProducto(ProductoModelo producto) {
        ProductoModelo productoExistente = productoRepository.findById(producto.getId_producto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        productoExistente.setNombreProducto(producto.getNombreProducto());
        productoExistente.setDescripcion(producto.getDescripcion());
        productoExistente.setTipo(producto.getTipo());
        productoExistente.setPrecio(producto.getPrecio());
        productoExistente.setFoto(producto.getFoto());
        productoExistente.setLatitud(producto.getLatitud());
        productoExistente.setLongitud(producto.getLongitud());

        return productoRepository.save(productoExistente);
    }

    // Método para eliminar un producto
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    public List<ProductoModelo> buscarProductosPorNombre(String nombreProducto) {
        return productoRepository.findByNombreProductoContainingIgnoreCase(nombreProducto);
    }
}
