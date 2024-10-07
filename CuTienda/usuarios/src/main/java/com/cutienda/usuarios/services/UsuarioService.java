package com.cutienda.usuarios.services;

import com.cutienda.usuarios.models.Usuario;
import com.cutienda.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario registrarUsuario(Usuario usuario) {
        // Lógica adicional de validación
        if (!usuario.getContraseña().equals(usuario.getConfirmacionContraseña())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        // Guarda el usuario en el repositorio
        return usuarioRepository.save(usuario);
    }




    public Usuario autenticar(String correoElectronico, String contraseña) {
        Usuario usuario = usuarioRepository.findByCorreoElectronico(correoElectronico);

        if (usuario != null && usuario.getContraseña().equals(contraseña)) {
            return usuario; // Autenticación exitosa
        }
        return null; // Autenticación fallida
    }

    public void eliminar(String id) {
        // Verifica si el usuario existe antes de eliminar, si es necesario
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id); // Elimina el usuario por ID
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    @org.jetbrains.annotations.NotNull
    private byte[] almacenarFoto(MultipartFile foto) throws IOException {
        // Convertir el archivo a bytes
        return foto.getBytes();
    }



}
