
package co.edu.uniandes.csw.bodega.logic.ejb;
import java.util.List;
import javax.inject.Inject;

import co.edu.uniandes.csw.bodega.logic.dto.BodegaDTO;
import co.edu.uniandes.csw.bodega.logic.api._IBodegaLogicService;
import co.edu.uniandes.csw.bodega.persistence.api.IBodegaPersistence;

public abstract class _BodegaLogicService implements _IBodegaLogicService {

	@Inject
	protected IBodegaPersistence persistance;

	public BodegaDTO createBodega(BodegaDTO bodega){
		return persistance.createBodega( bodega); 
    }

	public List<BodegaDTO> getBodegas(){
		return persistance.getBodegas(); 
	}

	public BodegaDTO getBodega(Long id){
		return persistance.getBodega(id); 
	}

	public void deleteBodega(Long id){
	    persistance.deleteBodega(id); 
	}

	public void updateBodega(BodegaDTO bodega){
	    persistance.updateBodega(bodega); 
	}	
}