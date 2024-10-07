package com.cutienda.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductoController {
    @GetMapping("/")
    public String home() {
        return "vista-cliente"; // Esto buscará en templates/vista-cliente.html
    }
}
