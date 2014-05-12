
package co.edu.uniandes.csw.log.logic.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public abstract class _LogDTO {

	private Long id;
	private String name;
	private Integer cantidad;
	private Boolean entra;
	private String justificacion;
	private Long bodegaId;
	private Long productoId;

	public Long getId() {
		return id;
	}
 
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCantidad() {
		return cantidad;
	}
 
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Boolean getEntra() {
		return entra;
	}
 
	public void setEntra(Boolean entra) {
		this.entra = entra;
	}
	public String getJustificacion() {
		return justificacion;
	}
 
	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}
	public Long getBodegaId() {
		return bodegaId;
	}
 
	public void setBodegaId(Long bodegaid) {
		this.bodegaId = bodegaid;
	}
	public Long getProductoId() {
		return productoId;
	}
 
	public void setProductoId(Long productoid) {
		this.productoId = productoid;
	}
	
}