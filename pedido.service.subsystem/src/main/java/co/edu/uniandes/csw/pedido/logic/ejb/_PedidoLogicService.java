
package co.edu.uniandes.csw.pedido.logic.ejb;
import java.util.List;
import javax.inject.Inject;

import co.edu.uniandes.csw.pedido.logic.dto.PedidoDTO;
import co.edu.uniandes.csw.pedido.logic.api._IPedidoLogicService;
import co.edu.uniandes.csw.pedido.persistence.api.IPedidoPersistence;

public abstract class _PedidoLogicService implements _IPedidoLogicService {

	@Inject
	protected IPedidoPersistence persistance;

	public PedidoDTO createPedido(PedidoDTO pedido){
		return persistance.createPedido( pedido); 
    }

	public List<PedidoDTO> getPedidos(){
		return persistance.getPedidos(); 
	}

	public PedidoDTO getPedido(Long id){
		return persistance.getPedido(id); 
	}

	public void deletePedido(Long id){
	    persistance.deletePedido(id); 
	}

	public void updatePedido(PedidoDTO pedido){
	    persistance.updatePedido(pedido); 
	}	
}