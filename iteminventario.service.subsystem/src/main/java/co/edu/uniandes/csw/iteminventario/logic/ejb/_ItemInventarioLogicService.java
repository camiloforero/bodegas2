
package co.edu.uniandes.csw.iteminventario.logic.ejb;
import java.util.List;
import javax.inject.Inject;

import co.edu.uniandes.csw.iteminventario.logic.dto.ItemInventarioDTO;
import co.edu.uniandes.csw.iteminventario.logic.api._IItemInventarioLogicService;
import co.edu.uniandes.csw.iteminventario.persistence.api.IItemInventarioPersistence;

public abstract class _ItemInventarioLogicService implements _IItemInventarioLogicService {

	@Inject
	protected IItemInventarioPersistence persistance;

	public ItemInventarioDTO createItemInventario(ItemInventarioDTO itemInventario){
		return persistance.createItemInventario( itemInventario); 
    }

	public List<ItemInventarioDTO> getItemInventarios(){
		return persistance.getItemInventarios(); 
	}

	public ItemInventarioDTO getItemInventario(Long id){
		return persistance.getItemInventario(id); 
	}

	public void deleteItemInventario(Long id){
	    persistance.deleteItemInventario(id); 
	}

	public void updateItemInventario(ItemInventarioDTO itemInventario){
	    persistance.updateItemInventario(itemInventario); 
	}	
}