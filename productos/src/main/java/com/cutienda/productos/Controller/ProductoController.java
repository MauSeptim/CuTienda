package com.cutienda.productos.Controller;

import com.cutienda.productos.Modelos.ProductoModelo;
import com.cutienda.productos.Services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("cutienda/api/productos")  // Cambiamos la ruta base para reflejar el estilo REST
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Conseguir todos los productos
    @GetMapping
    public ResponseEntity<List<ProductoModelo>> conseguirTodos() {
        List<ProductoModelo> productos = productoService.obtenerTodosProductos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    // Conseguir un producto por su ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductoModelo> conseguirProductoPorId(@PathVariable Long id) {
        ProductoModelo producto = productoService.obtenerProductoPorId(id);
        if (producto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoModelo>> buscarProductosPorNombre(@RequestParam String nombre) {
        List<ProductoModelo> productos = productoService.buscarProductosPorNombre(nombre);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    // Registrar un nuevo producto
    @PostMapping("/registro")
    public ResponseEntity<String> registrarProducto(
            @RequestParam("nombre_producto") String nombre,
            @RequestParam("id_usuario") int id_usuario,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("tipo") String tipo,
            @RequestParam("precio") BigDecimal precio,
            @RequestParam(value = "latitud", required = false) Double latitud,
            @RequestParam(value = "longitud", required = false) Double longitud,
            @RequestParam(value = "foto", required = false) MultipartFile foto) {
        try {
            ProductoModelo producto = new ProductoModelo();
            producto.setNombreProducto(nombre);
            producto.setDescripcion(descripcion);
            producto.setTipo(tipo);
            producto.setPrecio(precio);
            producto.setId_usuario(id_usuario);

            // Guardar la foto si está presente
            if (foto != null && !foto.isEmpty()) {
                byte[] fotoBytes = foto.getBytes();
                producto.setFoto(fotoBytes);
            }

            // Guardar la latitud y longitud si están presentes
            if (latitud != null && longitud != null) {
                producto.setLatitud(latitud);
                producto.setLongitud(longitud);
            }

            productoService.guardarProducto(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Producto registrado exitosamente.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar el producto: " + e.getMessage());
        }
    }

    // Actualizar un producto existente
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarProducto(@PathVariable Long id, @RequestBody ProductoModelo producto) {
        if (productoService.obtenerProductoPorId(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
        }
        producto.setId_producto(id);
        productoService.actualizarProducto(producto);
        return ResponseEntity.ok("Producto actualizado exitosamente.");
    }

    // Eliminar un producto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id) {
        try {
            productoService.eliminarProducto(id);
            return ResponseEntity.ok("Producto eliminado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el producto: " + e.getMessage());
        }
    }
    // Servir la foto de un producto por ID
    @GetMapping("/foto/{id}")
    public ResponseEntity<byte[]> obtenerFotoProducto(@PathVariable Long id) {
        ProductoModelo producto = productoService.obtenerProductoPorId(id);
        if (producto != null && producto.getFoto() != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")  // O el tipo de imagen correcto
                    .body(producto.getFoto());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}


