package co.edu.uniandes.csw.pedido.master.logic.ejb;

import co.edu.uniandes.csw.log.logic.api.ILogLogicService;
import co.edu.uniandes.csw.orden.logic.dto.OrdenDTO;
import co.edu.uniandes.csw.orden.persistence.api.IOrdenPersistence;
import co.edu.uniandes.csw.pedido.logic.dto.PedidoDTO;
import co.edu.uniandes.csw.pedido.master.logic.api._IPedidoMasterLogicService;
import co.edu.uniandes.csw.pedido.master.logic.dto.PedidoMasterDTO;
import co.edu.uniandes.csw.pedido.master.persistence.api.IPedidoMasterPersistence;
import co.edu.uniandes.csw.pedido.master.persistence.entity.PedidoOrdenEntity;
import co.edu.uniandes.csw.pedido.persistence.api.IPedidoPersistence;
import javax.inject.Inject;

public abstract class _PedidoMasterLogicService implements _IPedidoMasterLogicService {

    @Inject
    protected IPedidoPersistence pedidoPersistance;
    @Inject
    protected IPedidoMasterPersistence pedidoMasterPersistance;
    @Inject
    protected IOrdenPersistence ordenPersistance;
    
    @Inject
    protected ILogLogicService apiLog;

    public PedidoMasterDTO createMasterPedido(PedidoMasterDTO pedido) {
        PedidoDTO persistedPedidoDTO = pedidoPersistance.createPedido(pedido.getPedidoEntity());
        int cantidadDisponible = apiLog.darCantidadProducto(persistedPedidoDTO.getProductoId());
        System.out.println(cantidadDisponible);
        
        int cantidadPedida = persistedPedidoDTO.getCantidad();
        
        int diferencia = cantidadDisponible - cantidadPedida;
        
        int cantidadPosible = diferencia >= 0 ? cantidadPedida : cantidadDisponible;
        
        //Crea un DTO para guardar una orden de despacho
        OrdenDTO ordenDespacho = new OrdenDTO();
        //Guarda los atributos
        ordenDespacho.setEstado("Por entregar");
        ordenDespacho.setId(ordenPersistance.getMaxID() + 1l);
        ordenDespacho.setName(persistedPedidoDTO.getName() + " - " + "Orden de despacho");
        ordenDespacho.setTipo("Orden de despacho");
        ordenDespacho.setCantidad(cantidadPosible);
        
        //Guarda la orden creada
        OrdenDTO persistedOrdenDTO = ordenPersistance.createOrden(ordenDespacho);
        PedidoOrdenEntity pedidoOrdenEntity = new PedidoOrdenEntity(persistedPedidoDTO.getId(), persistedOrdenDTO.getId());
        pedidoMasterPersistance.createPedidoOrden(pedidoOrdenEntity);
        
        //Crea una orden de producci�n, si aplica
        if(diferencia < 0)
        {
            //Crea un DTO para guardar una orden de despacho
            OrdenDTO ordenProd = new OrdenDTO();
            //Guarda los atributos
            ordenProd.setEstado("Por entregar");
            ordenProd.setId(ordenPersistance.getMaxID() + 1l);
            ordenProd.setName(persistedPedidoDTO.getName() + " - " + "Orden de reaprovisionamiento");
            ordenProd.setTipo("Orden de reaprovisionamiento");
            ordenProd.setCantidad(diferencia);

            //Guarda la orden creada
            persistedOrdenDTO = ordenPersistance.createOrden(ordenProd);
            pedidoOrdenEntity = new PedidoOrdenEntity(persistedPedidoDTO.getId(), persistedOrdenDTO.getId());
            pedidoMasterPersistance.createPedidoOrden(pedidoOrdenEntity);
        }
        
        
        return pedido;
    }

    public PedidoMasterDTO getMasterPedido(Long id) {
        return pedidoMasterPersistance.getPedido(id);
    }

    public void deleteMasterPedido(Long id) {
        pedidoPersistance.deletePedido(id);
    }

    public void updateMasterPedido(PedidoMasterDTO pedido) {
        pedidoPersistance.updatePedido(pedido.getPedidoEntity());

        //---- FOR RELATIONSHIP
        // persist new orden
        if (pedido.getCreateOrden() != null) {
            for (OrdenDTO ordenDTO : pedido.getCreateOrden()) {
                OrdenDTO persistedOrdenDTO = ordenPersistance.createOrden(ordenDTO);
                PedidoOrdenEntity pedidoOrdenEntity = new PedidoOrdenEntity(pedido.getPedidoEntity().getId(), persistedOrdenDTO.getId());
                pedidoMasterPersistance.createPedidoOrden(pedidoOrdenEntity);
            }
        }
        // update orden
        if (pedido.getUpdateOrden() != null) {
            for (OrdenDTO ordenDTO : pedido.getUpdateOrden()) {
                ordenPersistance.updateOrden(ordenDTO);
            }
        }
        // delete orden
        if (pedido.getDeleteOrden() != null) {
            for (OrdenDTO ordenDTO : pedido.getDeleteOrden()) {
                pedidoMasterPersistance.deletePedidoOrden(pedido.getPedidoEntity().getId(), ordenDTO.getId());
                ordenPersistance.deleteOrden(ordenDTO.getId());
            }
        }
    }
}
