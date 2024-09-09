package com.cutienda.usuarios.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.cutienda.usuarios.models.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Usuario findByCorreoElectronico(String correoElectronico);
}

