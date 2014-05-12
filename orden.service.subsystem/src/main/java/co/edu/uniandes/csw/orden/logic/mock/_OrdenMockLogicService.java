
package co.edu.uniandes.csw.orden.logic.mock;
import java.util.ArrayList;
import java.util.List;

import co.edu.uniandes.csw.orden.logic.dto.OrdenDTO;
import co.edu.uniandes.csw.orden.logic.api._IOrdenLogicService;

public abstract class _OrdenMockLogicService implements _IOrdenLogicService {

	private Long id= new Long(1);
	protected List<OrdenDTO> data=new ArrayList<OrdenDTO>();

	public OrdenDTO createOrden(OrdenDTO orden){
		id++;
		orden.setId(id);
		return orden;
    }

	public List<OrdenDTO> getOrdens(){
		return data; 
	}

	public OrdenDTO getOrden(Long id){
		for(OrdenDTO d:data){
			if(d.getId().equals(id)){
				return d;
			}
		}
		return null;
	}

	public void deleteOrden(Long id){
	    OrdenDTO delete=null;
		for(OrdenDTO d:data){
			if(d.getId().equals(id)){
				delete=d;
			}
		}
		if(delete!=null){
			data.remove(delete);
		} 
	}

	public void updateOrden(OrdenDTO orden){
	    OrdenDTO delete=null;
		for(OrdenDTO d:data){
			if(d.getId().equals(id)){
				delete=d;
			}
		}
		if(delete!=null){
			data.remove(delete);
			data.add(orden);
		} 
	}	
}