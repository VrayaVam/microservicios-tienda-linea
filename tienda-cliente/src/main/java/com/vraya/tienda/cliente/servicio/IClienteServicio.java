/**
 * 
 */
package com.vraya.tienda.cliente.servicio;


import java.util.List;

import com.vraya.tienda.cliente.entidad.ClienteEntidad;
import com.vraya.tienda.cliente.entidad.RegionEntidad;

/**
 * @author Xideral01
 *
 */
public interface IClienteServicio {
	
	public List<ClienteEntidad> buscarClientes();
	
	public List<ClienteEntidad> buscarClientesPorRegion(RegionEntidad region);
	
	public ClienteEntidad registrarCliente(ClienteEntidad cliente);
	
	public ClienteEntidad actulizarCliente(ClienteEntidad cliente);
	
	public ClienteEntidad borrarCliente(ClienteEntidad cliente);
	
	public ClienteEntidad obtenerCliente(Long id);
}
