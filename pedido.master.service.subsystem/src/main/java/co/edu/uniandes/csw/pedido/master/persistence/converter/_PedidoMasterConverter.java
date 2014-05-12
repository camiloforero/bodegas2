package co.edu.uniandes.csw.pedido.master.persistence.converter;
import co.edu.uniandes.csw.pedido.master.persistence.entity.PedidoOrdenEntity;
import co.edu.uniandes.csw.orden.logic.dto.OrdenDTO;
import co.edu.uniandes.csw.orden.persistence.converter.OrdenConverter;
import co.edu.uniandes.csw.pedido.logic.dto.PedidoDTO;
import co.edu.uniandes.csw.pedido.master.logic.dto.PedidoMasterDTO;
import co.edu.uniandes.csw.pedido.persistence.converter.PedidoConverter;
import co.edu.uniandes.csw.pedido.persistence.entity.PedidoEntity;
import java.util.ArrayList;
import java.util.List;

public abstract class _PedidoMasterConverter {

    public static PedidoMasterDTO entity2PersistenceDTO(PedidoEntity pedidoEntity 
    ,List<PedidoOrdenEntity> pedidoOrdenEntity 
    ) {
        PedidoDTO pedidoDTO = PedidoConverter.entity2PersistenceDTO(pedidoEntity);
        ArrayList<OrdenDTO> ordenEntities = new ArrayList<OrdenDTO>(pedidoOrdenEntity.size());
        for (PedidoOrdenEntity pedidoOrden : pedidoOrdenEntity) {
            ordenEntities.add(OrdenConverter.entity2PersistenceDTO(pedidoOrden.getOrdenEntity()));
        }
        PedidoMasterDTO respDTO  = new PedidoMasterDTO();
        respDTO.setPedidoEntity(pedidoDTO);
        respDTO.setListOrden(ordenEntities);
        return respDTO;
    }

}