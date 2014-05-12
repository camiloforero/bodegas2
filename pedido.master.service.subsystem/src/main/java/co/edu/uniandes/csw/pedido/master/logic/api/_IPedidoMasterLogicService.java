 
package co.edu.uniandes.csw.pedido.master.logic.api;

import co.edu.uniandes.csw.pedido.master.logic.dto.PedidoMasterDTO;

public interface _IPedidoMasterLogicService {

	public PedidoMasterDTO createMasterPedido(PedidoMasterDTO detail);
    public void updateMasterPedido(PedidoMasterDTO detail);
	public void deleteMasterPedido(Long id); 
	public PedidoMasterDTO getMasterPedido(Long id);
        
}