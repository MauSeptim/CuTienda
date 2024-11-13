package com.cutienda.usuarios.controllers;

import com.cutienda.usuarios.models.Usuario;
import com.cutienda.usuarios.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.IOException;
import java.security.Principal;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/cutienda")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "FormRegistro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(
            @RequestParam("nombre") String nombre,
            @RequestParam("apellidos") String apellidos,
            @RequestParam("correoElectronico") String correoElectronico,
            @RequestParam("telefono") String telefono,
            @RequestParam("tipo") String tipo,
            @RequestParam("contraseña") String contraseña,
            @RequestParam("confirm-password") String confirmacionContraseña,
            @RequestParam("foto") MultipartFile foto,
            RedirectAttributes redirectAttributes) {

        try {
            // Verificar si el correo electrónico ya está en uso
            if (usuarioService.existeCorreo(correoElectronico).isPresent()) {
                throw new IllegalArgumentException("El correo electrónico ya está en uso");
            }

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
                throw new IllegalArgumentException("Las contraseñas no coinciden");
            }

            // Almacenar la foto directamente como bytes en el objeto Usuario
            if (foto != null && !foto.isEmpty()) {
                byte[] imagenBytes = foto.getBytes(); // Obtener los bytes de la foto
                usuario.setFotoUrl(imagenBytes); // Establecer los bytes en el objeto Usuario
            } else {
                throw new IllegalArgumentException("La foto es obligatoria");
            }

            // Registrar el usuario en la base de datos
            usuarioService.registrarUsuario(usuario); // Guardar el usuario en la base de datos
            redirectAttributes.addFlashAttribute("mensaje", "Usuario registrado con éxito");

            return "redirect:/cutienda/registro"; // Redirige a la página de éxito
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Error al cargar la foto: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar usuario: " + e.getMessage());
        }

        return "redirect:/cutienda/registro"; // Redirige de vuelta al formulario en caso de error
    }






    @GetMapping("/registroExitoso")
    public String registroExitoso() {
        return "registroExitoso";
    }

    @GetMapping("/login")
    public String mostrarInicio() {
        return "login";
    }

    @GetMapping("/perfil")
    public String mostrarPerfil(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null) {
            System.out.println("Foto Base64: " + usuario.getFotoUrlBase64()); // Depurar valor de fotoUrlBase64
            model.addAttribute("usuario", usuario);
            return "perfilUsuario";
        } else {
            redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión para acceder al perfil.");
            return "redirect:/cutienda/login";
        }
    }





    @PostMapping("/login")
    public String iniciarSesion(
            @RequestParam("correoElectronico") String correoElectronico,
            @RequestParam("contraseña") String contraseña,
            @RequestParam("selectedRole") String selectedRole,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        try {
            Usuario usuario = usuarioService.autenticar(correoElectronico, contraseña);
            if (usuario != null && selectedRole.equals(usuario.getTipo())) {
                // Guardar el usuario en la sesión
                session.setAttribute("usuario", usuario);

                // Redirigir basado en el rol del usuario
                String url = "";
                if ("admin".equals(usuario.getTipo())) {
                    url = "http://localhost:8080/cutienda/perfil";
                } else if ("client".equals(usuario.getTipo())) {
                    url = "http://localhost:8080/cutienda/perfil";
                }
                return "redirect:" + url;
            } else {
                // Autenticación fallida o rol seleccionado no coincide
                redirectAttributes.addFlashAttribute("error", "Correo o contraseña incorrectos, o rol seleccionado no coincide con el rol del usuario.");
                return "redirect:/cutienda/login";
            }
        } catch (Exception e) {
            // Manejo de errores
            redirectAttributes.addFlashAttribute("error", "Error durante la autenticación: " + e.getMessage());
            return "redirect:/cutienda/login";
        }
    }




    @PostMapping("/perfil/eliminar")
    public String eliminarCuenta(HttpSession session, RedirectAttributes redirectAttributes) {
        // Obtener el usuario desde la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null) {
            // Lógica para eliminar la cuenta del usuario
            usuarioService.eliminar(usuario.getId());

            // Invalidar la sesión
            session.invalidate();

            // Agregar un mensaje de éxito y redirigir a la página de inicio
            redirectAttributes.addFlashAttribute("message", "Cuenta eliminada con éxito.");
            return "redirect:/cutienda/login";
        } else {
            // Redirigir a la página de inicio de sesión si no hay usuario en la sesión
            redirectAttributes.addFlashAttribute("error", "Debes iniciar sesión para eliminar tu cuenta.");
            return "redirect:/cutienda/login";
        }
    }

    @GetMapping("/recovery")
    public String recovery() {
        return "Recovery";
    }

    @GetMapping("/menu")
    public String menu() {
        return "redirect:" + "http://localhost:8076/cutienda/menu";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        String requestUrl = request.getRequestURL().toString();

        if (requestUrl.contains("cutienda/registro")) {
            redirectAttributes.addFlashAttribute("error", "Ocurrió un error durante el registro: " + e.getMessage());
            return "redirect:/cutienda/registro";
        } else {
            redirectAttributes.addFlashAttribute("error", "Error interno: " + e.getMessage());
            return "redirect:/cutienda/login";
        }
    }

    @PostMapping("/recuperar")
    public String recuperarContraseña(
            @RequestParam String nombre,
            @RequestParam String apellidos,
            @RequestParam String correo,
            @RequestParam(required = false) String telefono,
            @RequestParam String newPassword,
            RedirectAttributes redirectAttributes) {

        // Verificar si el usuario existe
        Usuario usuario = usuarioService.buscarUsuario(nombre, apellidos, correo, telefono);
        if (usuario != null) {
            // Si el usuario es encontrado, se puede proceder a cambiar la contraseña
            usuarioService.actualizarContraseña(usuario.getId(), newPassword); // Actualiza la contraseña
            redirectAttributes.addFlashAttribute("message", "Contraseña cambiada exitosamente.");
            return "redirect:/cutienda/login"; // Redirige al usuario a la página de login después de cambiar la contraseña
        } else {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado.");
            return "redirect:/cutienda/recovery"; // Retorna a la página de recuperación de contraseña
        }
    }

    @GetMapping("/verificarUsuario")
    @ResponseBody // Para que la respuesta sea devuelta como JSON
    public ResponseEntity<Map<String, Object>> verificarUsuario(
            @RequestParam String nombre,
            @RequestParam String apellidos,
            @RequestParam String correo,
            @RequestParam(required = false) String telefono) {

        Map<String, Object> response = new HashMap<>();
        Usuario usuario = usuarioService.buscarUsuario(nombre, apellidos, correo, telefono);

        if (usuario != null) {
            response.put("usuarioEncontrado", true);
            // Aquí puedes agregar más información si lo necesitas
        } else {
            response.put("usuarioEncontrado", false);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/perfil/actualizar")
    public String actualizarPerfil(
            @RequestParam("nombre") String nombre,
            @RequestParam("apellidos") String apellidos,
            @RequestParam("correoElectronico") String correoElectronico,
            @RequestParam("telefono") String telefono,
            @RequestParam(required = false) String nuevaContraseña,
            @RequestParam(required = false) String confirmacionContraseña,
            @RequestParam(required = false) MultipartFile foto,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        try {
            Usuario usuario = (Usuario) session.getAttribute("usuario");

            if (usuario == null || usuario.getId() == null) {
                redirectAttributes.addFlashAttribute("error", "Debes iniciar sesión para actualizar tu perfil.");
                return "redirect:/cutienda/login";
            }

            // Actualiza los datos del usuario
            usuario.setNombre(nombre);
            usuario.setApellidos(apellidos);
            usuario.setCorreoElectronico(correoElectronico);
            usuario.setTelefono(telefono);

            // Si se proporciona una nueva contraseña, validarla y actualizar
            if (nuevaContraseña != null && !nuevaContraseña.isEmpty()) {
                if (!nuevaContraseña.equals(confirmacionContraseña)) {
                    redirectAttributes.addFlashAttribute("error", "Las contraseñas no coinciden.");
                    return "redirect:/cutienda/perfil";
                }
                usuario.setContraseña(nuevaContraseña); // Asegúrate de que la lógica de actualización sea la correcta.
            }

            // Manejar la imagen de perfil
            if (foto != null && !foto.isEmpty()) {
                byte[] imagenBytes = foto.getBytes();
                usuario.setFotoUrl(imagenBytes);
            }

            // Actualizar el usuario en la base de datos
            usuarioService.actualizarUsuario(usuario);

            // Actualizar la sesión con el nuevo usuario
            session.setAttribute("usuario", usuario);
            redirectAttributes.addFlashAttribute("mensaje", "Perfil actualizado con éxito.");

            return "redirect:/cutienda/perfil";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Error al cargar la foto: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar perfil: " + e.getMessage());
        }

        return "redirect:/cutienda/perfil"; // Redirige de vuelta al perfil en caso de error
    }




}
