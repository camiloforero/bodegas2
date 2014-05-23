
package co.edu.uniandes.csw.producto.persistence;

import co.edu.uniandes.csw.producto.logic.dto.ProductoDTO;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

import co.edu.uniandes.csw.producto.persistence.api.IProductoPersistence;
import co.edu.uniandes.csw.producto.persistence.converter.ProductoConverter;
import java.util.List;
import java.util.Properties;
import javax.persistence.Query;
import javax.ejb.LocalBean;


@Default
@Stateless 
@LocalBean
public class ProductoPersistence extends _ProductoPersistence  implements IProductoPersistence {

    @SuppressWarnings("unchecked")
        public List<ProductoDTO> getProductos(String nombre) {
                Query q = entityManager.createQuery("select u from ProductoEntity u where name = nombre");
                return ProductoConverter.entity2PersistenceDTOList(q.getResultList());
        }        
    
}