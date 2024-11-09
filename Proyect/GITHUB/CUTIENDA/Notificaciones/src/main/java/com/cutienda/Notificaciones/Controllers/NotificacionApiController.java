package com.cutienda.Notificaciones.Controllers;
import com.cutienda.Notificaciones.Modelos.Notificacion;
import com.cutienda.Notificaciones.Repositories.NotificacionRepositorio;
import com.cutienda.Notificaciones.Services.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class NotificacionApiController {

    @Autowired
    private NotificacionService notificacionService; // Servicio para obtener notificaciones

    @Autowired
    private NotificacionRepositorio notificacionRepository;


    @GetMapping("/cutienda/noti")
    public List<Notificacion> obtenerNotificaciones() {
        return notificacionService.obtenerTodasLasNotificaciones();

    }

    @PutMapping("cutienda/noti/{id}/leer") // Mapea la URL completa
    public Notificacion marcarComoLeida(@PathVariable Long id) {
        return notificacionService.marcarNotificacionComoLeida(id);
    }
}