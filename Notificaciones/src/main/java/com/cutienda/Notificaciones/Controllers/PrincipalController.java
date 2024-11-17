package com.cutienda.Notificaciones.Controllers;

import com.cutienda.Notificaciones.Modelos.Notificacion;
import com.cutienda.Notificaciones.Repositories.NotificacionRepositorio;
import com.cutienda.Notificaciones.Services.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/notificaciones")
public class PrincipalController {

    @Autowired
    private NotificacionService notificacionService;
    @Autowired
    private NotificacionRepositorio notificacionRepository;

    // Obtener una notificación por ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Notificacion>> obtenerNotificacionPorId(@PathVariable Long id) {
        Optional<Notificacion> notificacion = notificacionService.obtenerNotificacionPorId(id);
        return notificacion.isPresent() ?
                new ResponseEntity<>(notificacion, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Crear una nueva notificación
    @PostMapping
    public ResponseEntity<Notificacion> crearNotificacion(@RequestBody Notificacion notificacion) {
        Notificacion nuevaNotificacion = notificacionService.crearNotificacion(
                notificacion.getIdUsuario(),
                notificacion.getMensaje(),
                notificacion.getCategoria(),
                LocalDateTime.now(),
                notificacion.isLeido()
        );
        return new ResponseEntity<>(nuevaNotificacion, HttpStatus.CREATED);
    }

    // Eliminar una notificación por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNotificacion(@PathVariable Long id) {
        notificacionService.eliminarNotificacion(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/user/{id}")
    public List<Notificacion> notificacionesExceptoElUser(@PathVariable int id) {
        return notificacionService.buscarNotificacionesExceptoEsteIdUser(id);
    }

    // Redirección para productos
    @GetMapping("/productos")
    public ResponseEntity<Void> redirigirProductos() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "http://localhost:8076/cutienda/menu")
                .build();
    }
    @GetMapping
    public ResponseEntity<List<Notificacion>> obtenerNotificaciones() {
        List<Notificacion> notificaciones = notificacionService.obtenerTodasLasNotificaciones();
        return new ResponseEntity<>(notificaciones, HttpStatus.OK);
    }

    // Marcar una notificación como leída por su ID
    @PutMapping("/{id}/leer")
    public ResponseEntity<Notificacion> marcarComoLeida(@PathVariable Long id) {
        Optional<Notificacion> notificacionOpt = notificacionRepository.findById(id);
        if (notificacionOpt.isPresent()) {
            Notificacion notificacionLeida = notificacionService.marcarNotificacionComoLeida(id);
            return new ResponseEntity<>(notificacionLeida, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

