
package co.edu.uniandes.csw.log.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.edu.uniandes.csw.log.logic.dto.LogDTO;
import co.edu.uniandes.csw.log.persistence.api._ILogPersistence;
import co.edu.uniandes.csw.log.persistence.converter.LogConverter;
import co.edu.uniandes.csw.log.persistence.entity.LogEntity;

public abstract class _LogPersistence implements _ILogPersistence {

	@PersistenceContext(unitName="LogPU")
	protected EntityManager entityManager;
	
	public LogDTO createLog(LogDTO log) {
		LogEntity entity=LogConverter.persistenceDTO2Entity(log);
		entityManager.persist(entity);
		return LogConverter.entity2PersistenceDTO(entity);
	}

	@SuppressWarnings("unchecked")
	public List<LogDTO> getLogs() {
		Query q = entityManager.createQuery("select u from LogEntity u");
		return LogConverter.entity2PersistenceDTOList(q.getResultList());
	}

	public LogDTO getLog(Long id) {
		return LogConverter.entity2PersistenceDTO(entityManager.find(LogEntity.class, id));
	}

	public void deleteLog(Long id) {
		LogEntity entity=entityManager.find(LogEntity.class, id);
		entityManager.remove(entity);
	}

	public void updateLog(LogDTO detail) {
		LogEntity entity=entityManager.merge(LogConverter.persistenceDTO2Entity(detail));
		LogConverter.entity2PersistenceDTO(entity);
	}

}