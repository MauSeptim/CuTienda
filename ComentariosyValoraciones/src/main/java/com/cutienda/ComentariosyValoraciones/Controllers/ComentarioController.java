package com.cutienda.ComentariosyValoraciones.Controllers;


import com.cutienda.ComentariosyValoraciones.Services.ComentarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/cutienda")
public class ComentarioController {
    @Autowired
    private ComentarioService comentarioService;


    @GetMapping("/comentarios")
    public String mostrarInicio() {
        System.out.println("Método mostrarInicio llamado");
        return "clienteComentarios";
    }

}
