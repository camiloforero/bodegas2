package co.edu.uniandes.csw.bodega.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.inject.Inject;

import co.edu.uniandes.csw.bodega.logic.api.IBodegaLogicService;
import co.edu.uniandes.csw.bodega.logic.dto.BodegaDTO;


public abstract class _BodegaService {

	@Inject
	protected IBodegaLogicService bodegaLogicService;
	
	@POST
	public BodegaDTO createBodega(BodegaDTO bodega){
		return bodegaLogicService.createBodega(bodega);
	}
	
	@DELETE
	@Path("{id}")
	public void deleteBodega(@PathParam("id") Long id){
		bodegaLogicService.deleteBodega(id);
	}
	
	@GET
	public List<BodegaDTO> getBodegas(){
		return bodegaLogicService.getBodegas();
	}
	
	@GET
	@Path("{id}")
	public BodegaDTO getBodega(@PathParam("id") Long id){
		return bodegaLogicService.getBodega(id);
	}
	
	@PUT
    @Path("{id}")
	public void updateBodega(@PathParam("id") Long id, BodegaDTO bodega){
		bodegaLogicService.updateBodega(bodega);
	}
	
}