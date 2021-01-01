/**
 * 
 */
package com.vraya.tienda.producto.test;

import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.vraya.tienda.producto.entidad.CategoriaEntity;
import com.vraya.tienda.producto.entidad.ProductoEntity;
import com.vraya.tienda.producto.repositorio.IProductoRepositorio;

/**
 * @author Xideral01
 *
 */
@DataJpaTest
public class RepositorioDePruebasProducto {

	
	@Autowired
	private IProductoRepositorio iProductoRepositorio;
	
	@Test
	public void buscarProductoXCategoria() {
		
		ProductoEntity producto01 =  ProductoEntity.builder()
									.nombre("computer")
				                    .descripcion("")
				                    .inventario(Double.parseDouble("10"))
				                    .precio(Double.parseDouble("1240.99"))
				                    .estado("Creado")
				                    .fechaCreacion(new Date())
				                    .categoria(CategoriaEntity.builder().id(1L).build())
				                    .build();
		
		iProductoRepositorio.save(producto01);
		List<ProductoEntity> productos= iProductoRepositorio.findByCategoria(producto01.getCategoria());
		productos.stream().forEach(p->System.out.println(p.toString()));
		Assertions.assertThat(productos.size()).isEqualTo(3);
		
	
	}								
}
	

