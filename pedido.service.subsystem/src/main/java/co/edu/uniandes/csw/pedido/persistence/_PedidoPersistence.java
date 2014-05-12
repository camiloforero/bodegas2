
package co.edu.uniandes.csw.pedido.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.edu.uniandes.csw.pedido.logic.dto.PedidoDTO;
import co.edu.uniandes.csw.pedido.persistence.api._IPedidoPersistence;
import co.edu.uniandes.csw.pedido.persistence.converter.PedidoConverter;
import co.edu.uniandes.csw.pedido.persistence.entity.PedidoEntity;

public abstract class _PedidoPersistence implements _IPedidoPersistence {

	@PersistenceContext(unitName="PedidoPU")
	protected EntityManager entityManager;
	
	public PedidoDTO createPedido(PedidoDTO pedido) {
		PedidoEntity entity=PedidoConverter.persistenceDTO2Entity(pedido);
		entityManager.persist(entity);
		return PedidoConverter.entity2PersistenceDTO(entity);
	}

	@SuppressWarnings("unchecked")
	public List<PedidoDTO> getPedidos() {
		Query q = entityManager.createQuery("select u from PedidoEntity u");
		return PedidoConverter.entity2PersistenceDTOList(q.getResultList());
	}

	public PedidoDTO getPedido(Long id) {
		return PedidoConverter.entity2PersistenceDTO(entityManager.find(PedidoEntity.class, id));
	}

	public void deletePedido(Long id) {
		PedidoEntity entity=entityManager.find(PedidoEntity.class, id);
		entityManager.remove(entity);
	}

	public void updatePedido(PedidoDTO detail) {
		PedidoEntity entity=entityManager.merge(PedidoConverter.persistenceDTO2Entity(detail));
		PedidoConverter.entity2PersistenceDTO(entity);
	}

}