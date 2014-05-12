
package co.edu.uniandes.csw.pedido.persistence;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

import co.edu.uniandes.csw.pedido.persistence.api.IPedidoPersistence;
import javax.ejb.LocalBean;

@Default
@Stateless 
@LocalBean
public class PedidoPersistence extends _PedidoPersistence  implements IPedidoPersistence {

}