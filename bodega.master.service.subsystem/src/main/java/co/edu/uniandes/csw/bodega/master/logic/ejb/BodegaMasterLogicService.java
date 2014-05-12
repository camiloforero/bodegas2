package co.edu.uniandes.csw.bodega.master.logic.ejb;

import co.edu.uniandes.csw.bodega.master.logic.api.IBodegaMasterLogicService;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

@Default
@Stateless
@LocalBean
public class BodegaMasterLogicService extends _BodegaMasterLogicService implements IBodegaMasterLogicService {

}