package com.cutienda.vendedor.Controller;


import com.cutienda.vendedor.Modelos.Comentario;
import com.cutienda.vendedor.Modelos.Usuario;
import com.cutienda.vendedor.Modelos.producto;
import com.cutienda.vendedor.Services.ComentarioService;
import com.cutienda.vendedor.Services.ProductoService;
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



    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ComentarioService comentarioService;
    @Autowired
    private ProductoService productoService;

    @GetMapping("/vendedor/{idvendedor}/{id_producto}")
    public String getVendedor(@PathVariable Long idvendedor, @PathVariable Long id_producto, Model model) {
        // Obtener información del vendedor
        System.out.println("ID Producto recibido: " + id_producto);
        Usuario vendedor = usuarioService.obtenerVendedor(idvendedor);
        List<Comentario> comentarios = comentarioService.obtenerComentariosPorVendedor(idvendedor);

        // Obtener el producto y verificar las coordenadas
        producto prod = productoService.obtenerProductoPorId(id_producto);
        if (prod != null) {
            System.out.println("Coordenadas del producto: Latitud: " + prod.getLatitud() + ", Longitud: " + prod.getLongitud());
        } else {
            System.out.println("Producto no encontrado");
        }

        // Calcular el promedio de las calificaciones
        double promedioCalificacion = comentarios.stream()
                .mapToInt(Comentario::getCalificacion)
                .average()
                .orElse(0.0);  // Si no hay comentarios, el promedio será 0

        // Pasar los datos al modelo
        model.addAttribute("producto", prod);
        model.addAttribute("vendedor", vendedor);
        model.addAttribute("comentarios", comentarios);
        model.addAttribute("promedioCalificacion", promedioCalificacion);

        return "vendedor";  // Asegúrate de que la vista sea la correcta
    }








}
