package com.vraya.tienda.compra.servicio;

import java.util.List;

import com.vraya.tienda.compra.entidad.FacturaEntidad;

public interface IFacturaServicio {
	
	public List<FacturaEntidad> buscarFacturas();
	
	public FacturaEntidad crearFactura(FacturaEntidad factura);
	
	public FacturaEntidad actualizarFactura(FacturaEntidad factura);
	
	public FacturaEntidad borrarFactura(FacturaEntidad factura);
	
	public FacturaEntidad obtenerFactura(Long id);
	
	

}
