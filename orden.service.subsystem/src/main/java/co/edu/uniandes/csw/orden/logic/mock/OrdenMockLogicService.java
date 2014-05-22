
package co.edu.uniandes.csw.orden.logic.mock;
import javax.enterprise.inject.Alternative;
import javax.inject.Singleton;

import co.edu.uniandes.csw.orden.logic.api.IOrdenLogicService;

@Alternative
@Singleton
public class OrdenMockLogicService extends _OrdenMockLogicService implements IOrdenLogicService {

    public boolean satisfacer(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
	
}