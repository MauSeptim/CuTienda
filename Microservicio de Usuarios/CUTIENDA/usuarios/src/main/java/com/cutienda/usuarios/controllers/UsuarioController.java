package com.cutienda.usuarios.controllers;

import com.cutienda.usuarios.models.Usuario;
import com.cutienda.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cutienda")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "FormRegistro";
    }

    @PostMapping("/registro")
    public ResponseEntity<String> registrarUsuario(
            @RequestParam("nombre") String nombre,
            @RequestParam("apellidos") String apellidos,
            @RequestParam("correoElectronico") String correoElectronico,
            @RequestParam("telefono") String telefono,
            @RequestParam("tipo") String tipo,
            @RequestParam("contraseña") String contraseña,
            @RequestParam("confirm-password") String confirmacionContraseña,
            @RequestParam("foto") MultipartFile foto) {

        try {
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setApellidos(apellidos);
            usuario.setCorreoElectronico(correoElectronico);
            usuario.setTelefono(telefono);
            usuario.setTipo(tipo);
            usuario.setContraseña(contraseña);
            usuario.setConfirmacionContraseña(confirmacionContraseña);

            usuarioService.registrarUsuario(usuario, foto);

            return new ResponseEntity<>("Usuario registrado con éxito", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/registroExitoso")
    public String registroExitoso() {
        return "registroExitoso";
    }

    @GetMapping("/login")
    public String mostrarInicio() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<String> iniciarSesion(
            @RequestParam("correoElectronico") String correoElectronico,
            @RequestParam("contraseña") String contraseña,
            @RequestParam("selectedRole") String selectedRole,
            RedirectAttributes redirectAttributes) {

        Usuario usuario = usuarioService.autenticar(correoElectronico, contraseña);

        if (usuario != null) {
            if (selectedRole.equals(usuario.getTipo())) {
                // Determinar la redirección basada en el rol
                if ("admin".equals(usuario.getTipo())) {
                    // Redirigir a la página de administrador
                    return new ResponseEntity<>("Usuario inició sesión como Administrador", HttpStatus.OK);
                } else if ("client".equals(usuario.getTipo())) {
                    // Redirigir a la página del cliente
                    return new ResponseEntity<>("Usuario inició sesión como Cliente", HttpStatus.OK);
                }
            } else {
                // Rol seleccionado no coincide con el rol del usuario
                redirectAttributes.addFlashAttribute("error", "Rol seleccionado no coincide con el rol del usuario.");
                return new ResponseEntity<>("Fallo en el inicio de sesión: rol incorrecto", HttpStatus.BAD_REQUEST);
            }
        } else {
            // Autenticación fallida
            redirectAttributes.addFlashAttribute("error", "Correo o contraseña incorrectos.");
            return new ResponseEntity<>("Fallo en el inicio de sesión: credenciales incorrectas", HttpStatus.BAD_REQUEST);
        }
        return null;
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
