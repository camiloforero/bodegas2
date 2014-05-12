
package co.edu.uniandes.csw.orden.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.edu.uniandes.csw.orden.logic.dto.OrdenDTO;
import co.edu.uniandes.csw.orden.persistence.api._IOrdenPersistence;
import co.edu.uniandes.csw.orden.persistence.converter.OrdenConverter;
import co.edu.uniandes.csw.orden.persistence.entity.OrdenEntity;

public abstract class _OrdenPersistence implements _IOrdenPersistence {

	@PersistenceContext(unitName="OrdenPU")
	protected EntityManager entityManager;
	
	public OrdenDTO createOrden(OrdenDTO orden) {
		OrdenEntity entity=OrdenConverter.persistenceDTO2Entity(orden);
		entityManager.persist(entity);
		return OrdenConverter.entity2PersistenceDTO(entity);
	}

	@SuppressWarnings("unchecked")
	public List<OrdenDTO> getOrdens() {
		Query q = entityManager.createQuery("select u from OrdenEntity u");
		return OrdenConverter.entity2PersistenceDTOList(q.getResultList());
	}

	public OrdenDTO getOrden(Long id) {
		return OrdenConverter.entity2PersistenceDTO(entityManager.find(OrdenEntity.class, id));
	}

	public void deleteOrden(Long id) {
		OrdenEntity entity=entityManager.find(OrdenEntity.class, id);
		entityManager.remove(entity);
	}

	public void updateOrden(OrdenDTO detail) {
		OrdenEntity entity=entityManager.merge(OrdenConverter.persistenceDTO2Entity(detail));
		OrdenConverter.entity2PersistenceDTO(entity);
	}

}