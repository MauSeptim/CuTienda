package com.cutienda.services;

import com.cutienda.models.Producto;
import com.cutienda.repositories.EtiquetaRepository;
import com.cutienda.repositories.ProductoEtiquetaRepository;
import com.cutienda.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService{
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    EtiquetaRepository etiquetaRepository;
    @Autowired
    ProductoEtiquetaRepository productoEtiquetaRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository, EtiquetaRepository etiquetaRepository, ProductoEtiquetaRepository productoEtiquetaRepository) {
        this.productoRepository = productoRepository;
        this.etiquetaRepository = etiquetaRepository;
        this.productoEtiquetaRepository = productoEtiquetaRepository;
    }

    @Override
    public Producto guardar(Producto producto) {
        if (producto != null)
            return productoRepository.save(producto);
        else
            return null;
    }

    @Override
    public Producto eliminarPorId(String id) {
        Producto eliminado = conseguirPorId(id);

        if (eliminado != null) {
            productoRepository.deleteById(id);
        }
        return eliminado;
    }

    @Override
    public Producto conseguirPorId(String id) {
        Optional<Producto> opc = productoRepository.findById(id);

        return opc.orElse(null);
    }

    @Override
    public List<Producto> conseguirTodos() {
        return productoRepository.findAll();
    }

    @Override
    public void actualizarPorId(String id, Producto p) {
        Producto prod = conseguirPorId(id);

        if (prod != null) {
            prod.setDescripcion(p.getDescripcion());
            prod.setFoto(p.getFoto());
            prod.setNombre(p.getNombre());
            prod.setPrecio(p.getPrecio());
            prod.setVendedor(p.getVendedor());

            guardar(prod);
        }
    }
}
