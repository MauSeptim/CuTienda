package com.cutienda.productos.Services;

import com.cutienda.productos.Modelos.ProductoModelo;
import com.cutienda.productos.Repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public void guardarProducto(ProductoModelo producto) {
        productoRepository.save(producto);
    }
    public ProductoModelo obtenerProductoPorId(Long id_producto) {
        // Optional para manejar el caso de que el producto no exista
        Optional<ProductoModelo> producto = productoRepository.findById(id_producto);
        return producto.orElse(null); // Retorna null si no encuentra el producto
    }

    // Método para actualizar un producto
    public void actualizarProducto(ProductoModelo producto) {
        // Aquí solo se actualizan los campos relevantes
        ProductoModelo productoExistente = productoRepository.findById(producto.getId_producto()).orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        productoExistente.setNombre_producto(producto.getNombre_producto());
        productoExistente.setDescripcion(producto.getDescripcion());
        productoExistente.setTipo(producto.getTipo());
        productoExistente.setPrecio(producto.getPrecio());

        productoRepository.save(productoExistente);
    }
    public void eliminarProducto(Long id) {
        // Llama al repositorio para eliminar el producto
        productoRepository.deleteById(id);
    }


}
