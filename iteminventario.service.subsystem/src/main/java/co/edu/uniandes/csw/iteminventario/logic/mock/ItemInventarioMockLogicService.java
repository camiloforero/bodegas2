
package co.edu.uniandes.csw.iteminventario.logic.mock;
import javax.enterprise.inject.Alternative;
import javax.inject.Singleton;

import co.edu.uniandes.csw.iteminventario.logic.api.IItemInventarioLogicService;

@Alternative
@Singleton
public class ItemInventarioMockLogicService extends _ItemInventarioMockLogicService implements IItemInventarioLogicService {
	
}