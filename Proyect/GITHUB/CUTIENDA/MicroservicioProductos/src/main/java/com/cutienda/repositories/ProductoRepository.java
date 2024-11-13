package com.cutienda.repositories;

import com.cutienda.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {  // Cambia de MongoRepository a JpaRepository y de String a Long
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
}

