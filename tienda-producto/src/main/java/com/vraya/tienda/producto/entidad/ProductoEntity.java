/**
 * 
 */
package com.vraya.tienda.producto.entidad;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * @author Xideral01
 *
 */
//esto permite crear instacias de nuestra clase
@Entity
@Table(name="tbl_productos")
@Data
@AllArgsConstructor @NoArgsConstructor @Builder 
public class ProductoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//validación de campos
	@NotEmpty(message = "El nombre es obligatorio")
	private String nombre;
	private String descripcion;
	
	//validar el inventario mayor a cero, caso contrario enviar un mensje
	@Positive(message = "El inventario tiene que ser mayor  0")
	private Double inventario;
	private Double precio;
	private String estado;
	
	@Column(name="fecha_creacion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCreacion;
	
	//EAGER carga todo, Lazy carga solo por peticion
	//jsonIgnoringProperties ayuda a eliminar el error lazy
	//Validar los campos usamos @NotNull, caso contrario enviamos un mensaje
	@NotNull(message = "La categoria tiene que ser distinta de vacia")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_categoria")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private CategoriaEntity categoria;
	

}
