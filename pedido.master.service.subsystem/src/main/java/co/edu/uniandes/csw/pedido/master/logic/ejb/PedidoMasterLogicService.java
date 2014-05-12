package co.edu.uniandes.csw.pedido.master.logic.ejb;

import co.edu.uniandes.csw.pedido.master.logic.api.IPedidoMasterLogicService;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

@Default
@Stateless
@LocalBean
public class PedidoMasterLogicService extends _PedidoMasterLogicService implements IPedidoMasterLogicService {

}