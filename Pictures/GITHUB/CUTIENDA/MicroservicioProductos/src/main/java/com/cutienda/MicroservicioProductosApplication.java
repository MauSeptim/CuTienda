package com.cutienda;

import com.cutienda.models.Producto;
import com.cutienda.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroservicioProductosApplication implements CommandLineRunner{
	@Autowired
	ProductoService productoService;

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioProductosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//var p = new Producto("mau", "jose", "ninguno", "jsjsj", 39592.4);
	}
}
