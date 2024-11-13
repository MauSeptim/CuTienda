package com.cutienda.apis;

import com.cutienda.dto.ProductoDTO;
import com.cutienda.models.Producto;
import com.cutienda.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/cutienda")
public class ProductoApi {
    @Autowired
    private ProductoService productoService;

    @GetMapping("")
    public String bienvenido() {
        return "<h1>Bienvenidos</h1><br/><p>a la api <strong>Productos</strong></p>";
    }

    @GetMapping("/producto/{id}/id")
    public ResponseEntity<ProductoDTO> obtenerProducto(@PathVariable Long id) {
        Producto producto = productoService.conseguirPorId(id);
        if (producto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Crear DTO para la respuesta
        ProductoDTO productoDTO = new ProductoDTO(
                producto.getIdProducto(),
                producto.getNombreProducto(),
                producto.getIdUsuario(),
                producto.getDescripcion(),
                "http://localhost:8076/cutienda/producto/" + producto.getIdProducto() + "/foto",  // URL para la imagen
                producto.getPrecio(),
                producto.getTipo()
        );

        return ResponseEntity.ok(productoDTO);
    }

    @GetMapping("/producto/todos")
    public List<ProductoDTO> todosLosProductos() {
        List<Producto> productos = productoService.conseguirTodos();
        return productos.stream().map(producto -> {
            return new ProductoDTO(
                    producto.getIdProducto(),
                    producto.getNombreProducto(),
                    producto.getIdUsuario(),
                    producto.getDescripcion(),
                    "http://localhost:8076/cutienda/producto/" + producto.getIdProducto() + "/foto",  // URL para la imagen
                    producto.getPrecio(),
                    producto.getTipo()
            );
        }).toList();
    }

    @GetMapping("/producto/{nombreProducto}/nombre")
    public List<ProductoDTO> obtenerPorNombre(@PathVariable String nombreProducto) {
        return productoService.obtenerPorNombreCoincidente(nombreProducto)
                .stream()
                .map(p -> {
                    return new ProductoDTO(
                            p.getIdProducto(),
                            p.getNombreProducto(),
                            p.getIdUsuario(),
                            p.getDescripcion(),
                            "http://localhost:8076/cutienda/producto/" + p.getIdProducto() + "/foto",  // URL para la imagen
                            p.getPrecio(),
                            p.getTipo()
                    );
                }).toList();
    }


    @PostMapping("/producto/guardar")
    public ResponseEntity<Producto> guardarProducto(
            @RequestParam("nombreProducto") String nombre,
            @RequestParam("idUsuario") String idUsuario,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") double precio,
            @RequestParam("tipo") String tipo,
            @RequestParam("foto") MultipartFile foto) {

        try {
            // Obtener los bytes y el tipo de contenido de la imagen
            byte[] imagenBytes = foto.getBytes();
            String contentType = foto.getContentType(); // Obtener el tipo de contenido

            // Crear un nuevo producto y establecer la imagen
            Producto producto = Producto.builder()
                    .descripcion(descripcion)
                    .nombre(nombre)
                    .tipo(contentType)
                    .idUsuario(idUsuario)
                    .precio(precio)
                    .foto(imagenBytes)
                    .build();

            productoService.guardar(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(producto);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/producto/{idProducto}/foto", produces = MediaType.ALL_VALUE)
    public ResponseEntity<byte[]> obtenerFoto(@PathVariable Long idProducto) {
        Producto producto = productoService.conseguirPorId(idProducto);
        if (producto == null || producto.getFoto() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Establecer un tipo MIME predeterminado si el tipo es inválido
        String tipo = producto.getTipo();
        if (tipo == null || !tipo.contains("/")) {
            tipo = "image/jpeg";  // Asignar un tipo MIME predeterminado
        }

        // Devuelve la imagen como respuesta
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(tipo)) // O el tipo adecuado según el archivo
                .body(producto.getFoto());
    }

}
