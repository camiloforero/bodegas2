
package co.edu.uniandes.csw.log.logic.mock;
import java.util.ArrayList;
import java.util.List;

import co.edu.uniandes.csw.log.logic.dto.LogDTO;
import co.edu.uniandes.csw.log.logic.api._ILogLogicService;

public abstract class _LogMockLogicService implements _ILogLogicService {

	private Long id= new Long(1);
	protected List<LogDTO> data=new ArrayList<LogDTO>();

	public LogDTO createLog(LogDTO log){
		id++;
		log.setId(id);
		return log;
    }

	public List<LogDTO> getLogs(){
		return data; 
	}

	public LogDTO getLog(Long id){
		for(LogDTO d:data){
			if(d.getId().equals(id)){
				return d;
			}
		}
		return null;
	}

	public void deleteLog(Long id){
	    LogDTO delete=null;
		for(LogDTO d:data){
			if(d.getId().equals(id)){
				delete=d;
			}
		}
		if(delete!=null){
			data.remove(delete);
		} 
	}

	public void updateLog(LogDTO log){
	    LogDTO delete=null;
		for(LogDTO d:data){
			if(d.getId().equals(id)){
				delete=d;
			}
		}
		if(delete!=null){
			data.remove(delete);
			data.add(log);
		} 
	}	
}