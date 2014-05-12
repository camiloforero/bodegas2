package co.edu.uniandes.csw.pedido.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.inject.Inject;

import co.edu.uniandes.csw.pedido.logic.api.IPedidoLogicService;
import co.edu.uniandes.csw.pedido.logic.dto.PedidoDTO;


public abstract class _PedidoService {

	@Inject
	protected IPedidoLogicService pedidoLogicService;
	
	@POST
	public PedidoDTO createPedido(PedidoDTO pedido){
		return pedidoLogicService.createPedido(pedido);
	}
	
	@DELETE
	@Path("{id}")
	public void deletePedido(@PathParam("id") Long id){
		pedidoLogicService.deletePedido(id);
	}
	
	@GET
	public List<PedidoDTO> getPedidos(){
		return pedidoLogicService.getPedidos();
	}
	
	@GET
	@Path("{id}")
	public PedidoDTO getPedido(@PathParam("id") Long id){
		return pedidoLogicService.getPedido(id);
	}
	
	@PUT
    @Path("{id}")
	public void updatePedido(@PathParam("id") Long id, PedidoDTO pedido){
		pedidoLogicService.updatePedido(pedido);
	}
	
}