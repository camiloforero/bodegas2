
package co.edu.uniandes.csw.pedido.persistence.api;

import java.util.List; 

import co.edu.uniandes.csw.pedido.logic.dto.PedidoDTO;

public interface _IPedidoPersistence {

	public PedidoDTO createPedido(PedidoDTO detail);
	public List<PedidoDTO> getPedidos();
	public PedidoDTO getPedido(Long id);
	public void deletePedido(Long id);
	public void updatePedido(PedidoDTO detail);
	
}