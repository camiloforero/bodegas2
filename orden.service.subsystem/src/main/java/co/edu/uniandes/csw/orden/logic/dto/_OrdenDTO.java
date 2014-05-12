
package co.edu.uniandes.csw.orden.logic.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public abstract class _OrdenDTO {

	private Long id;
	private String name;
	private String tipo;
	private String estado;

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
	public String getTipo() {
		return tipo;
	}
 
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getEstado() {
		return estado;
	}
 
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}