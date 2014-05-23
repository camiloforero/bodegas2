package co.edu.uniandes.csw.orden.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.inject.Inject;

import co.edu.uniandes.csw.orden.logic.api.IOrdenLogicService;
import co.edu.uniandes.csw.orden.logic.dto.OrdenDTO;


public abstract class _OrdenService {

	@Inject
	protected IOrdenLogicService ordenLogicService;
	
	@POST
	public OrdenDTO createOrden(OrdenDTO orden){
		return ordenLogicService.createOrden(orden);
	}
	
	@DELETE
	@Path("{id}")
	public void deleteOrden(@PathParam("id") Long id){
		ordenLogicService.deleteOrden(id);
	}
	
	@GET
	public List<OrdenDTO> getOrdens(){
		return ordenLogicService.getOrdens();
	}
	
	@GET
	@Path("{id}")
	public OrdenDTO getOrden(@PathParam("id") Long id){
		return ordenLogicService.getOrden(id);
	}
	
	@PUT
    @Path("{id}")
	public void updateOrden(@PathParam("id") Long id, OrdenDTO orden){
		ordenLogicService.updateOrden(orden);
	}
        
        @GET
	@Path("{id}/satisfacer")
	public boolean satisfacer(@PathParam("id") Long id){
            System.out.println("Esta llegando hasta el @GET del service");
		return ordenLogicService.satisfacer(id);
	}
	
}