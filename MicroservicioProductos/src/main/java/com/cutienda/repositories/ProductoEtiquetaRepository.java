package com.cutienda.repositories;

import com.cutienda.models.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoEtiquetaRepository extends MongoRepository<Producto, String> {
}
