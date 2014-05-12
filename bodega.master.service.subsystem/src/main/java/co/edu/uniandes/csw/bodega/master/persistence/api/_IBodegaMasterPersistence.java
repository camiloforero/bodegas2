package co.edu.uniandes.csw.bodega.master.persistence.api;

import co.edu.uniandes.csw.bodega.master.persistence.entity.BodegaItemInventarioEntity;
import co.edu.uniandes.csw.iteminventario.logic.dto.ItemInventarioDTO;
import co.edu.uniandes.csw.bodega.master.logic.dto.BodegaMasterDTO;
import java.util.List;

public interface _IBodegaMasterPersistence {

    public BodegaItemInventarioEntity createBodegaItemInventario(BodegaItemInventarioEntity entity);

    public void deleteBodegaItemInventario(Long bodegaId, Long itemInventarioId);
    
    public List<ItemInventarioDTO> getItemInventarioListForBodega(Long bodegaId);
    
    public BodegaMasterDTO getBodega(Long bodegaId);

}
