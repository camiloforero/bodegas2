
package co.edu.uniandes.csw.orden.persistence;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

import co.edu.uniandes.csw.orden.persistence.api.IOrdenPersistence;
import javax.ejb.LocalBean;
import javax.persistence.Query;

@Default
@Stateless 
@LocalBean
public class OrdenPersistence extends _OrdenPersistence  implements IOrdenPersistence {

    public long getMaxID() {
        Query q = entityManager.createQuery("SELECT MAX(u.id) from OrdenEntity u");
        Long resultado = (Long) q.getSingleResult();
        return resultado == null? -1l : resultado;
    }

}