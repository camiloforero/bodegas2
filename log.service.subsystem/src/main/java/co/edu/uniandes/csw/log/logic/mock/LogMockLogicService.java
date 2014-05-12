
package co.edu.uniandes.csw.log.logic.mock;
import javax.enterprise.inject.Alternative;
import javax.inject.Singleton;

import co.edu.uniandes.csw.log.logic.api.ILogLogicService;

@Alternative
@Singleton
public class LogMockLogicService extends _LogMockLogicService implements ILogLogicService {
	
}