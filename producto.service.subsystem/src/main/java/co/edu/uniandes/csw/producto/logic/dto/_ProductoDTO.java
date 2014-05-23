
package co.edu.uniandes.csw.producto.logic.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public abstract class _ProductoDTO {

	private Long id;
	private String name;
	private String tipo;
	private String talla;
	private String precio;
	private String imagen;
	private Integer calificacion;
	private Integer numCalificaciones;

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
	public String getTalla() {
		return talla;
	}
 
	public void setTalla(String talla) {
		this.talla = talla;
	}
	public String getPrecio() {
		return precio;
	}
 
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public String getImagen() {
		return imagen;
	}
 
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public Integer getCalificacion() {
		return calificacion;
	}
 
	public void setCalificacion(Integer calificacion) {
		this.calificacion = calificacion;
	}
	public Integer getNumCalificaciones() {
		return numCalificaciones;
	}
 
	public void setNumCalificaciones(Integer numcalificaciones) {
		this.numCalificaciones = numcalificaciones;
	}
	
}