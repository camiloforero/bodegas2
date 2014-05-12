
package co.edu.uniandes.csw.orden.logic.api;

import java.util.List; 

import co.edu.uniandes.csw.orden.logic.dto.OrdenDTO;

public interface _IOrdenLogicService {

	public OrdenDTO createOrden(OrdenDTO detail);
	public List<OrdenDTO> getOrdens();
	public OrdenDTO getOrden(Long id);
	public void deleteOrden(Long id);
	public void updateOrden(OrdenDTO detail);
	
}