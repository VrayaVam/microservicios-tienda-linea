/**
 * 
 */
package com.vraya.tienda.producto.servicio;
import java.util.List;
import com.vraya.tienda.producto.entidad.*;

/**
 * @author Xideral01
 *
 */
public interface IProductoServicio {
	
	public List<ProductoEntity> listarProductos();
	public ProductoEntity obtenerProducto(Long id);
	public ProductoEntity crearProducto(ProductoEntity producto);
	public ProductoEntity actualizarProducto(ProductoEntity producto);
	public ProductoEntity borrarProducto(Long id);
	public List<ProductoEntity> buscarPorCategoria(CategoriaEntity categoria);
	public ProductoEntity actualizarInventario(Long id, double cantidad);

}
