
package co.edu.uniandes.csw.pedido.logic.ejb;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless; 
import javax.inject.Inject;

import javax.enterprise.inject.Default;

import co.edu.uniandes.csw.pedido.logic.api.IPedidoLogicService;

@Default
@Stateless
@LocalBean
public class PedidoLogicService extends _PedidoLogicService implements IPedidoLogicService {

}