
package co.edu.uniandes.csw.orden.logic.ejb;
import java.util.List;
import javax.inject.Inject;

import co.edu.uniandes.csw.orden.logic.dto.OrdenDTO;
import co.edu.uniandes.csw.orden.logic.api._IOrdenLogicService;
import co.edu.uniandes.csw.orden.persistence.api.IOrdenPersistence;

public abstract class _OrdenLogicService implements _IOrdenLogicService {

	@Inject
	protected IOrdenPersistence persistance;

	public OrdenDTO createOrden(OrdenDTO orden){
		return persistance.createOrden( orden); 
    }

	public List<OrdenDTO> getOrdens(){
		return persistance.getOrdens(); 
	}

	public OrdenDTO getOrden(Long id){
		return persistance.getOrden(id); 
	}

	public void deleteOrden(Long id){
	    persistance.deleteOrden(id); 
	}

	public void updateOrden(OrdenDTO orden){
	    persistance.updateOrden(orden); 
	}	
}