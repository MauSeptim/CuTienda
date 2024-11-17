package com.cutienda.vendedor.Services;

import com.cutienda.vendedor.Modelos.Comentario;
import com.cutienda.vendedor.Repositorios.ComentarioRepositorio;
import com.cutienda.vendedor.Repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepositorio comentarioRepository;

    public List<Comentario> obtenerComentariosPorVendedor(Long idVendedor) {
        return comentarioRepository.findByIdVendedor(idVendedor);  // Asume que tienes un método en el repositorio para esto
    }

    public double obtenerCalificacionPromedio(Long vendedorId) {
        List<Comentario> comentarios = comentarioRepository.findByIdVendedor(vendedorId);
        if (comentarios.isEmpty()) {
            return 0; // Si no hay comentarios, la calificación promedio es 0
        }
        double suma = 0;
        for (Comentario comentario : comentarios) {
            suma += comentario.getCalificacion();
        }
        return suma / comentarios.size(); // Promedio de calificaciones
    }


}
