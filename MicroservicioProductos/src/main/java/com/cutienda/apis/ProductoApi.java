package com.cutienda.apis;

import com.cutienda.models.Producto;
import com.cutienda.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cutienda")
public class ProductoApi {
    @Autowired
    ProductoService productoService;

    @GetMapping("")
    public String bienvenido() {
        String titulo = "<h1>Bienvenidos</h1>";
        String saltoLinea = "<br/>";
        String cuerpo = "<p>a la api <strong>Productos</strong></p>";

        return titulo + saltoLinea + cuerpo;
    }

    @GetMapping("/producto/{id}")
    public Producto ObtenerProducto(@PathVariable String id) {
        return productoService.conseguirPorId(id);
    }
    @GetMapping("/producto/todos")
    public List<Producto> todosLosProductos() {
        return productoService.conseguirTodos();
    }
    @PostMapping("/producto/guardar")
    public ResponseEntity<Producto> GuardarProducto(@RequestBody Producto p) {
        if (p == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else {
            productoService.guardar(p);
            return ResponseEntity.status(HttpStatus.CREATED).body(p);
        }
    }
}
