package com.cutienda.Notificaciones.Repositories;

import com.cutienda.Notificaciones.Modelos.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Anotación para marcar esta interfaz como un repositorio
public interface NotificacionRepositorio extends JpaRepository<Notificacion, Long> {
    // Aquí puedes agregar métodos personalizados si los necesitas
    List<Notificacion> findByIdUsuarioNot(int idUsuario);
}
