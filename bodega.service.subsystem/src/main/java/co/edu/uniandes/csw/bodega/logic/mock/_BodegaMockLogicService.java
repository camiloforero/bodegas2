
package co.edu.uniandes.csw.bodega.logic.mock;
import java.util.ArrayList;
import java.util.List;

import co.edu.uniandes.csw.bodega.logic.dto.BodegaDTO;
import co.edu.uniandes.csw.bodega.logic.api._IBodegaLogicService;

public abstract class _BodegaMockLogicService implements _IBodegaLogicService {

	private Long id= new Long(1);
	protected List<BodegaDTO> data=new ArrayList<BodegaDTO>();

	public BodegaDTO createBodega(BodegaDTO bodega){
		id++;
		bodega.setId(id);
		return bodega;
    }

	public List<BodegaDTO> getBodegas(){
		return data; 
	}

	public BodegaDTO getBodega(Long id){
		for(BodegaDTO d:data){
			if(d.getId().equals(id)){
				return d;
			}
		}
		return null;
	}

	public void deleteBodega(Long id){
	    BodegaDTO delete=null;
		for(BodegaDTO d:data){
			if(d.getId().equals(id)){
				delete=d;
			}
		}
		if(delete!=null){
			data.remove(delete);
		} 
	}

	public void updateBodega(BodegaDTO bodega){
	    BodegaDTO delete=null;
		for(BodegaDTO d:data){
			if(d.getId().equals(id)){
				delete=d;
			}
		}
		if(delete!=null){
			data.remove(delete);
			data.add(bodega);
		} 
	}	
}