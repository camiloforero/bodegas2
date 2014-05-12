
package co.edu.uniandes.csw.bodega.persistence.api;

import java.util.List; 

import co.edu.uniandes.csw.bodega.logic.dto.BodegaDTO;

public interface _IBodegaPersistence {

	public BodegaDTO createBodega(BodegaDTO detail);
	public List<BodegaDTO> getBodegas();
	public BodegaDTO getBodega(Long id);
	public void deleteBodega(Long id);
	public void updateBodega(BodegaDTO detail);
	
}