
package co.edu.uniandes.csw.log.logic.ejb;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless; 
import javax.inject.Inject;

import javax.enterprise.inject.Default;

import co.edu.uniandes.csw.log.logic.api.ILogLogicService;

@Default
@Stateless
@LocalBean
public class LogLogicService extends _LogLogicService implements ILogLogicService {

}