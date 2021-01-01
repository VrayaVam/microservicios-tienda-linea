/**
 * 
 */
package com.vraya.tienda.producto.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vraya.tienda.producto.entidad.CategoriaEntity;
import com.vraya.tienda.producto.entidad.ProductoEntity;

/**
 * @author Xideral01
 *
 */

public interface IProductoRepositorio extends JpaRepository<ProductoEntity, Long> {

	public List<ProductoEntity> findByCategoria(CategoriaEntity categoria);
}
