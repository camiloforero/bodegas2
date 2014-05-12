
package co.edu.uniandes.csw.log.persistence;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

import co.edu.uniandes.csw.log.persistence.api.ILogPersistence;
import javax.ejb.LocalBean;

@Default
@Stateless 
@LocalBean
public class LogPersistence extends _LogPersistence  implements ILogPersistence {

}