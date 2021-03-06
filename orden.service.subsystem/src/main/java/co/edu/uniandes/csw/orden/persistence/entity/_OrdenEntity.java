
package co.edu.uniandes.csw.orden.persistence.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class _OrdenEntity {

	@Id
	@GeneratedValue(generator = "Orden")
	private Long id;
	private String name;
	private String tipo;
	private String estado;
	private Integer cantidad;

	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public String getTipo(){
		return tipo;
	}
	
	public void setTipo(String tipo){
		this.tipo = tipo;
	}
	public String getEstado(){
		return estado;
	}
	
	public void setEstado(String estado){
		this.estado = estado;
	}
	public Integer getCantidad(){
		return cantidad;
	}
	
	public void setCantidad(Integer cantidad){
		this.cantidad = cantidad;
	}
}