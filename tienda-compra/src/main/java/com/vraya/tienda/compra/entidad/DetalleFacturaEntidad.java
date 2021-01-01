/**
 * 
 */
package com.vraya.tienda.compra.entidad;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Positive;

import com.vraya.tienda.compra.modelo.ProductoModelo;

import lombok.Data;

/**
 * @author Xideral01
 *
 */
@Entity
@Data
@Table(name="tbl_factura_detalles")
public class DetalleFacturaEntidad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * validacion de cantidad mayor a cero
	 */
	@Positive(message = "El inventario tiene que ser mayor a cero")
	private Double cantidad;
	private Double precio;
	
	@Column(name ="id_producto")
	private Long idProducto;
	
	/**
	 * este atributo no va ser registrado en la base de datos, pero , vamos a usuarlo
	 */
	@Transient
	private Double subTotal;
	
	@Transient
	private ProductoModelo product;
	
	public Double getSubTotal() {
		if(this.precio>0 && this.cantidad>0) {
			return this.cantidad * this.precio;
		}else {
			return (double)0;
		}
	}
	
	public DetalleFacturaEntidad() {
		this.cantidad=(double)0;
		this.precio=(double)0;
		
	}
	

}
