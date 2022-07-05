package com.CRUD_SpringBoot_JUnit_Test.productos;

import org.springframework.data.repository.CrudRepository;

public interface ProductoRepository extends CrudRepository<Producto, Integer> {
	
	//creo método para que me busque un objeto por el nombre
	public Producto findByNombre(String nombre);

}
