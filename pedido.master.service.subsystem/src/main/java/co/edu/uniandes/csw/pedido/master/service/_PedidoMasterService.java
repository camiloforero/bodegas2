package co.edu.uniandes.csw.pedido.master.service;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.inject.Inject;

import co.edu.uniandes.csw.pedido.master.logic.api.IPedidoMasterLogicService;
import co.edu.uniandes.csw.pedido.master.logic.dto.PedidoMasterDTO;

public abstract class _PedidoMasterService {

    @Inject
    protected IPedidoMasterLogicService pedidoLogicService;

    @POST
    public PedidoMasterDTO createPedido(PedidoMasterDTO pedido) {
        return pedidoLogicService.createMasterPedido(pedido);
    }

    @DELETE
    @Path("{id}")
    public void deletePedido(@PathParam("id") Long id) {
        pedidoLogicService.deleteMasterPedido(id);
    }
    
    @GET
    @Path("{id}")
    public PedidoMasterDTO getPedido(@PathParam("id") Long id) {
        return pedidoLogicService.getMasterPedido(id);
    }

    @PUT
    @Path("{id}")
    public void updatePedido(@PathParam("id") Long id, PedidoMasterDTO pedido) {
        pedidoLogicService.updateMasterPedido(pedido);
    }

}
