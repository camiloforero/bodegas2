
package co.edu.uniandes.csw.iteminventario.logic.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public abstract class _ItemInventarioDTO {

	private Long id;
	private Integer cantidad;
	private String name;
	private Long productoId;

	public Long getId() {
		return id;
	}
 
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getCantidad() {
		return cantidad;
	}
 
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
	public Long getProductoId() {
		return productoId;
	}
 
	public void setProductoId(Long productoid) {
		this.productoId = productoid;
	}
	
}