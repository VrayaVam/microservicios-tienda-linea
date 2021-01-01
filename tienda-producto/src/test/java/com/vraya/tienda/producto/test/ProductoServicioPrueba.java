package com.vraya.tienda.producto.test;

import java.util.Date;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vraya.tienda.producto.entidad.CategoriaEntity;
import com.vraya.tienda.producto.entidad.ProductoEntity;
import com.vraya.tienda.producto.implementacion.ProductoServicioImp;
import com.vraya.tienda.producto.repositorio.IProductoRepositorio;
import com.vraya.tienda.producto.servicio.IProductoServicio;

@SpringBootTest
public class ProductoServicioPrueba {
	
	/**
	 * El mock permite hacer pruebas con datos simulados
	 */
	@Mock
	private IProductoRepositorio iProductoRepositorio;
	
	private IProductoServicio iProductoServicio;
	
	/**
	 * Esto tiene que ser ejecuato antes de iniciar este test
	 */
	@BeforeEach
	public void configurarDatos() {
		
		//inicializamos la prueba
		MockitoAnnotations.initMocks(this);
		//instanciamos a productServices y pasmos nuestro repositorio mokeado por el contructor
		iProductoServicio = new ProductoServicioImp(iProductoRepositorio);
		//crear un producto
		ProductoEntity computer =  ProductoEntity.builder()
				.nombre("computer")
                .descripcion("")
                .inventario(Double.parseDouble("12"))
                .precio(Double.parseDouble("5"))
                .build();
		
		//Cuando un producto con 1d=1 es buscado, vamos  a retornar nuetro productMokc computer 
		Mockito.when(iProductoRepositorio.findById(1L))
				.thenReturn(Optional.of(computer));
		
		//Mockear actulizacion
		Mockito.when(iProductoRepositorio.save(computer))
		.thenReturn(computer);
		
		
	}
	
	/**
	 * Método validando la búsqueda de nuestro computer
	 */
	@Test
	public void validarProducto() {
		
		ProductoEntity producto = iProductoServicio.obtenerProducto(1L);
		System.out.println("Nombre del producto-----"+producto.getNombre());
		Assertions.assertThat(producto.getNombre().equals("computer"));
		
	}
	
	/**
	 * Verificar nuestra lógica de negocio está funcionando bien, agregamos un nuevo valor a nuestro invenario
	 * este tiene que ser actualizado.
	 */
	public void validarActualizacionInventario() {
		ProductoEntity productoInventario= iProductoServicio.actualizarInventario(1L, Double.parseDouble("8"));
		Assertions.assertThat(productoInventario.getInventario()).isEqualTo(13);
	}
}
