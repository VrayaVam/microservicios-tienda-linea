/**
 * 
 */
package com.vraya.tienda.compra.modelo;

import lombok.Data;

/**
 * @author Xideral01
 *
 */
@Data
public class ProductoModelo {
	
	private Long id;
	
	private String name;
	
	private String descripcion;
	
	private Double inventario;
	
	private Double precio;
	
	private String estatus;
	
	private CategoriaModelo categoria;

}
