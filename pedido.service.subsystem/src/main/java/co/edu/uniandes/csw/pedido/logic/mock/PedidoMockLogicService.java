
package co.edu.uniandes.csw.pedido.logic.mock;
import javax.enterprise.inject.Alternative;
import javax.inject.Singleton;

import co.edu.uniandes.csw.pedido.logic.api.IPedidoLogicService;

@Alternative
@Singleton
public class PedidoMockLogicService extends _PedidoMockLogicService implements IPedidoLogicService {
	
}