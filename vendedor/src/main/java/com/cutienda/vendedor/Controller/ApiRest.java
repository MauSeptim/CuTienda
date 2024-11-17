package com.cutienda.vendedor.Controller;

import com.cutienda.vendedor.Modelos.Comentario;
import com.cutienda.vendedor.Modelos.Usuario;
import com.cutienda.vendedor.Modelos.producto;
import com.cutienda.vendedor.Services.ComentarioService;
import com.cutienda.vendedor.Services.ProductoService;
import com.cutienda.vendedor.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cutienda")
public class ApiRest {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ComentarioService comentarioService;
    @Autowired
    private ProductoService productoService;

    @GetMapping("/vendedorapi/{idvendedor}/{id_producto}")
    public Map<String, Object> getVendedor(
            @PathVariable Long idvendedor,
            @PathVariable Long id_producto) {

        Map<String, Object> response = new HashMap<>();

        // Obtener información del vendedor
        Usuario vendedor = usuarioService.obtenerVendedor(idvendedor);
        if (vendedor == null) {
            response.put("error", "Vendedor no encontrado");
            return response;
        }

        List<Comentario> comentarios = comentarioService.obtenerComentariosPorVendedor(idvendedor);

        // Obtener el producto y verificar las coordenadas
        producto prod = productoService.obtenerProductoPorId(id_producto);
        if (prod == null) {
            response.put("error", "Producto no encontrado");
            return response;
        }

        // Calcular el promedio de las calificaciones
        double promedioCalificacion = comentarios.stream()
                .mapToInt(Comentario::getCalificacion)
                .average()
                .orElse(0.0);  // Si no hay comentarios, el promedio será 0

        // Construir la respuesta
        response.put("producto", prod);
        response.put("vendedor", vendedor);
        response.put("comentarios", comentarios);
        response.put("promedioCalificacion", promedioCalificacion);

        return response;
    }
}
