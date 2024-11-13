package com.cutienda.vendedor.Services;

// UsuarioService.java
import com.cutienda.vendedor.Modelos.Comentario;
import com.cutienda.vendedor.Modelos.Usuario;
import com.cutienda.vendedor.Repositorios.ComentarioRepositorio;
import com.cutienda.vendedor.Repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepositorio usuarioRepository;

    public Usuario obtenerVendedor(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
}
