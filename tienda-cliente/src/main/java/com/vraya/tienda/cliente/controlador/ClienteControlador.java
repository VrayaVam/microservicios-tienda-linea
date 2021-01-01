/**
 * 
 */
package com.vraya.tienda.cliente.controlador;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vraya.tienda.cliente.entidad.ClienteEntidad;
import com.vraya.tienda.cliente.entidad.RegionEntidad;
import com.vraya.tienda.cliente.excepcion.MensajeError;
import com.vraya.tienda.cliente.servicio.IClienteServicio;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Xideral01
 *
 */
//lombok para el logg
@Slf4j
@RestController
@RequestMapping("/clientes")
public class ClienteControlador {
	
	@Autowired
	private IClienteServicio iClienteServicio;
	//-----------------------------------------ObtenerTodosLosClientes
	
	@GetMapping
	public ResponseEntity<List<ClienteEntidad>> listaClientes(@RequestParam(name="idRegion", required=false) Long idRegion){
		List<ClienteEntidad> clientes = new ArrayList<>();
		if(null==idRegion) {
			clientes =iClienteServicio.buscarClientes();
			if(clientes.isEmpty()) {
				return ResponseEntity.noContent().build();
			}
		}else {
			RegionEntidad region= new RegionEntidad();
			region.setId(idRegion);
			clientes =iClienteServicio.buscarClientesPorRegion(region);
			if(null==clientes) {
				log.error("Clientes con region id{} no encontrado",idRegion);
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.ok(clientes);
	}

	//-----------------------------------------RegresarUnSimpleCliente
	
	@GetMapping(value="/{id}")
	public ResponseEntity<ClienteEntidad> obtenerCliente(@PathVariable("id") Long id){
		log.info("Regres cliente con id{}",id);
		ClienteEntidad cliente= iClienteServicio.obtenerCliente(id);
		if(null==cliente) {
			log.error("Cliente con id{} no encontrado0",id);
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cliente);
	}
	
	//-----------------------------------------Crear un cliente
	
	
	@PostMapping
	public  ResponseEntity<ClienteEntidad> crearCliente(@Valid @RequestBody ClienteEntidad cliente, BindingResult result){
		log.info("creando cliente:{}",cliente);
		if(result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatearMensajes(result));
		}
		
		ClienteEntidad clienteBD= iClienteServicio.registrarCliente(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteBD);
	}
	
	//-----------------------------------------Modificar cliente
	
	@PutMapping(value="/{id}")
		public ResponseEntity<?> modificarCliente(@PathVariable("id") long id, @RequestBody ClienteEntidad cliente){
			
			log.info("Actualizando cliente con id{}",id);
			ClienteEntidad clienteActual = iClienteServicio.obtenerCliente(id);
			if(null== clienteActual) {
				log.error("Imposible para actualizar el cliente. Cliente con id{} no encontrado", id);
				return ResponseEntity.notFound().build();
			}
			
			cliente.setId(id);
			clienteActual=iClienteServicio.actulizarCliente(cliente);
			return ResponseEntity.ok(clienteActual);
			
		}
		
		//-----------------------------------------Delete cliente
		@DeleteMapping(value="/{id}")
		public ResponseEntity<ClienteEntidad> borrarCliente(@PathVariable("id") long id){
			
			log.info("Obteniendo y borrando el cliente con id{}",id);
			ClienteEntidad cliente = iClienteServicio.obtenerCliente(id);
			if(null== cliente) {
				log.error("Imposible para borrr el cliente. Cliente con id{} no encontrado", id);
				return ResponseEntity.notFound().build();
			}
			
			
			cliente=iClienteServicio.borrarCliente(cliente);
			return ResponseEntity.ok(cliente);
			
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
