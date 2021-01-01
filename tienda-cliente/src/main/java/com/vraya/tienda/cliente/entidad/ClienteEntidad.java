/**
 * 
 */
package com.vraya.tienda.cliente.entidad;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.Data;

/**
 * @author Xideral01
 *
 */
@Data
@Entity
@Table(name = "tbl_clientes")
public class ClienteEntidad implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "El número de documento no puede ser vacio")
	@Size(min = 8, max = 8, message = "La longitud del de documento tiene que ser 8")
	@Column(name = "id_numero", unique = true, length = 8, nullable = false)
	private String idNumero;
	
	@NotEmpty(message = "El primer nombre no puede ser vacio")
	@Column(name = "primer_nombre", nullable = false)
	private String primerNombre;
	
	@NotEmpty(message = "El apellido no puede ser vacio")
	@Column(name = "apellido", nullable = false)
	private String apellido;
	
	@NotEmpty(message = "El correo es obligatorio")
	@Email(message = "La direccion del correo está mal formada")
	@Column(name="correo_elec", unique = true, nullable = false)
	private String correo;
	
	@Column(name = "foto")
	private String fotoUrl;
	
	
	@NotNull(message = "La región tiene que existir")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_region")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private RegionEntidad region;
	
	
	private String estado;
	

}
