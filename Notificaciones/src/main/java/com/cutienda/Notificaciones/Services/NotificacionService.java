package com.cutienda.Notificaciones.Services;

import com.cutienda.Notificaciones.Modelos.Notificacion;
import com.cutienda.Notificaciones.Repositories.NotificacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepositorio notificacionRepository;

    // Obtener todas las notificaciones
    public List<Notificacion> obtenerTodasLasNotificaciones() {
        return notificacionRepository.findAll();
    }

    // Obtener una notificación por su ID
    public Optional<Notificacion> obtenerNotificacionPorId(Long id) {
        return notificacionRepository.findById(id);
    }

    // Crear una nueva notificación
    public Notificacion crearNotificacion(int id_usuario, String mensaje, String categoria, LocalDateTime fecha, boolean leido) {
        Notificacion nuevaNotificacion = new Notificacion(id_usuario ,mensaje, categoria, fecha, leido);
        return notificacionRepository.save(nuevaNotificacion);
    }


    public Notificacion marcarNotificacionComoLeida(Long id) {
        Optional<Notificacion> notificacionOptional = notificacionRepository.findById(id);
        if (notificacionOptional.isPresent()) {
            Notificacion notificacion = notificacionOptional.get();
            notificacion.setLeido(true); // Cambia el estado a "leído"
            return notificacionRepository.save(notificacion); // Guarda los cambios en la base de datos
        }
        throw new RuntimeException("Notificación no encontrada con el ID: " + id);
    }
    public List<Notificacion> buscarNotificacionesExceptoEsteIdUser(int userId) {
        return notificacionRepository.findByIdUsuarioNot(userId);
    }

    // Eliminar una notificación
    public void eliminarNotificacion(Long id) {
        notificacionRepository.deleteById(id);
    }
}

