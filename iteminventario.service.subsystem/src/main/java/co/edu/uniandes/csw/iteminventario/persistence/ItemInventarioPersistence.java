
package co.edu.uniandes.csw.iteminventario.persistence;

import co.edu.uniandes.csw.iteminventario.logic.dto.ItemInventarioDTO;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

import co.edu.uniandes.csw.iteminventario.persistence.api.IItemInventarioPersistence;
import co.edu.uniandes.csw.iteminventario.persistence.converter.ItemInventarioConverter;
import java.util.List;
import javax.ejb.LocalBean;
import javax.persistence.Query;

@Default
@Stateless 
@LocalBean
public class ItemInventarioPersistence extends _ItemInventarioPersistence  implements IItemInventarioPersistence {
    
    @SuppressWarnings("unchecked")
    public List<ItemInventarioDTO> getItemInventariosProducto(long idProducto) {
        
	Query q = entityManager.createQuery("select u from ItemInventarioEntity u where u.productoId = " + idProducto);
	return ItemInventarioConverter.entity2PersistenceDTOList(q.getResultList());
    }
    
    public long getMaxID() {
        Query q = entityManager.createQuery("SELECT MAX(u.id) from ItemInventarioEntity u");
        Long resultado = (Long) q.getSingleResult();
        return resultado == null? -1l : resultado;
    }

}