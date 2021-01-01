/**
 * 
 */
package com.vraya.tienda.producto.exeption;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Xideral01
 *
 */
@Getter @Setter @Builder
public class MensajeError {
	
	private String codigo;
	private List<Map<String, String>> mensajes;

}
