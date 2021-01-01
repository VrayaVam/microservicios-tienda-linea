package com.vraya.tienda.cliente.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vraya.tienda.cliente.entidad.ClienteEntidad;
import com.vraya.tienda.cliente.entidad.RegionEntidad;
import com.vraya.tienda.cliente.repositorio.IClienteRepositorio;
import com.vraya.tienda.cliente.servicio.IClienteServicio;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClienteServicioImpl implements IClienteServicio {
	
	@Autowired
	private IClienteRepositorio iClienteRepositorio;

	@Override
	public List<ClienteEntidad> buscarClientes() {
		return iClienteRepositorio.findAll();
	}

	@Override
	public List<ClienteEntidad> buscarClientesPorRegion(RegionEntidad region) {
		
		return iClienteRepositorio.findByRegion(region);
	}

	/**
	 * implementamos un mécanismo para hacer el método POST idempotente
	 */
	@Override
	public ClienteEntidad registrarCliente(ClienteEntidad cliente) {
		//esta validacion permite hacer la validacion indempotente
		ClienteEntidad clienteBD = iClienteRepositorio.findByIdNumero(cliente.getIdNumero());
		
		if(clienteBD!=null) {
			return clienteBD;
		}
		
		cliente.setEstado("Creado");
		clienteBD = iClienteRepositorio.save(cliente);
		return clienteBD;
	}

	@Override
	public ClienteEntidad actulizarCliente(ClienteEntidad cliente) {
		ClienteEntidad clienteBD = this.obtenerCliente(cliente.getId());
		if(clienteBD == null) {
			return null;
		}
		clienteBD.setPrimerNombre(cliente.getPrimerNombre());
		clienteBD.setApellido(cliente.getApellido());
		clienteBD.setCorreo(cliente.getCorreo());
		clienteBD.setFotoUrl(cliente.getFotoUrl());
		return iClienteRepositorio.save(clienteBD);
	}

	@Override
	public ClienteEntidad borrarCliente(ClienteEntidad cliente) {
		ClienteEntidad clienteBD = this.obtenerCliente(cliente.getId());
		if(clienteBD == null) {
			return null;
		}
		
		cliente.setEstado("Borrado");
		return iClienteRepositorio.save(cliente);
	}
	
	public ClienteEntidad obtenerCliente(Long id) {
		return iClienteRepositorio.findById(id).orElse(null);
		
	}

}
