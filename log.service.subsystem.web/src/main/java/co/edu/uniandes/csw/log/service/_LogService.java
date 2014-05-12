package co.edu.uniandes.csw.log.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.inject.Inject;

import co.edu.uniandes.csw.log.logic.api.ILogLogicService;
import co.edu.uniandes.csw.log.logic.dto.LogDTO;


public abstract class _LogService {

	@Inject
	protected ILogLogicService logLogicService;
	
	@POST
	public LogDTO createLog(LogDTO log){
		return logLogicService.createLog(log);
	}
	
	@DELETE
	@Path("{id}")
	public void deleteLog(@PathParam("id") Long id){
		logLogicService.deleteLog(id);
	}
	
	@GET
	public List<LogDTO> getLogs(){
		return logLogicService.getLogs();
	}
	
	@GET
	@Path("{id}")
	public LogDTO getLog(@PathParam("id") Long id){
		return logLogicService.getLog(id);
	}
	
	@PUT
    @Path("{id}")
	public void updateLog(@PathParam("id") Long id, LogDTO log){
		logLogicService.updateLog(log);
	}
	
}