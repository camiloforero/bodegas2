package co.edu.uniandes.csw.bodega.master.persistence;
import co.edu.uniandes.csw.iteminventario.logic.dto.ItemInventarioDTO;
import co.edu.uniandes.csw.bodega.master.persistence.entity.BodegaItemInventarioEntity;
import co.edu.uniandes.csw.iteminventario.persistence.converter.ItemInventarioConverter;
import co.edu.uniandes.csw.bodega.logic.dto.BodegaDTO;
import co.edu.uniandes.csw.bodega.master.logic.dto.BodegaMasterDTO;
import co.edu.uniandes.csw.bodega.master.persistence.api._IBodegaMasterPersistence;
import co.edu.uniandes.csw.bodega.persistence.api.IBodegaPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class _BodegaMasterPersistence implements _IBodegaMasterPersistence {

    @PersistenceContext(unitName = "BodegaMasterPU")
    protected EntityManager entityManager;
    
    @Inject
    protected IBodegaPersistence bodegaPersistence;  

    public BodegaMasterDTO getBodega(Long bodegaId) {
        BodegaMasterDTO bodegaMasterDTO = new BodegaMasterDTO();
        BodegaDTO bodega = bodegaPersistence.getBodega(bodegaId);
        bodegaMasterDTO.setBodegaEntity(bodega);
        bodegaMasterDTO.setListItemInventario(getItemInventarioListForBodega(bodegaId));
        return bodegaMasterDTO;
    }

    public BodegaItemInventarioEntity createBodegaItemInventario(BodegaItemInventarioEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    public void deleteBodegaItemInventario(Long bodegaId, Long itemInventarioId) {
        Query q = entityManager.createNamedQuery("BodegaItemInventarioEntity.deleteBodegaItemInventario");
        q.setParameter("bodegaId", bodegaId);
        q.setParameter("itemInventarioId", itemInventarioId);
        q.executeUpdate();
    }

    public List<ItemInventarioDTO> getItemInventarioListForBodega(Long bodegaId) {
        ArrayList<ItemInventarioDTO> resp = new ArrayList<ItemInventarioDTO>();
        Query q = entityManager.createNamedQuery("BodegaItemInventarioEntity.getItemInventarioListForBodega");
        q.setParameter("bodegaId", bodegaId);
        List<BodegaItemInventarioEntity> qResult =  q.getResultList();
        for (BodegaItemInventarioEntity bodegaItemInventarioEntity : qResult) { 
            if(bodegaItemInventarioEntity.getItemInventarioEntity()==null){
                entityManager.refresh(bodegaItemInventarioEntity);
            }
            resp.add(ItemInventarioConverter.entity2PersistenceDTO(bodegaItemInventarioEntity.getItemInventarioEntity()));
        }
        return resp;
    }

}
