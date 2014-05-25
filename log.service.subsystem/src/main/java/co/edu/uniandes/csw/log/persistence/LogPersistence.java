
package co.edu.uniandes.csw.log.persistence;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

import co.edu.uniandes.csw.log.persistence.api.ILogPersistence;
import javax.ejb.LocalBean;
import javax.persistence.Query;

@Default
@Stateless 
@LocalBean
public class LogPersistence extends _LogPersistence  implements ILogPersistence {

    public long getMaxID() {
        Query q = entityManager.createQuery("SELECT MAX(u.id) from LogEntity u");
        Long resultado = (Long) q.getSingleResult();
        return resultado == null? -1l : resultado;
    }

}