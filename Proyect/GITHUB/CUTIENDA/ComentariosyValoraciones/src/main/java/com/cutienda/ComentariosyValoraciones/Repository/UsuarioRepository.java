package com.cutienda.ComentariosyValoraciones.Repository;

import com.cutienda.ComentariosyValoraciones.Modelos.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {

}