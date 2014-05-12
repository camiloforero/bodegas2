package co.edu.uniandes.csw.pedido.master.logic.ejb;

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

    public PedidoMasterDTO createMasterPedido(PedidoMasterDTO pedido) {
        PedidoDTO persistedPedidoDTO = pedidoPersistance.createPedido(pedido.getPedidoEntity());
        if (pedido.getCreateOrden() != null) {
            for (OrdenDTO ordenDTO : pedido.getCreateOrden()) {
                OrdenDTO persistedOrdenDTO = ordenPersistance.createOrden(ordenDTO);
                PedidoOrdenEntity pedidoOrdenEntity = new PedidoOrdenEntity(persistedPedidoDTO.getId(), persistedOrdenDTO.getId());
                pedidoMasterPersistance.createPedidoOrden(pedidoOrdenEntity);
            }
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
