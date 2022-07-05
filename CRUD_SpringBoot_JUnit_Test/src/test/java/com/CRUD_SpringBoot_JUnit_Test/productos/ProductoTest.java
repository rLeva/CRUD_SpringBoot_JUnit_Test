package com.CRUD_SpringBoot_JUnit_Test.productos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest // Con esta anotación le indicamos que vamos a hacer una prueba unitaria de una
				// entidad
@AutoConfigureTestDatabase(replace = Replace.NONE) // le indico que me haga las operaciones a la BBDD real
@TestMethodOrder(OrderAnnotation.class) // para pasarle el orden de las pruebas unutarias se va a ejecutar primero
public class ProductoTest {

	@Autowired
	private ProductoRepository repo;

	@Test // Con esta anotacion le indico que el metodo testGuardarProducto() es un método
			// que vamos a probar...
	@Rollback(false)
	@Order(1)
	public void testGuardarProducto() {
		// Instanciamos un objeto Producto con nombre y precio
		Producto producto = new Producto("Laptop", 780);
		Producto productoGuardado = repo.save(producto);
		// apruebo esta prueba unitaria siempre que el valor pasado por el parentesis no
		// sea nulo
		assertNotNull(productoGuardado);
	}

	
	@Test
	@Order(2)
	public void testBuscarProductoPorNombre() { // creamos una variable con
		// el mismo nombre que el campo nombre de la BBDD que queramos buscar
		String nombre = "Televisor"; // me va a buscar el objeto que coincida con el nombre que le pasamos
		Producto producto = repo.findByNombre(nombre); // compruebo que
		// el nombre que recojo de la BBDD es igual al nombre que le estamos pasando con
		// la variable nombre
		assertThat(producto.getNombre()).isEqualTo(nombre);
	}

	
	@Test
	@Order(3)
	public void testBuscarProductoPorNombreNoExistente() {
		// creamos una variable con un valor que no existe en la BBDD
		String nombre = "Televisor HD";
		// Busco un objeto que coincida con el nombre de la variable que he creado
		// anteriormente
		Producto producto = repo.findByNombre(nombre);
		// compruebo que el nombre que recojo de la BBDD no coincide con el nombre que
		// le paso, que el campo es null
		assertNull(producto);
	}

	
	@Test // Con esta anotacion le indico que el metodo testGuardarProducto() es un método
			// que vamos a probar...
	@Rollback(false)
	@Order(4)
	public void testActualizarProducto() {
		// ACTUALIZO DATOS
		// creo una variable con el nombre del producto que va a ser el nuevo valor del
		// objeto.
		String nombreProducto = "Televisor HD";
		// creo un objeto producto pasandole nuevos datos, un nuevo nombre y un nuevo
		// precio
		Producto producto = new Producto(nombreProducto, 650);
		// pasamos el id del producto a actualizar, en este caso es el 1
		producto.setId(1);
		// guardo los nuevos datos
		repo.save(producto);

		// CONFIRMO QUE EL PRODUCTO SE A ACTUALIZADO
		// creo un objeto producto con los datos actualizados y le paso el nombre del
		// producto que acabo de crear.
		Producto productoActualizado = repo.findByNombre(nombreProducto);
		// confirmo que el productoActualizado es igual al nombre del producto
		assertThat(productoActualizado.getNombre()).isEqualTo(nombreProducto);

	}

	
	@Test
	@Order(5)
	public void testListarProductos() {
		// creo una lista de tipo productos,como el metodo findAll no es de tipo
		// lista producto lo casteo para que no haya problemas
		List<Producto> productos = (List<Producto>) repo.findAll();

		for (Producto producto : productos) {
			System.out.println(producto);
		}
		// el metodo se confirma si la lista de Productos es mayor que cero
		assertThat(productos).size().isGreaterThan(0);
	}

	
	@Test // Con esta anotacion le indico que el metodo testGuardarProducto() es un método
			// que vamos a probar...
	@Rollback(false)
	@Order(6)
	public void testEliminarProducto() {
		// primero me creo un id ya que elimina los productos buscandolos por su id
		Integer id = 1;

		// creo una variable booleana para ver si el id existe
		boolean esExistenteAntesdeEliminar = repo.findById(id).isPresent();
		repo.deleteById(id);
		boolean noExistenteDespuesdeEliminar = repo.findById(id).isPresent();

		assertTrue(esExistenteAntesdeEliminar);
		assertFalse(noExistenteDespuesdeEliminar);
	}
}
