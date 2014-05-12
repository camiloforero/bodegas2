
package co.edu.uniandes.csw.log.persistence.api;

import java.util.List; 

import co.edu.uniandes.csw.log.logic.dto.LogDTO;

public interface _ILogPersistence {

	public LogDTO createLog(LogDTO detail);
	public List<LogDTO> getLogs();
	public LogDTO getLog(Long id);
	public void deleteLog(Long id);
	public void updateLog(LogDTO detail);
	
}