package com.cutienda.usuarios.services;

import com.cutienda.usuarios.models.Usuario;
import com.cutienda.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario registrarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario autenticar(String correoElectronico, String contraseña) {
        Optional<Usuario> usuario = usuarioRepository.findByCorreoElectronico(correoElectronico);
        return usuario.filter(u -> u.getContraseña().equals(contraseña)).orElse(null);
    }

    public Usuario buscarUsuario(String nombre, String apellidos, String correo, String telefono) {
        return usuarioRepository.findAll().stream()
                .filter(u -> u.getNombre().equalsIgnoreCase(nombre)
                        && u.getApellidos().equalsIgnoreCase(apellidos)
                        && u.getCorreoElectronico().equalsIgnoreCase(correo)
                        && (telefono == null || u.getTelefono().equals(telefono)))
                .findFirst().orElse(null);
    }

    public void actualizarContraseña(Long id, String nuevaContraseña) {
        usuarioRepository.findById(id).ifPresent(usuario -> {
            usuario.setContraseña(nuevaContraseña);
            usuarioRepository.save(usuario);
        });
    }

    public Optional<Usuario> existeCorreo(String correoElectronico) {
        return usuarioRepository.findByCorreoElectronico(correoElectronico);
    }



    public void eliminar(Long id) {
        // Verificar si el usuario existe antes de eliminar
        if (usuarioRepository.existsById(id)) {
            // Eliminar el usuario
            usuarioRepository.deleteById(id);
        } else {
            // Manejar la situación si el usuario no existe (opcional)
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
    }

    public Usuario actualizarUsuario(Usuario usuario) {
        // Verificar si el usuario existe
        if (usuarioRepository.existsById(usuario.getId())) {
            // Actualizar el usuario en la base de datos
            return usuarioRepository.save(usuario);
        } else {
            // Manejar la situación si el usuario no existe (opcional)
            throw new RuntimeException("Usuario no encontrado con ID: " + usuario.getId());
        }
    }

}
