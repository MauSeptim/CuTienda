package com.cutienda.ComentariosyValoraciones.Repository;

import com.cutienda.ComentariosyValoraciones.Modelos.ComentarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComentarioRepositories extends JpaRepository<ComentarioModel, Long> {
    // Aquí puedes definir métodos personalizados si es necesario
    List<ComentarioModel> findByVendedor(Long vendedorid);
}


