package com.cutienda.vendedor.Controller;


import com.cutienda.vendedor.Modelos.Comentario;
import com.cutienda.vendedor.Modelos.Usuario;
import com.cutienda.vendedor.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cutienda")
public class VendedorController {

    @GetMapping("/vendedor/{idvendedor}/{idproducto}")
    public String mostrarNotificacion() {

        return "vendedor";
    }

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/vendedor/{id}")
    public String verVendedor(@PathVariable Long id, Model model) {
        Usuario vendedor = usuarioService.obtenerVendedor(id);
        List<Comentario> comentario = usuarioService.obtenerComentariosPorVendedor(id);

        model.addAttribute("vendedor", vendedor);
        model.addAttribute("comentario", comentario);

        return "vendedor";  // Nombre del archivo HTML
    }


}
