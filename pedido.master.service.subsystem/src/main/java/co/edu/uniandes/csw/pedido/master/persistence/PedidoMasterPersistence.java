package co.edu.uniandes.csw.pedido.master.persistence;

import javax.ejb.Stateless;

import co.edu.uniandes.csw.pedido.master.persistence.api.IPedidoMasterPersistence;
import javax.ejb.LocalBean;
import javax.enterprise.inject.Default;

@Default
@Stateless 
@LocalBean
public class PedidoMasterPersistence extends _PedidoMasterPersistence  implements IPedidoMasterPersistence {

}