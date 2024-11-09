package com.cutienda.productos.Repositories;

import com.cutienda.productos.Modelos.ProductoModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoModelo, Long> {
}

