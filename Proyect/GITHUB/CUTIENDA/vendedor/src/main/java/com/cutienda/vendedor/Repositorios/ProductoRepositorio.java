package com.cutienda.vendedor.Repositorios;

import com.cutienda.vendedor.Modelos.Usuario;
import com.cutienda.vendedor.Modelos.producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepositorio extends JpaRepository<producto, Long> {
}
