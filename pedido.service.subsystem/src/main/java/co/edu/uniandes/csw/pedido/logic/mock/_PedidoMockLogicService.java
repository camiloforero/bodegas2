
package co.edu.uniandes.csw.pedido.logic.mock;
import java.util.ArrayList;
import java.util.List;

import co.edu.uniandes.csw.pedido.logic.dto.PedidoDTO;
import co.edu.uniandes.csw.pedido.logic.api._IPedidoLogicService;

public abstract class _PedidoMockLogicService implements _IPedidoLogicService {

	private Long id= new Long(1);
	protected List<PedidoDTO> data=new ArrayList<PedidoDTO>();

	public PedidoDTO createPedido(PedidoDTO pedido){
		id++;
		pedido.setId(id);
		return pedido;
    }

	public List<PedidoDTO> getPedidos(){
		return data; 
	}

	public PedidoDTO getPedido(Long id){
		for(PedidoDTO d:data){
			if(d.getId().equals(id)){
				return d;
			}
		}
		return null;
	}

	public void deletePedido(Long id){
	    PedidoDTO delete=null;
		for(PedidoDTO d:data){
			if(d.getId().equals(id)){
				delete=d;
			}
		}
		if(delete!=null){
			data.remove(delete);
		} 
	}

	public void updatePedido(PedidoDTO pedido){
	    PedidoDTO delete=null;
		for(PedidoDTO d:data){
			if(d.getId().equals(id)){
				delete=d;
			}
		}
		if(delete!=null){
			data.remove(delete);
			data.add(pedido);
		} 
	}	
}