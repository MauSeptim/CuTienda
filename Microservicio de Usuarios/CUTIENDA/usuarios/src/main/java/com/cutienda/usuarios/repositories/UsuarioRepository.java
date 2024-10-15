package com.cutienda.usuarios.repositories;

import com.cutienda.usuarios.models.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    Usuario findByCorreoElectronico(String correoElectronico);

    Optional<Usuario> findByNombreAndApellidosAndCorreoElectronicoAndTelefono(
            String nombre, String apellidos, String correoElectronico, String telefono);
}
