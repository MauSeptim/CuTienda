package com.cutienda.usuarios.services;

import com.cutienda.usuarios.models.Usuario;
import com.cutienda.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario registrarUsuario(Usuario usuario, MultipartFile foto) {
        // Lógica para manejar la carga de la foto
        if (foto != null && !foto.isEmpty()) {
            try {
                String fotoUrl = almacenarFoto(foto);
                usuario.setFotoUrl(fotoUrl);
            } catch (IOException e) {
                throw new RuntimeException("Error al almacenar la foto: " + e.getMessage());
            }
        }

        // Lógica adicional de validación
        if (!usuario.getContraseña().equals(usuario.getConfirmacionContraseña())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        return usuarioRepository.save(usuario);
    }

    private String almacenarFoto(MultipartFile foto) throws IOException {
        // Ruta donde se almacenarán las fotos
        String directorioDestino = "C:/Users/user/Videos/CUTIENDA/usuarios/src/main/resources/static/";

        // Verifica si el directorio de destino existe, si no, lo crea
        File directorio = new File(directorioDestino);
        if (!directorio.exists()) {
            directorio.mkdirs(); // Crea el directorio si no existe
        }

        // Genera un nombre único para la imagen
        String nombreArchivo = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();

        // Ruta completa donde se guardará la imagen
        Path rutaCompleta = Paths.get(directorioDestino, nombreArchivo);

        // Guarda el archivo en la ruta especificada
        Files.copy(foto.getInputStream(), rutaCompleta);

        // Retorna la URL relativa para acceder a la imagen
        return "/Perfiles/" + nombreArchivo;
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


}
