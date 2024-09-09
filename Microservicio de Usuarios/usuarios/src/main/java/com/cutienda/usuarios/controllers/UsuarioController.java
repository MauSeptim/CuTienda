package com.cutienda.usuarios.controllers;

import com.cutienda.usuarios.models.Usuario;
import com.cutienda.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cutienda")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    public String registrarUsuario(@RequestParam("foto") MultipartFile foto, @ModelAttribute Usuario usuario) {
        usuarioService.registrarUsuario(usuario, foto);
        return "redirect:/registroExitoso";
    }


    // Ruta para obtener un usuario por correo (JSON)
    @GetMapping("/correo/{correo}")
    public ResponseEntity<Usuario> obtenerUsuarioPorCorreo(@PathVariable String correo) {
        Usuario usuario = usuarioService.obtenerUsuarioPorCorreo(correo);
        if (usuario == null) {
            return ResponseEntity.notFound().build();  // Devuelve un 404 si no se encuentra
        }
        return ResponseEntity.ok(usuario);  // Devuelve el usuario si se encuentra
    }


    // Ruta para mostrar la vista HTML
    @GetMapping("/inicio")
    public String mostrarInicio() {
        return "login";
    }

    @GetMapping("/formulario")
    public String mostrarFormulario() {
        return "FormRegistro";  // Aquí "formulario" es el nombre del archivo HTML en /templates
    }
}
