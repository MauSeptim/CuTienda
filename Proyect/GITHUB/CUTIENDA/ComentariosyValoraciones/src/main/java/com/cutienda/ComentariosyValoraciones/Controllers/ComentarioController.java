package com.cutienda.ComentariosyValoraciones.Controllers;


import com.cutienda.ComentariosyValoraciones.Modelos.ComentarioModel;
import com.cutienda.ComentariosyValoraciones.Modelos.Usuarios;
import com.cutienda.ComentariosyValoraciones.Repository.UsuarioRepository;
import com.cutienda.ComentariosyValoraciones.Services.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;


@Controller
@RequestMapping("/cutienda")
public class ComentarioController {
    @Autowired
    private ComentarioService comentarioService;
    @Autowired
    private UsuarioRepository usuarioRepository; // Inyección del repositorio

    @Autowired
    private UsuarioRepository usuarioRepositorio;


    @GetMapping("/comentarios/{idusuario}/{idvendedor}")
    public String mostrarInicio(@PathVariable Long idusuario, @PathVariable Long idvendedor, Model model) {
        Usuarios usuario = usuarioRepository.findById(idvendedor).orElse(null);
        System.out.println("Foto Base64: " + usuario.getFotoUrlBase64()); // Depurar valor de fotoUrlBase64
        if (usuario == null) {
            // Registro en los logs o manejo de error
            System.err.println("Usuario no encontrado: " + idusuario);
            return "error"; // O redirigir a una página de error
        }

        model.addAttribute("usuario", usuario);
        return "clienteComentarios";
    }



    @GetMapping("/menu")
    public String menu() {
        return "redirect:" + "http://localhost:8076/cutienda/menu";
    }

    @PostMapping("/comentarios/guardar")
    public String registrarComentario(
            @RequestParam("vendedor") Long vendedor,
            @RequestParam("usuario") Long usuario,
            @RequestParam("contenido") String contenido,
            @RequestParam("calificacion") int calificacion,
            RedirectAttributes redirectAttributes) {

        try {
            // Crear un nuevo objeto ComentarioModel
            ComentarioModel comentario = new ComentarioModel();
            comentario.setVendedor(vendedor);
            comentario.setUsuario(usuario);
            comentario.setContenido(contenido);
            comentario.setCalificacion(calificacion);

            // Obtener la fecha actual y establecerla en el comentario
            String fechaCreacion = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            comentario.setFechaCreacion(fechaCreacion);



            // Guardar el comentario en la base de datos
            comentarioService.registrarComentario(comentario);
            redirectAttributes.addFlashAttribute("mensaje", "Comentario registrado con éxito");

            return "redirect:/cutienda/menu"; // Redirige a la página de éxito
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar comentario: " + e.getMessage());
            e.printStackTrace(); // Registrar la excepción
        }

        return "redirect:/cutienda/menu"; // Redirige de vuelta al formulario en caso de error
    }






}
