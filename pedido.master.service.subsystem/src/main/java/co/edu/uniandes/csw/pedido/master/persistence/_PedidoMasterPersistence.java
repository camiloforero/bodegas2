package co.edu.uniandes.csw.pedido.master.persistence;
import co.edu.uniandes.csw.orden.logic.dto.OrdenDTO;
import co.edu.uniandes.csw.pedido.master.persistence.entity.PedidoOrdenEntity;
import co.edu.uniandes.csw.orden.persistence.converter.OrdenConverter;
import co.edu.uniandes.csw.pedido.logic.dto.PedidoDTO;
import co.edu.uniandes.csw.pedido.master.logic.dto.PedidoMasterDTO;
import co.edu.uniandes.csw.pedido.master.persistence.api._IPedidoMasterPersistence;
import co.edu.uniandes.csw.pedido.persistence.api.IPedidoPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class _PedidoMasterPersistence implements _IPedidoMasterPersistence {

    @PersistenceContext(unitName = "PedidoMasterPU")
    protected EntityManager entityManager;
    
    @Inject
    protected IPedidoPersistence pedidoPersistence;  

    public PedidoMasterDTO getPedido(Long pedidoId) {
        PedidoMasterDTO pedidoMasterDTO = new PedidoMasterDTO();
        PedidoDTO pedido = pedidoPersistence.getPedido(pedidoId);
        pedidoMasterDTO.setPedidoEntity(pedido);
        pedidoMasterDTO.setListOrden(getOrdenListForPedido(pedidoId));
        return pedidoMasterDTO;
    }

    public PedidoOrdenEntity createPedidoOrden(PedidoOrdenEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    public void deletePedidoOrden(Long pedidoId, Long ordenId) {
        Query q = entityManager.createNamedQuery("PedidoOrdenEntity.deletePedidoOrden");
        q.setParameter("pedidoId", pedidoId);
        q.setParameter("ordenId", ordenId);
        q.executeUpdate();
    }

    public List<OrdenDTO> getOrdenListForPedido(Long pedidoId) {
        ArrayList<OrdenDTO> resp = new ArrayList<OrdenDTO>();
        Query q = entityManager.createNamedQuery("PedidoOrdenEntity.getOrdenListForPedido");
        q.setParameter("pedidoId", pedidoId);
        List<PedidoOrdenEntity> qResult =  q.getResultList();
        for (PedidoOrdenEntity pedidoOrdenEntity : qResult) { 
            if(pedidoOrdenEntity.getOrdenEntity()==null){
                entityManager.refresh(pedidoOrdenEntity);
            }
            resp.add(OrdenConverter.entity2PersistenceDTO(pedidoOrdenEntity.getOrdenEntity()));
        }
        return resp;
    }

}
