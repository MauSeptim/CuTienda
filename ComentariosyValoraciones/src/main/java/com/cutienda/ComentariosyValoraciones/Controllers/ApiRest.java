package com.cutienda.ComentariosyValoraciones.Controllers;

import com.cutienda.ComentariosyValoraciones.Modelos.ComentarioModel;
import com.cutienda.ComentariosyValoraciones.Modelos.Usuarios;
import com.cutienda.ComentariosyValoraciones.Repository.UsuarioRepository;
import com.cutienda.ComentariosyValoraciones.Services.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("cutienda2")
public class ApiRest {

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Obtener detalles del usuario con comentarios.
     */
    @GetMapping("/comentarios/{idusuario}/{idvendedor}")
    public ResponseEntity<?> mostrarUsuarioConComentarios(
            @PathVariable Long idusuario,
            @PathVariable Long idvendedor) {
        Optional<Usuarios> usuarioOpt = usuarioRepository.findById(idvendedor);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(404)
                    .body("Usuario no encontrado para el vendedor con ID: " + idvendedor);
        }
        Usuarios usuario = usuarioOpt.get();

        // Retornar los datos del usuario
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/comentarios/{vendedorid}")
    public List<ComentarioModel> comentariosPorVendedor(@PathVariable Long vendedorid) {
        return comentarioService.comentariosPorVendedor(vendedorid);
    }

    /**
     * Guardar un nuevo comentario.
     */
    @PostMapping("/comentarios/guardar")
    public ResponseEntity<?> registrarComentario(@RequestBody ComentarioModel comentarioRequest) {
        try {
            // Crear un nuevo objeto ComentarioModel
            ComentarioModel comentario = new ComentarioModel();
            comentario.setVendedor(comentarioRequest.getVendedor());
            comentario.setUsuario(comentarioRequest.getUsuario());
            comentario.setContenido(comentarioRequest.getContenido());
            comentario.setCalificacion(comentarioRequest.getCalificacion());

            // Obtener la fecha actual y establecerla en el comentario
            String fechaCreacion = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            comentario.setFechaCreacion(fechaCreacion);

            // Guardar el comentario en la base de datos
            comentarioService.registrarComentario(comentario);

            // Retornar la respuesta exitosa
            return ResponseEntity.ok("Comentario registrado con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Error al registrar comentario: " + e.getMessage());
        }
    }

    /**
     * Redirigir al menú.
     */
    @GetMapping("/menu")
    public ResponseEntity<?> menu() {
        return ResponseEntity.ok("Redirigir a: http://localhost:8076/cutienda/menu");
    }
}


