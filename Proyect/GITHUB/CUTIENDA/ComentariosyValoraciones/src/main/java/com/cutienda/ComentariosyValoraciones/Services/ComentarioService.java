package com.cutienda.ComentariosyValoraciones.Services;

import com.cutienda.ComentariosyValoraciones.Modelos.ComentarioModel;
import com.cutienda.ComentariosyValoraciones.Repository.ComentarioRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepositories comentarioRepository;

    // Método para registrar un nuevo comentario
    public ComentarioModel registrarComentario(ComentarioModel comentario) {
        // Guardar el comentario en la base de datos
        return comentarioRepository.save(comentario);
    }

}
