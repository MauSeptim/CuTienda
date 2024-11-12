package com.cutienda.controllers;
import com.cutienda.dto.ProductoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.cutienda.services.ProductoService;

import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:8080")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/cutienda/menu")
    public String home() {
        return "vista-cliente"; // Esto buscará en templates/vista-cliente.html
    }

    @GetMapping("/cutienda/perfil")
    public String perfil() {
        return "redirect:" + "http://localhost:8080/cutienda/perfil";
    }

    @GetMapping("/cutienda/notificaciones")
    public String notificaciones() {
        return "redirect:" + "http://localhost:8083/cutienda/notificaciones";
    }

    @GetMapping("/cutienda/comentarios/{vendedor}")
    public String Nombre(@PathVariable String vendedor){

        return "redirect:" + "http://localhost:8081/cutienda/comentarios/" + vendedor;


    }

}
