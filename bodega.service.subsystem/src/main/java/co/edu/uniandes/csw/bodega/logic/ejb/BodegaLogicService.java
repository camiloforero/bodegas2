
package co.edu.uniandes.csw.bodega.logic.ejb;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless; 
import javax.inject.Inject;

import javax.enterprise.inject.Default;

import co.edu.uniandes.csw.bodega.logic.api.IBodegaLogicService;

@Default
@Stateless
@LocalBean
public class BodegaLogicService extends _BodegaLogicService implements IBodegaLogicService {

}