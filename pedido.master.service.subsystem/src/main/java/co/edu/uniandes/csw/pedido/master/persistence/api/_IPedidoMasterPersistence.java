package co.edu.uniandes.csw.pedido.master.persistence.api;

import co.edu.uniandes.csw.pedido.master.persistence.entity.PedidoOrdenEntity;
import co.edu.uniandes.csw.orden.logic.dto.OrdenDTO;
import co.edu.uniandes.csw.pedido.master.logic.dto.PedidoMasterDTO;
import java.util.List;

public interface _IPedidoMasterPersistence {

    public PedidoOrdenEntity createPedidoOrden(PedidoOrdenEntity entity);

    public void deletePedidoOrden(Long pedidoId, Long ordenId);
    
    public List<OrdenDTO> getOrdenListForPedido(Long pedidoId);
    
    public PedidoMasterDTO getPedido(Long pedidoId);

}
