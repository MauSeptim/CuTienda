package com.cutienda.repositories;

import com.cutienda.models.Etiqueta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtiquetaRepository extends MongoRepository<Etiqueta, String> {
}
