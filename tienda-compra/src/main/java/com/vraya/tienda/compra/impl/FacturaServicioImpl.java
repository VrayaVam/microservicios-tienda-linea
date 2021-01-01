package com.vraya.tienda.compra.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vraya.tienda.compra.entidad.FacturaEntidad;
import com.vraya.tienda.compra.repositorio.IDetalleFacturaRepositorio;
import com.vraya.tienda.compra.repositorio.IFacturaRepositorio;
import com.vraya.tienda.compra.servicio.IFacturaServicio;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FacturaServicioImpl  implements IFacturaServicio{
	
	@Autowired
	private IFacturaRepositorio iFacturaRepositorio;
	
	@Autowired
	private IDetalleFacturaRepositorio iDetalleFacturaRepositorio;
	
	

	@Override
	public List<FacturaEntidad> buscarFacturas() {
		return iFacturaRepositorio.findAll();
	}

	@Override
	public FacturaEntidad crearFactura(FacturaEntidad factura) {
		FacturaEntidad facturaDB = iFacturaRepositorio.findByNumeroFactura(factura.getNumeroFactura());
		if(facturaDB !=null){
			return facturaDB;
		}
		factura.setEstado("Creado");
		return iFacturaRepositorio.save(factura);
	}

	@Override
	public FacturaEntidad actualizarFactura(FacturaEntidad factura) {
		FacturaEntidad facturaDB = obtenerFactura(factura.getId());
		if(facturaDB !=null){
			return facturaDB;
		}
		facturaDB.setIdCliente(factura.getIdCliente());
		facturaDB.setDescripion(factura.getDescripion());
		facturaDB.setNumeroFactura(factura.getNumeroFactura());
		facturaDB.getDetalles().clear();
		facturaDB.setDetalles(factura.getDetalles());
		return iFacturaRepositorio.save(facturaDB);
	}

	@Override
	public FacturaEntidad borrarFactura(FacturaEntidad factura) {
		FacturaEntidad facturaDB = obtenerFactura(factura.getId());
		if(facturaDB ==null){
			return null;
		}
		facturaDB.setEstado("Delete");
		return iFacturaRepositorio.save(facturaDB);
	}

	@Override
	public FacturaEntidad obtenerFactura(Long id) {
		return iFacturaRepositorio.findById(id).orElse(null);
	}

}
