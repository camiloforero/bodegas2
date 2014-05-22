package co.edu.uniandes.csw.log.persistence;

import co.edu.uniandes.csw.bodega.master.persistence.entity.BodegaItemInventarioEntity;
import co.edu.uniandes.csw.iteminventario.logic.dto.ItemInventarioDTO;
import co.edu.uniandes.csw.iteminventario.persistence.converter.ItemInventarioConverter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class BodegaEntityPersistence 
{

    @PersistenceContext(unitName = "BodegaMasterPU")
    protected EntityManager entityManager; 
    
    
   public BodegaItemInventarioEntity createBodegaItemInventario(BodegaItemInventarioEntity entity) {
       
        entityManager.persist(entity);
        return entity;
    }
   
   public List<ItemInventarioDTO> getItemInventarioListForBodegaProducto(Long bodegaId, Long productoId) {
        ArrayList<ItemInventarioDTO> resp = new ArrayList<ItemInventarioDTO>();
        Query q = entityManager.createNamedQuery("BodegaItemInventarioEntity.getItemInventarioListForBodegaProducto");
        q.setParameter("bodegaId", bodegaId);
        q.setParameter("productoId", productoId);
        List<BodegaItemInventarioEntity> qResult =  q.getResultList();
        System.out.println(qResult.size());
        for (BodegaItemInventarioEntity bodegaItemInventarioEntity : qResult) { 
            
            resp.add(ItemInventarioConverter.entity2PersistenceDTO(bodegaItemInventarioEntity.getItemInventarioEntity()));
        }
        return resp;
    }

}
