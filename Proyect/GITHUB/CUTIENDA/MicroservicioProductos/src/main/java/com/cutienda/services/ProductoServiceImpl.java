package com.cutienda.services;

import com.cutienda.models.Producto;
import com.cutienda.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public Producto guardar(Producto producto) {
        if (producto != null) {
            return productoRepository.save(producto);
        } else {
            return null;
        }
    }

    @Override
    public Producto eliminarPorId(Long id) {  // Cambiar de String a Long
        Producto eliminado = conseguirPorId(id);

        if (eliminado != null) {
            productoRepository.deleteById(id);
        }
        return eliminado;
    }

    @Override
    public Producto conseguirPorId(Long id) {  // Cambiar de String a Long
        Optional<Producto> opc = productoRepository.findById(id);
        return opc.orElse(null);
    }

    @Override
    public List<Producto> conseguirTodos() {
        return productoRepository.findAll();
    }

    @Override
    public void actualizarPorId(String id, Producto producto) {

    }


    @Override
    public List<Producto> obtenerPorNombreCoincidente(String nombreProducto) {
        List<Producto> coincidentes = productoRepository.findByNombreContainingIgnoreCase(nombreProducto);
        return coincidentes != null ? coincidentes : List.of();
    }
}
