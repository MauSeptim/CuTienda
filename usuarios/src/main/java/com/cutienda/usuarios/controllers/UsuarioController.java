package com.cutienda.usuarios.controllers;

import com.cutienda.usuarios.models.Usuario;
import com.cutienda.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/cutienda")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Endpoint para registrar un usuario
    @PostMapping("/registro")
    public ResponseEntity<Map<String, String>> registrarUsuario(
            @RequestParam("nombre") String nombre,
            @RequestParam("apellidos") String apellidos,
            @RequestParam("email") String correoElectronico,
            @RequestParam("telefono") String telefono,
            @RequestParam("role") String tipo,
            @RequestParam("password") String contraseña,
            @RequestParam("confirmPassword") String confirmacionContraseña,
            @RequestParam("foto") MultipartFile foto) {

        Map<String, String> response = new HashMap<>();
        try {
            if (usuarioService.existeCorreo(correoElectronico).isPresent()) {
                response.put("error", "El correo electrónico ya está en uso");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (!contraseña.equals(confirmacionContraseña)) {
                response.put("error", "Las contraseñas no coinciden");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setApellidos(apellidos);
            usuario.setCorreoElectronico(correoElectronico);
            usuario.setTelefono(telefono);
            usuario.setTipo(tipo);
            usuario.setContraseña(contraseña);
            usuario.setConfirmacionContraseña(confirmacionContraseña);

            if (foto != null && !foto.isEmpty()) {
                byte[] imagenBytes = foto.getBytes();
                usuario.setFotoUrl(imagenBytes);
            } else {
                response.put("error", "La foto es obligatoria");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
            }

            usuarioService.registrarUsuario(usuario);
            response.put("message", "Usuario registrado con éxito");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IOException e) {
            response.put("error", "Error al cargar la foto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            response.put("error", "Error al registrar usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Endpoint para iniciar sesión
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> iniciarSesion(
            @RequestParam("email") String correoElectronico,
            @RequestParam("password") String contraseña,
            @RequestParam("role") String selectedRole) {

        Map<String, Object> response = new HashMap<>();
        try {
            Usuario usuario = usuarioService.autenticar(correoElectronico, contraseña);
            if (usuario != null && selectedRole.equals(usuario.getTipo())) {
                response.put("usuario", usuario);
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "Correo o contraseña incorrectos, o rol seleccionado no coincide con el rol del usuario.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            response.put("error", "Error durante la autenticación: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Endpoint para recuperar la contraseña
    @PostMapping("/recuperar")
    public ResponseEntity<Map<String, String>> recuperarContraseña(
            @RequestParam String nombre,
            @RequestParam String apellidos,
            @RequestParam String correo,
            @RequestParam(required = false) String telefono,
            @RequestParam String newPassword) {

        Map<String, String> response = new HashMap<>();
        try {
            Usuario usuario = usuarioService.buscarUsuario(nombre, apellidos, correo, telefono);
            if (usuario != null) {
                usuarioService.actualizarContraseña(usuario.getId(), newPassword);
                response.put("message", "Contraseña cambiada exitosamente.");
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "Usuario no encontrado.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("error", "Error al recuperar la contraseña: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Verificar si el usuario existe
    @GetMapping("/verificarUsuario")
    public ResponseEntity<Map<String, Object>> verificarUsuario(
            @RequestParam String nombre,
            @RequestParam String apellidos,
            @RequestParam String correo,
            @RequestParam(required = false) String telefono) {

        Map<String, Object> response = new HashMap<>();
        Usuario usuario = usuarioService.buscarUsuario(nombre, apellidos, correo, telefono);

        if (usuario != null) {
            response.put("usuarioEncontrado", true);
        } else {
            response.put("usuarioEncontrado", false);
        }
        return ResponseEntity.ok(response);
    }

    // Endpoint para actualizar perfil
    @PostMapping("/perfil/actualizar")
    public ResponseEntity<Map<String, String>> actualizarPerfil(
            @RequestParam("nombre") String nombre,
            @RequestParam("apellidos") String apellidos,
            @RequestParam("correoElectronico") String correoElectronico,
            @RequestParam("telefono") String telefono,
            @RequestParam(required = false) String nuevaContraseña,
            @RequestParam(required = false) String confirmacionContraseña,
            @RequestParam(required = false) MultipartFile foto,
            HttpSession session) {

        Map<String, String> response = new HashMap<>();
        try {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            if (usuario == null || usuario.getId() == null) {
                response.put("error", "Debes iniciar sesión para actualizar tu perfil.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            usuario.setNombre(nombre);
            usuario.setApellidos(apellidos);
            usuario.setCorreoElectronico(correoElectronico);
            usuario.setTelefono(telefono);

            if (nuevaContraseña != null && !nuevaContraseña.isEmpty()) {
                if (!nuevaContraseña.equals(confirmacionContraseña)) {
                    response.put("error", "Las contraseñas no coinciden.");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
                usuario.setContraseña(nuevaContraseña);
            }

            if (foto != null && !foto.isEmpty()) {
                byte[] imagenBytes = foto.getBytes();
                usuario.setFotoUrl(imagenBytes);
            }

            usuarioService.actualizarUsuario(usuario);
            session.setAttribute("usuario", usuario);

            response.put("message", "Perfil actualizado con éxito.");
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            response.put("error", "Error al cargar la foto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            response.put("error", "Error al actualizar perfil: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
