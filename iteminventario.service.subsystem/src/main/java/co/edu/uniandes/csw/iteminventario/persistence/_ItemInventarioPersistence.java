
package co.edu.uniandes.csw.iteminventario.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.edu.uniandes.csw.iteminventario.logic.dto.ItemInventarioDTO;
import co.edu.uniandes.csw.iteminventario.persistence.api._IItemInventarioPersistence;
import co.edu.uniandes.csw.iteminventario.persistence.converter.ItemInventarioConverter;
import co.edu.uniandes.csw.iteminventario.persistence.entity.ItemInventarioEntity;

public abstract class _ItemInventarioPersistence implements _IItemInventarioPersistence {

	@PersistenceContext(unitName="ItemInventarioPU")
	protected EntityManager entityManager;
	
	public ItemInventarioDTO createItemInventario(ItemInventarioDTO itemInventario) {
		ItemInventarioEntity entity=ItemInventarioConverter.persistenceDTO2Entity(itemInventario);
		entityManager.persist(entity);
		return ItemInventarioConverter.entity2PersistenceDTO(entity);
	}

	@SuppressWarnings("unchecked")
	public List<ItemInventarioDTO> getItemInventarios() {
		Query q = entityManager.createQuery("select u from ItemInventarioEntity u");
		return ItemInventarioConverter.entity2PersistenceDTOList(q.getResultList());
	}

	public ItemInventarioDTO getItemInventario(Long id) {
		return ItemInventarioConverter.entity2PersistenceDTO(entityManager.find(ItemInventarioEntity.class, id));
	}

	public void deleteItemInventario(Long id) {
            System.out.println(id);
		ItemInventarioEntity entity=entityManager.find(ItemInventarioEntity.class, id);
                System.out.println(entity);
		entityManager.remove(entity);
	}

	public void updateItemInventario(ItemInventarioDTO detail) {
		ItemInventarioEntity entity=entityManager.merge(ItemInventarioConverter.persistenceDTO2Entity(detail));
		ItemInventarioConverter.entity2PersistenceDTO(entity);
	}

}