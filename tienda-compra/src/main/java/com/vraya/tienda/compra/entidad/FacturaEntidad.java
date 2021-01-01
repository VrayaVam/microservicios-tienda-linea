/**
 * 
 */
package com.vraya.tienda.compra.entidad;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * @author Xideral01
 *
 */
@Data
@Entity
@Table(name = "tbl_facturas")
public class FacturaEntidad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column (name = "numero_factura")
	private String numeroFactura;
	
	private String descripion;
	
	@Column(name="id_Cliente")
	private Long idCliente;
	
	@Column(name="fecha_creacion")
	@Temporal(TemporalType.DATE)
	private Date fechaCreacion;
	
	/*
	 * Estamos usando el One to Many para indicar la creacion de una 
	 * factura con su detalles y si la cabecera no existe, los items tampo existen
	 */
	@Valid
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@OneToMany(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
	@JoinColumn(name="id_factura")
	private List<DetalleFacturaEntidad>detalles;
	
	private String estado;
	
	public FacturaEntidad() {detalles= new ArrayList<>();}
	
	/**
	 * Crear la fecha antes de insertarla
	 */
	@PrePersist
	public void preCrearFecha() {
		this.fechaCreacion= new Date();
	}

}
