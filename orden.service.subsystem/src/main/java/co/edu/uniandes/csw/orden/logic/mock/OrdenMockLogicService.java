
package co.edu.uniandes.csw.orden.logic.mock;
import javax.enterprise.inject.Alternative;
import javax.inject.Singleton;

import co.edu.uniandes.csw.orden.logic.api.IOrdenLogicService;

@Alternative
@Singleton
public class OrdenMockLogicService extends _OrdenMockLogicService implements IOrdenLogicService {
	
}