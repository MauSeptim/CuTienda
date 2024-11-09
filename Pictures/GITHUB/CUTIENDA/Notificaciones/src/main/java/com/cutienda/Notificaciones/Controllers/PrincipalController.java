package com.cutienda.Notificaciones.Controllers;

import com.cutienda.Notificaciones.Modelos.Notificacion;
import com.cutienda.Notificaciones.Services.NotificacionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cutienda")
public class PrincipalController {

    @GetMapping("/notificaciones/{id}")
    public String mostrarNotificacion() {
        return "Notificaciones";
    }

    @GetMapping("/productos")
    public String mostrarProductos() {
        return "redirect:" + "http://localhost:8076/cutienda/menu";
    }

    @Autowired
    private NotificacionService notificacionService;

    // Obtener una notificación por ID
    @GetMapping("/{id}")
    public Optional<Notificacion> obtenerNotificacionPorId(@PathVariable Long id) {
        return notificacionService.obtenerNotificacionPorId(id);
    }

    // Crear una nueva notificación
    @PostMapping("/crear")
    public Notificacion crearNotificacion(@RequestBody Notificacion notificacion) {
        return notificacionService.crearNotificacion(
                notificacion.getId_usuario(),
                notificacion.getMensaje(),
                notificacion.getCategoria(),
                LocalDateTime.now(),
                notificacion.isLeido()
        );
    }
    @GetMapping("/cutienda/noti")
    public List<Notificacion> obtenerNotificaciones() {
        return notificacionService.obtenerTodasLasNotificaciones();

    }

    // Eliminar una notificación por ID
    @DeleteMapping("/{id}")
    public void eliminarNotificacion(@PathVariable Long id) {
        notificacionService.eliminarNotificacion(id);
    }



}
