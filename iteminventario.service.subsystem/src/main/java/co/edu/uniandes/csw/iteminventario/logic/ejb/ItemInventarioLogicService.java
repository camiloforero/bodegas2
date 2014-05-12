
package co.edu.uniandes.csw.iteminventario.logic.ejb;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless; 
import javax.inject.Inject;

import javax.enterprise.inject.Default;

import co.edu.uniandes.csw.iteminventario.logic.api.IItemInventarioLogicService;

@Default
@Stateless
@LocalBean
public class ItemInventarioLogicService extends _ItemInventarioLogicService implements IItemInventarioLogicService {

}