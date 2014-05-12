
package co.edu.uniandes.csw.bodega.logic.mock;
import javax.enterprise.inject.Alternative;
import javax.inject.Singleton;

import co.edu.uniandes.csw.bodega.logic.api.IBodegaLogicService;

@Alternative
@Singleton
public class BodegaMockLogicService extends _BodegaMockLogicService implements IBodegaLogicService {
	
}