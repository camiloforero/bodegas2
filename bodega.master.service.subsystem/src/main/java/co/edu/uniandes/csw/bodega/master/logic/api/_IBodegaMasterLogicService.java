 
package co.edu.uniandes.csw.bodega.master.logic.api;

import co.edu.uniandes.csw.bodega.master.logic.dto.BodegaMasterDTO;

public interface _IBodegaMasterLogicService {

	public BodegaMasterDTO createMasterBodega(BodegaMasterDTO detail);
    public void updateMasterBodega(BodegaMasterDTO detail);
	public void deleteMasterBodega(Long id); 
	public BodegaMasterDTO getMasterBodega(Long id);
        
}