package com.cutienda.productos.Repositories;

import com.cutienda.productos.Modelos.ProductoModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<ProductoModelo, Long> {
    List<ProductoModelo> findByNombreProductoContainingIgnoreCase(String nombreProducto);
}

