/**
 * 
 */
package com.vraya.tienda.compra.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vraya.tienda.compra.entidad.FacturaEntidad;

/**
 * @author Xideral01
 *
 */
public interface IFacturaRepositorio extends JpaRepository<FacturaEntidad, Long> {
	
	public List<FacturaEntidad> findByIdCliente (Long idCliente);
	
	public FacturaEntidad findByNumeroFactura(String numeroFactura);
	
	

}
