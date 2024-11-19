package com.cutienda.usuarios.controllers;

import com.cutienda.usuarios.models.Usuario;
import com.cutienda.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
            if (usuarioService.encontrarPorCorreo(correoElectronico).isPresent()) {
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

            Usuario resp =  usuarioService.registrarUsuario(usuario);
            response.put("message", resp);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IOException e) {
            response.put("error", "Error al cargar la foto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            response.put("error", "Error al registrar usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PostMapping("/correo")
    public ResponseEntity<Object> conseguirUsuarioPorEmail(@RequestParam("email") String email) {
        Usuario user = usuarioService.encontrarPorCorreo(email).orElse(null);
        Map<String, Object> response;

        if (user == null) {
            response = new HashMap<>();
            response.put("message", "No existe usuario con ese correo");
            response.put("error", HttpStatus.NOT_FOUND);
            return ResponseEntity.ok(response);
        }
        else {
            return ResponseEntity.ok(user);
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
            }
            else if (selectedRole.isEmpty()) {
                response.put("error", "Elige admin o cliente");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            else {
                response.put("error", "Correo o contraseña incorrectos, o rol seleccionado no coincide con el rol del usuario.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            response.put("error", "Error durante la autenticación: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PostMapping("/id")
    public ResponseEntity<Object> conseguirIdPorCorreo(@RequestParam("email") String email) {
        Usuario user = usuarioService.encontrarPorCorreo(email).orElse(null);
        Map<String, Object> response = new HashMap<>();

        if (user == null) {
            response.put("message", "No existe usuario con ese correo");
            response.put("error", HttpStatus.NOT_FOUND);
        }
        else {
            response.put("id", user.getId());
        }
        return ResponseEntity.ok(response);
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
    @PostMapping("/perfil/actualizar/{id}")
    public ResponseEntity<Object> actualizarPerfil(
            @PathVariable Long id,
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "apellidos", required = false) String apellidos,
            @RequestParam(value = "correoElectronico", required = false) String correoElectronico,
            @RequestParam(value = "telefono", required = false) String telefono,
            @RequestParam(required = false, value = "newPassword") String nuevaContraseña,
            @RequestParam(required = false, value = "passwordConfirm") String confirmacionContraseña,
            @RequestParam(required = false, value = "foto") MultipartFile foto) {

        Map<String, String> response = new HashMap<>();

        // Buscar el usuario en la base de datos
        Optional<Usuario> optionalUsuario = usuarioService.buscarPorId(id);
        if (!optionalUsuario.isPresent()) {
            response.put("error", "Usuario no encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Usuario usuario = optionalUsuario.get();

        try {
            // Actualizar datos del usuario si están presentes
            if (nombre != null && !nombre.isEmpty()) {
                usuario.setNombre(nombre);
            }
            if (apellidos != null && !apellidos.isEmpty()) {
                usuario.setApellidos(apellidos);
            }
            if (correoElectronico != null && !correoElectronico.isEmpty()) {
                usuario.setCorreoElectronico(correoElectronico);
            }
            if (telefono != null && !telefono.isEmpty()) {
                usuario.setTelefono(telefono);
            }

            // Validar y actualizar contraseña si es necesario
            if (nuevaContraseña != null && !nuevaContraseña.isEmpty()) {
                if (!nuevaContraseña.equals(confirmacionContraseña)) {
                    response.put("error", "Las contraseñas no coinciden.");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
                usuario.setContraseña(nuevaContraseña);
            }

            // Procesar y guardar la foto si está presente
            if (foto != null && !foto.isEmpty()) {
                byte[] imagenBytes = foto.getBytes();
                usuario.setFotoUrl(imagenBytes);
            }

            // Guardar los cambios en la base de datos
            usuarioService.actualizarUsuario(usuario);

            System.out.println(nuevaContraseña);
            System.out.println(confirmacionContraseña);
            System.out.println(usuario.getId());
            System.out.println(usuario.getContraseña());
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

    @GetMapping("/{id}")
    public ResponseEntity<Object> ObtenerUsuarioPorId(@PathVariable Long id) {
        Usuario user = usuarioService.buscarPorId(id).orElse(null);

        if (user == null) {
            Map response = Map.of("message", "No existe este usuario");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok(user);
    }
    @GetMapping("/{id}/foto")
    public ResponseEntity<byte[]> obtenerFoto(@PathVariable Long id) {
        Usuario user = usuarioService.buscarPorId(id).orElse(null);
        if (user != null && user.getFotoUrl() != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")  // O el tipo de imagen correcto
                    .body(user.getFotoUrl());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
