
package co.edu.uniandes.csw.iteminventario.logic.mock;
import java.util.ArrayList;
import java.util.List;

import co.edu.uniandes.csw.iteminventario.logic.dto.ItemInventarioDTO;
import co.edu.uniandes.csw.iteminventario.logic.api._IItemInventarioLogicService;

public abstract class _ItemInventarioMockLogicService implements _IItemInventarioLogicService {

	private Long id= new Long(1);
	protected List<ItemInventarioDTO> data=new ArrayList<ItemInventarioDTO>();

	public ItemInventarioDTO createItemInventario(ItemInventarioDTO itemInventario){
		id++;
		itemInventario.setId(id);
		return itemInventario;
    }

	public List<ItemInventarioDTO> getItemInventarios(){
		return data; 
	}

	public ItemInventarioDTO getItemInventario(Long id){
		for(ItemInventarioDTO d:data){
			if(d.getId().equals(id)){
				return d;
			}
		}
		return null;
	}

	public void deleteItemInventario(Long id){
	    ItemInventarioDTO delete=null;
		for(ItemInventarioDTO d:data){
			if(d.getId().equals(id)){
				delete=d;
			}
		}
		if(delete!=null){
			data.remove(delete);
		} 
	}

	public void updateItemInventario(ItemInventarioDTO itemInventario){
	    ItemInventarioDTO delete=null;
		for(ItemInventarioDTO d:data){
			if(d.getId().equals(id)){
				delete=d;
			}
		}
		if(delete!=null){
			data.remove(delete);
			data.add(itemInventario);
		} 
	}	
}