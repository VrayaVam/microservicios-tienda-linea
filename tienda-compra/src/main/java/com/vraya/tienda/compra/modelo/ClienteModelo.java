/**
 * 
 */
package com.vraya.tienda.compra.modelo;

import lombok.Builder;
import lombok.Data;

/**
 * @author Xideral01
 *
 */
@Data
@Builder
public class ClienteModelo {

	private Long id;
	
	private String idNumero;
	
	private String nombreCompleto;
	
	private String apellido;
	
	private String correo;
	
	private String fotoUrl;
	
	private RegionModelo region;
	
	private String estado;

}
