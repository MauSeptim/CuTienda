package com.cutienda.usuarios.controllers;

import com.cutienda.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cutienda/usuarios")
public class Secundario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/{id}/foto")
    public ResponseEntity<byte[]> obtenerFoto(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    byte[] imagen = usuario.getFotoUrl();
                    if (imagen != null) {
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.IMAGE_JPEG); // O MediaType.IMAGE_PNG según corresponda
                        return new ResponseEntity<byte[]>(imagen, headers, HttpStatus.OK); // Asegúrate de especificar el tipo
                    }
                    return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND); // Asegúrate de especificar el tipo
                })
                .orElse(new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND)); // Asegúrate de especificar el tipo
    }

}

