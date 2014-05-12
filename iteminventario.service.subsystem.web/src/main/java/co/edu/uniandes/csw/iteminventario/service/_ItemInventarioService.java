package co.edu.uniandes.csw.iteminventario.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.inject.Inject;

import co.edu.uniandes.csw.iteminventario.logic.api.IItemInventarioLogicService;
import co.edu.uniandes.csw.iteminventario.logic.dto.ItemInventarioDTO;


public abstract class _ItemInventarioService {

	@Inject
	protected IItemInventarioLogicService itemInventarioLogicService;
	
	@POST
	public ItemInventarioDTO createItemInventario(ItemInventarioDTO itemInventario){
		return itemInventarioLogicService.createItemInventario(itemInventario);
	}
	
	@DELETE
	@Path("{id}")
	public void deleteItemInventario(@PathParam("id") Long id){
		itemInventarioLogicService.deleteItemInventario(id);
	}
	
	@GET
	public List<ItemInventarioDTO> getItemInventarios(){
		return itemInventarioLogicService.getItemInventarios();
	}
	
	@GET
	@Path("{id}")
	public ItemInventarioDTO getItemInventario(@PathParam("id") Long id){
		return itemInventarioLogicService.getItemInventario(id);
	}
	
	@PUT
    @Path("{id}")
	public void updateItemInventario(@PathParam("id") Long id, ItemInventarioDTO itemInventario){
		itemInventarioLogicService.updateItemInventario(itemInventario);
	}
	
}