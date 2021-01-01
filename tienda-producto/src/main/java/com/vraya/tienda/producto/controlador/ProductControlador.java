/**
 * 
 */
package com.vraya.tienda.producto.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vraya.tienda.producto.entidad.CategoriaEntity;
import com.vraya.tienda.producto.entidad.ProductoEntity;
import com.vraya.tienda.producto.exeption.MensajeError;
import com.vraya.tienda.producto.servicio.IProductoServicio;

/**
 * @author Xideral01
 *
 */
@RestController
@RequestMapping(value="/productos")
public class ProductControlador {
	
	@Autowired
	private IProductoServicio iProductoServicio;
	
	/**
	 * Este método regresa toda la lista de todos los productos
	 * El requestParam recupera el id de la categoría el nombre del parametro es opcional
	 * http://localhost:8091/productos?categoriaId=2
	 */
	@GetMapping
	public ResponseEntity<List<ProductoEntity>> listarProductos(@RequestParam(name="categoriaId", required = false) Long categoriaId) {
		
		List<ProductoEntity> productos = new ArrayList<>();
		
		if(null == categoriaId) {
			productos = iProductoServicio.listarProductos();
			if(productos.isEmpty()) {
				return ResponseEntity.noContent().build();//este es el 204
			}
		}else {
			productos = iProductoServicio.buscarPorCategoria(CategoriaEntity.builder().id(categoriaId).build());
			if(productos.isEmpty()) {
				return ResponseEntity.notFound().build();//este es 404
			}
		}
		
		
		
		return ResponseEntity.ok(productos);// esto regresa un ok un 200
	}
	
	/**
	 * esperamos un id de producto y vamos a capturarlo con un path variable
	 * http://localhost:8091/productos/2
	 * @param id
	 * @return
	 */
	@GetMapping(value="/{id}")
	public ResponseEntity<ProductoEntity> obtenerProducto(@PathVariable("id") Long id) {
		
		ProductoEntity producto  = iProductoServicio.obtenerProducto(id);
		if(null == producto) {
			return ResponseEntity.notFound().build();//este es 404
		}
		
			
		return ResponseEntity.ok(producto);// esto regresa un ok un 200
	}
	
	/**
	 * Esta es el uri para tener los datos
	 * validar nuestro requestbody de nuestro poducto tiene que ser decorado con @Valid
	 * Recibimos el resultado de las validaciones en BindingResult
	 * 
	 *  http://localhost:8091/productos/4
	 * @param producto
	 * @return
	 */
	@PostMapping
	public ResponseEntity<ProductoEntity> registrarProducto(@Valid @RequestBody ProductoEntity producto, BindingResult result) {
		//verificamos la devolucion de errornes, si hay errores enviamos un ResponseStatusException 
		if(result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatearMensaje(result));
		}
		ProductoEntity productoCreado  = iProductoServicio.crearProducto(producto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<ProductoEntity> modificarProducto(@PathVariable("id")Long id, @RequestBody ProductoEntity producto) {
		
		producto.setId(id);
		ProductoEntity productoBD =iProductoServicio.actualizarProducto(producto);
		
		if(productoBD==null) {
			return ResponseEntity.notFound().build();//este es 404
		}
		return ResponseEntity.ok(productoBD);// esto regresa un ok un 200
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<ProductoEntity> eliminarProducto(@PathVariable("id")Long id) {
		
		ProductoEntity productoEliminado =iProductoServicio.borrarProducto(id);
		
		if(productoEliminado==null) {
			return ResponseEntity.notFound().build();//este es 404
		}
		return ResponseEntity.ok(productoEliminado);// esto regresa un ok un 200
	}
	
	/**
	 * Forma de envio
	 * http://localhost:8091/productos/3/inventario?cantidad=10
	 * @param id
	 * @param cantidad
	 * @return
	 */
	@GetMapping(value="/{id}/inventario")
	public ResponseEntity<ProductoEntity> actualizarInventario(@PathVariable("id")Long id, @RequestParam(name="cantidad", required = true) Double cantidad) {
		
		ProductoEntity productoInventariado =iProductoServicio.actualizarInventario(id, cantidad);
		//verificar la existencia del producto
		if(productoInventariado==null) {
			return ResponseEntity.notFound().build();//este es 404
		}
		return ResponseEntity.ok(productoInventariado);// esto regresa un ok un 200
	}

	
	private String formatearMensaje(BindingResult result) {
		List<Map<String, String>> errores = result.getFieldErrors().stream()
								.map(err-> {
									Map<String, String> error = new HashMap();
									error.put(err.getField(), err.getDefaultMessage());
									return error;
								}).collect(Collectors.toList());
		
		MensajeError mensajeError =MensajeError.builder()
							.codigo("01")
							.mensajes(errores).build();
		
		//Vamos usar jackson para construir un json
		ObjectMapper mapper = new ObjectMapper();
		String jsonString="";
		try {
			jsonString = mapper.writeValueAsString(mensajeError);
		} catch (JsonProcessingException e) {
			//e.printStackTrace();
			
		}
		return jsonString;
		
	}

}
