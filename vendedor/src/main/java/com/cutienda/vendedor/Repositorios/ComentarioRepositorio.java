package com.cutienda.vendedor.Repositorios;


import com.cutienda.vendedor.Modelos.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComentarioRepositorio extends JpaRepository<Comentario, Long> {
    List<Comentario> findByIdVendedor(Long id_vendedor);  // Cambia a `findByIdVendedor`
}
