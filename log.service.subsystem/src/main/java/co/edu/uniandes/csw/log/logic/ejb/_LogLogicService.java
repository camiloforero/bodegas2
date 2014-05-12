
package co.edu.uniandes.csw.log.logic.ejb;
import java.util.List;
import javax.inject.Inject;

import co.edu.uniandes.csw.log.logic.dto.LogDTO;
import co.edu.uniandes.csw.log.logic.api._ILogLogicService;
import co.edu.uniandes.csw.log.persistence.api.ILogPersistence;

public abstract class _LogLogicService implements _ILogLogicService {

	@Inject
	protected ILogPersistence persistance;

	public LogDTO createLog(LogDTO log){
		return persistance.createLog( log); 
    }

	public List<LogDTO> getLogs(){
		return persistance.getLogs(); 
	}

	public LogDTO getLog(Long id){
		return persistance.getLog(id); 
	}

	public void deleteLog(Long id){
	    persistance.deleteLog(id); 
	}

	public void updateLog(LogDTO log){
	    persistance.updateLog(log); 
	}	
}