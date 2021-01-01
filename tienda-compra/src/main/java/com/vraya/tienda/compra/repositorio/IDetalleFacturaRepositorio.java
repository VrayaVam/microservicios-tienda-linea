/**
 * 
 */
package com.vraya.tienda.compra.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vraya.tienda.compra.entidad.DetalleFacturaEntidad;

/**
 * @author Xideral01
 *
 */
public interface IDetalleFacturaRepositorio extends JpaRepository<DetalleFacturaEntidad, Long> {

}
