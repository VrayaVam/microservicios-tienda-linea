/**
 * 
 */
package com.vraya.tienda.cliente.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vraya.tienda.cliente.entidad.ClienteEntidad;
import com.vraya.tienda.cliente.entidad.RegionEntidad;

import java.util.List;
/**
 * @author Xideral01
 *
 */
public interface IClienteRepositorio extends JpaRepository<ClienteEntidad, Long> {
	
	public ClienteEntidad findByIdNumero(String idNumero);
	public List<ClienteEntidad> findByApellido(String apellido);
	public List<ClienteEntidad> findByRegion(RegionEntidad region);
	

}
