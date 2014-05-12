
package co.edu.uniandes.csw.log.persistence.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class _LogEntity {

	@Id
	@GeneratedValue(generator = "Log")
	private Long id;
	private String name;
	private Integer cantidad;
	private Boolean entra;
	private String justificacion;
	private Long bodegaId;
	private Long productoId;

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
	public Integer getCantidad(){
		return cantidad;
	}
	
	public void setCantidad(Integer cantidad){
		this.cantidad = cantidad;
	}
	public Boolean getEntra(){
		return entra;
	}
	
	public void setEntra(Boolean entra){
		this.entra = entra;
	}
	public String getJustificacion(){
		return justificacion;
	}
	
	public void setJustificacion(String justificacion){
		this.justificacion = justificacion;
	}
	public Long getBodegaId(){
		return bodegaId;
	}
	
	public void setBodegaId(Long bodegaId){
		this.bodegaId = bodegaId;
	}
	public Long getProductoId(){
		return productoId;
	}
	
	public void setProductoId(Long productoId){
		this.productoId = productoId;
	}
}