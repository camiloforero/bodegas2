
package co.edu.uniandes.csw.iteminventario.logic.api;

import java.util.List; 

import co.edu.uniandes.csw.iteminventario.logic.dto.ItemInventarioDTO;

public interface _IItemInventarioLogicService {

	public ItemInventarioDTO createItemInventario(ItemInventarioDTO detail);
	public List<ItemInventarioDTO> getItemInventarios();
	public ItemInventarioDTO getItemInventario(Long id);
	public void deleteItemInventario(Long id);
	public void updateItemInventario(ItemInventarioDTO detail);
	
}