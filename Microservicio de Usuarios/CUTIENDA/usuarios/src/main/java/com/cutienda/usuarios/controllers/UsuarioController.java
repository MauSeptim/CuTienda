package com.cutienda.usuarios.controllers;

import com.cutienda.usuarios.models.Usuario;
import com.cutienda.usuarios.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setApellidos(apellidos);
            usuario.setCorreoElectronico(correoElectronico);
            usuario.setTelefono(telefono);
            usuario.setTipo(tipo);
            usuario.setContraseña(contraseña);
            usuario.setConfirmacionContraseña(confirmacionContraseña);

            usuarioService.registrarUsuario(usuario, foto);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario registrado con éxito");

            return "redirect:/cutienda/registro"; // Redirige a la página de éxito
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar usuario: " + e.getMessage());
            return "redirect:/cutienda/registro"; // Redirige de vuelta al formulario en caso de error
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

    @GetMapping("/perfil")
    public String mostrarPerfil(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        // Recuperar el usuario desde la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null) {
            // Pasar el usuario completo al modelo para mostrarlos en el perfil
            model.addAttribute("usuario", usuario);
            return "perfilUsuario";
        } else {
            // Agregar un mensaje de error y redirigir a la página de inicio de sesión si no hay usuario en la sesión
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
            HttpSession session) {

        Usuario usuario = usuarioService.autenticar(correoElectronico, contraseña);

        if (usuario != null) {
            if (selectedRole.equals(usuario.getTipo())) {
                // Guardar el usuario en la sesión
                session.setAttribute("usuario", usuario);

                // Redirigir basado en el rol del usuario
                if ("admin".equals(usuario.getTipo())) {
                    return "redirect:/cutienda/perfil"; // Redirige a la página de perfil para administradores
                } else if ("client".equals(usuario.getTipo())) {
                    return "redirect:/cutienda/perfil"; // Redirige a la página de perfil para clientes
                }
            } else {
                // Rol seleccionado no coincide con el rol del usuario
                redirectAttributes.addFlashAttribute("error", "Rol seleccionado no coincide con el rol del usuario.");
                return "redirect:/cutienda/login";
            }
        } else {
            // Autenticación fallida
            redirectAttributes.addFlashAttribute("error", "Correo o contraseña incorrectos.");
            return "redirect:/cutienda/login";
        }
        return "redirect:/cutienda/login";
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
        return "recovery";
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

}
