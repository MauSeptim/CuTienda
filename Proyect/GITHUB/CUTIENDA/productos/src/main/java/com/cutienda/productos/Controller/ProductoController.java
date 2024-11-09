package com.cutienda.productos.Controller;

import com.cutienda.productos.Modelos.ProductoModelo;
import com.cutienda.productos.Services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/cutienda")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Ruta para mostrar el formulario de registro
    @GetMapping("/registroprod/{iduser}")
    public String mostrarRegistro() {
        return "registro";  // Asegúrate de tener un archivo HTML llamado 'registro.html'
    }

    @GetMapping("/registroprod/{iduser}/{id_producto}")
    public String mostrarProducto(@PathVariable Long id_producto, Model model) {
        ProductoModelo producto = productoService.obtenerProductoPorId(id_producto);
        if (producto == null) {
            model.addAttribute("error", "Producto no encontrado");
            return "error"; // Redirige a una página de error si no se encuentra el producto
        }
        model.addAttribute("producto", producto);
        return "vista"; // Nombre de la plantilla Thymeleaf
    }

    @PostMapping("/productos/registro")
    public String registrarProducto(@RequestParam("nombre_producto") String nombre,
                                    @RequestParam("id_usuario") int id_usuario,
                                    @RequestParam("descripcion") String descripcion,
                                    @RequestParam("tipo") String tipo,
                                    @RequestParam("precio") BigDecimal precio,
                                    @RequestParam(value = "latitud", required = false) Double latitud,
                                    @RequestParam(value = "longitud", required = false) Double longitud,
                                    @RequestParam(value = "foto", required = false) MultipartFile foto,
                                    Model model) {
        try {
            ProductoModelo producto = new ProductoModelo();
            producto.setNombre_producto(nombre);
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
            model.addAttribute("mensaje", "Producto registrado exitosamente");
            return "redirect:/cutienda/inicio";
        } catch (IOException e) {
            model.addAttribute("error", "Error al registrar el producto: " + e.getMessage());
            return "producto/registro";
        }
    }

    @PostMapping("/actualizarProducto/{id}")
    public String actualizarProducto(@PathVariable Long id, @ModelAttribute ProductoModelo producto) {
        // Asegúrate de que el ID no sea nulo
        if (id == null) {
            throw new IllegalArgumentException("El ID del producto no puede ser nulo");
        }
        // Aquí asignamos el ID del producto
        producto.setId_producto(id);
        productoService.actualizarProducto(producto);
        return "redirect:/cutienda/registroprod/" + producto.getId_usuario() + "/" + producto.getId_producto();
    }

    // Método para eliminar un producto
    @DeleteMapping("/eliminarProducto/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id) {
        try {
            productoService.eliminarProducto(id);  // Elimina el producto
            return ResponseEntity.ok("Producto eliminado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el producto: " + e.getMessage());
        }
    }





}

