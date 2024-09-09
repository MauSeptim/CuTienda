package com.cutienda.usuarios.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cutienda.usuarios.models.Usuario;
import com.cutienda.usuarios.repositories.UsuarioRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final Path rootLocation = Paths.get("uploads");

    public Usuario registrarUsuario(Usuario usuario, MultipartFile foto) {
        try {
            if (!foto.isEmpty()) {
                String filename = foto.getOriginalFilename();
                Path destinationFile = this.rootLocation.resolve(
                                Paths.get(filename))
                        .normalize().toAbsolutePath();
                Files.copy(foto.getInputStream(), destinationFile);
                usuario.setFoto(destinationFile.toString());
            }
            return usuarioRepository.save(usuario);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo", e);
        }
    }

    public Usuario obtenerUsuarioPorCorreo(String correoElectronico) {
        return usuarioRepository.findByCorreoElectronico(correoElectronico);
    }
}
