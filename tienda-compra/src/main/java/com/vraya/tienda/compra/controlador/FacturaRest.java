/**
 * 
 */
package com.vraya.tienda.compra.controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vraya.tienda.compra.entidad.FacturaEntidad;
import com.vraya.tienda.compra.excepcion.MensajeError;
import com.vraya.tienda.compra.impl.FacturaServicioImpl;
import com.vraya.tienda.compra.repositorio.IFacturaRepositorio;
import com.vraya.tienda.compra.servicio.IFacturaServicio;

import lombok.Delegate;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Xideral01
 *
 */
@Slf4j
@RestController
@RequestMapping("/facturas")
public class FacturaRest {
	
	@Autowired(required = true)
	private IFacturaServicio iFacturaServicio;
	
	// -------------------------------Obtener todas las facturas
	@GetMapping
	public ResponseEntity<List<FacturaEntidad>> listarFacturas(){
		List<FacturaEntidad> facturas = iFacturaServicio.buscarFacturas();
		if(facturas.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(facturas);
	}
	
	//----------------------Obtener una sola factura
	
	@GetMapping(value= "/{id}")
	public ResponseEntity<FacturaEntidad> obtenerFactura(@PathVariable("id") long id){
		
		log.info("Recuperando factura con id{}",id);
		FacturaEntidad factura= iFacturaServicio.obtenerFactura(id);
		if(null==factura) {
			log.error("Factura con id {} not found",id);
			return ResponseEntity.notFound().build();
		}
		 return ResponseEntity.ok(factura);
		
	}
	
	//----------------------Crear factura
	
	@PostMapping
	public ResponseEntity<FacturaEntidad> crearFactura(@Valid @RequestBody FacturaEntidad factura, BindingResult result){
		
		log.info("Creando factura: {}", factura);
		if(result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatearMensajes(result));
		}
		
		FacturaEntidad facturaDB= iFacturaServicio.crearFactura(factura);
		return ResponseEntity.status(HttpStatus.CREATED).body(facturaDB);
		
	}
	
	
	//----------------------Borrar factura
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<FacturaEntidad> borrarFactura(@PathVariable("id") long id){
		log.info("llevar y borrar factura con id{}", id);
		FacturaEntidad factura = iFacturaServicio.obtenerFactura(id);
		if(factura==null) {
			log.error("indisponibilidad para borrar. Factura con id{} no encontrada", id);
			return ResponseEntity.notFound().build();
		}
		factura =iFacturaServicio.borrarFactura(factura);
		return ResponseEntity.ok(factura);
	}
	
	//----------------------Actualizar factura
	@PutMapping(value="/{id}")
	public ResponseEntity<?> actualizarFactura(@PathVariable("") long id, @RequestBody FacturaEntidad factura ){
		
		log.info("Actualizar factura con id{}",id);
		factura.setId(id);
		FacturaEntidad facturaActual =iFacturaServicio.actualizarFactura(factura);
		if(facturaActual == null) {
			log.error("Indisponibilidad para acutalizar factura. Factura con id{} no encontrada", id);
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(facturaActual);
	}
	
	
	private String formatearMensajes(BindingResult result) {
		List<Map<String, String>> errors = result.getFieldErrors().stream()
				.map(err ->{
				Map<String, String> error= new HashMap<>();
				error.put(err.getField(), err.getDefaultMessage());
				return error;
				 }).collect(Collectors.toList());
		MensajeError mensajeError =MensajeError.builder()
				.codigo("01")
				.mensajes(errors).build();
		ObjectMapper mapper = new ObjectMapper();
		String jsonString="";
		try {
			jsonString = mapper.writeValueAsString(mensajeError);
		} catch (JsonProcessingException e) {
				e.printStackTrace();
		}
			return jsonString;	
		
	}

}
