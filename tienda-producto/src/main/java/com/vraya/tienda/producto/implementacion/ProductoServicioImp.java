/**
 * 
 */
package com.vraya.tienda.producto.implementacion;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vraya.tienda.producto.entidad.CategoriaEntity;
import com.vraya.tienda.producto.entidad.ProductoEntity;
import com.vraya.tienda.producto.repositorio.IProductoRepositorio;
import com.vraya.tienda.producto.servicio.IProductoServicio;

import lombok.RequiredArgsConstructor;

/**
 * @author Xideral01
 *
 */
//RequiredArgsContructor ayuda hacer la inyecion de dependencias por constructor, es decir,
//vamos a inyectar productRepositorio en el constructor de productServicesImpl
@Service
@RequiredArgsConstructor
public class ProductoServicioImp implements IProductoServicio{

	//@Autowired
	private final IProductoRepositorio iProductoRepositorio;
	
	
	@Override
	public List<ProductoEntity> listarProductos() {
		return iProductoRepositorio.findAll();
	}

	@Override
	public ProductoEntity obtenerProducto(Long id) {
		ProductoEntity other =null;
		return iProductoRepositorio.findById(id).orElse(other);
	}

	@Override
	public ProductoEntity crearProducto(ProductoEntity producto) {
		producto.setEstado("Creado");
		producto.setFechaCreacion(new Date());
		return iProductoRepositorio.save(producto);
	}

	@Override
	public ProductoEntity actualizarProducto(ProductoEntity producto) {
		ProductoEntity productDB = obtenerProducto(producto.getId());
		if(null == productDB) {
			return null;
		}
		
		productDB.setNombre(producto.getNombre());
		productDB.setDescripcion(producto.getDescripcion());
		productDB.setCategoria(producto.getCategoria());
		productDB.setPrecio(producto.getPrecio());
		return iProductoRepositorio.save(productDB);
	}

	@Override
	public ProductoEntity borrarProducto(Long id) {
		ProductoEntity productDB = obtenerProducto(id);
		if(null == productDB) {
			return null;
		}
		
		productDB.setEstado("Borrado"); 
		return iProductoRepositorio.save(productDB);
	}

	@Override
	public List<ProductoEntity> buscarPorCategoria(CategoriaEntity categoria) {
		// TODO Auto-generated method stub
		return iProductoRepositorio.findByCategoria(categoria);
	}

	@Override
	public ProductoEntity actualizarInventario(Long id, double cantidad) {
		ProductoEntity productDB = obtenerProducto(id);
		if(null == productDB) {
			return null;
		}
		Double inventario= productDB.getInventario()+cantidad;
		productDB.setInventario(inventario);
		return iProductoRepositorio.save(productDB);
	}
	

}
