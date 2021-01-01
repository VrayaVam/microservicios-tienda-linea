/**
 * 
 */
package com.vraya.tienda.producto.entidad;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * @author Xideral01
 *
 */
//esta anotación genera getters, setters and hashcode, toString y equals
@Entity
@Table (name="tbl_categorias") 
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class CategoriaEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;

}
