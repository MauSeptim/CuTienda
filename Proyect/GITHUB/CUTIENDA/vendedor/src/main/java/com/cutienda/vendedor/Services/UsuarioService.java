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

    @Autowired
    private ComentarioRepositorio comentarioRepository;

    public Usuario obtenerVendedor(Long id_vendedor) {
        return usuarioRepository.findById(id_vendedor).orElse(null);
    }

    public List<Comentario> obtenerComentariosPorVendedor(Long id_vendedor) {
        return comentarioRepository.findByIdVendedor(id_vendedor);
    }

    public double calcularPuntuacionPromedio(Long id_vendedor) {
        List<Comentario> comentarios = comentarioRepository.findByIdVendedor(id_vendedor);
        return comentarios.stream().mapToInt(Comentario::getCalificacion).average().orElse(0.0);
    }
}
