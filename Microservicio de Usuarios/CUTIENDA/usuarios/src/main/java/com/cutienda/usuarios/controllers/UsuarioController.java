package com.cutienda.usuarios.controllers;

import com.cutienda.usuarios.models.Usuario;
import com.cutienda.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cutienda")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<Map<String, Object>> registrarUsuario(
            @RequestParam("nombre") String nombre,
            @RequestParam("apellidos") String apellidos,
            @RequestParam("email") String correoElectronico,
            @RequestParam("telefono") String telefono,
            @RequestParam("role") String tipo,
            @RequestParam("password") String contraseña,
            @RequestParam("confirmPassword") String confirmacionContraseña,
            @RequestParam("foto") MultipartFile foto) {

        Map<String, Object> response = new HashMap<>();

        try {
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setApellidos(apellidos);
            usuario.setCorreoElectronico(correoElectronico);
            usuario.setTelefono(telefono);
            usuario.setTipo(tipo);
            usuario.setContraseña(contraseña);
            usuario.setConfirmacionContraseña(confirmacionContraseña);

            // Validar las contraseñas
            if (!contraseña.equals(confirmacionContraseña)) {
                return ResponseEntity.badRequest().body(Map.of("error", "Las contraseñas no coinciden"));
            }

            // Almacenar la foto directamente como bytes en el objeto Usuario
            if (foto != null && !foto.isEmpty()) {
                byte[] imagenBytes = foto.getBytes();
                usuario.setFotoUrl(imagenBytes);
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "La foto es obligatoria"));
            }

            // Registrar el usuario en la base de datos
            usuarioService.registrarUsuario(usuario);
            response.put("mensaje", "Usuario registrado con éxito");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al cargar la foto: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al registrar usuario: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> iniciarSesion(
            @RequestParam("correoElectronico") String correoElectronico,
            @RequestParam("contraseña") String contraseña,
            @RequestParam("selectedRole") String selectedRole) {

        Map<String, Object> response = new HashMap<>();
        Usuario usuario = usuarioService.autenticar(correoElectronico, contraseña);

        if (usuario != null) {
            if (selectedRole.equals(usuario.getTipo())) {
                response.put("usuario", usuario);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "Rol seleccionado no coincide con el rol del usuario."));
            }
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Correo o contraseña incorrectos."));
        }
    }

    @PostMapping("/perfil/eliminar")
    public ResponseEntity<Map<String, Object>> eliminarCuenta(@RequestParam("usuarioId") String usuarioId) {
        Map<String, Object> response = new HashMap<>();
        try {
            usuarioService.eliminar(usuarioId);
            response.put("mensaje", "Cuenta eliminada con éxito.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al eliminar cuenta: " + e.getMessage()));
        }
    }

    @PostMapping("/recuperar")
    public ResponseEntity<Map<String, Object>> recuperarContraseña(
            @RequestParam String nombre,
            @RequestParam String apellidos,
            @RequestParam String correo,
            @RequestParam(required = false) String telefono,
            @RequestParam String newPassword) {

        Map<String, Object> response = new HashMap<>();
        Usuario usuario = usuarioService.buscarUsuario(nombre, apellidos, correo, telefono);

        if (usuario != null) {
            usuarioService.actualizarContraseña(usuario.getId(), newPassword);
            response.put("mensaje", "Contraseña cambiada exitosamente.");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Usuario no encontrado."));
        }
    }

    @GetMapping("/verificarUsuario")
    public ResponseEntity<Map<String, Object>> verificarUsuario(
            @RequestParam String nombre,
            @RequestParam String apellidos,
            @RequestParam String correo,
            @RequestParam(required = false) String telefono) {

        Map<String, Object> response = new HashMap<>();
        Usuario usuario = usuarioService.buscarUsuario(nombre, apellidos, correo, telefono);

        response.put("usuarioEncontrado", usuario != null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/perfil/actualizar")
    public ResponseEntity<Map<String, Object>> actualizarPerfil(
            @RequestParam("usuarioId") Long usuarioId,
            @RequestParam("nombre") String nombre,
            @RequestParam("apellidos") String apellidos,
            @RequestParam("correoElectronico") String correoElectronico,
            @RequestParam("telefono") String telefono,
            @RequestParam(required = false) String nuevaContraseña,
            @RequestParam(required = false) MultipartFile foto) {

        Map<String, Object> response = new HashMap<>();
        try {
            Usuario usuario = usuarioService.buscarUsuario(nombre, apellidos, correoElectronico, telefono);
            if (usuario == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Usuario no encontrado."));
            }

            // Actualiza los datos del usuario
            usuario.setNombre(nombre);
            usuario.setApellidos(apellidos);
            usuario.setCorreoElectronico(correoElectronico);
            usuario.setTelefono(telefono);

            // Si se proporciona una nueva contraseña, validarla y actualizar
            if (nuevaContraseña != null && !nuevaContraseña.isEmpty()) {
                usuario.setContraseña(nuevaContraseña);
            }

            // Manejar la imagen de perfil
            if (foto != null && !foto.isEmpty()) {
                byte[] imagenBytes = foto.getBytes();
                usuario.setFotoUrl(imagenBytes);
            }

            // Actualizar el usuario en la base de datos
            usuarioService.actualizarUsuario(usuario);
            response.put("mensaje", "Perfil actualizado con éxito.");
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al cargar la foto: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al actualizar perfil: " + e.getMessage()));
        }
    }
}
