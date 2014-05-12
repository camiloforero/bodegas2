package co.edu.uniandes.csw.bodega.master.logic.ejb;

import co.edu.uniandes.csw.iteminventario.logic.dto.ItemInventarioDTO;
import co.edu.uniandes.csw.iteminventario.persistence.api.IItemInventarioPersistence;
import co.edu.uniandes.csw.bodega.logic.dto.BodegaDTO;
import co.edu.uniandes.csw.bodega.master.logic.api._IBodegaMasterLogicService;
import co.edu.uniandes.csw.bodega.master.logic.dto.BodegaMasterDTO;
import co.edu.uniandes.csw.bodega.master.persistence.api.IBodegaMasterPersistence;
import co.edu.uniandes.csw.bodega.master.persistence.entity.BodegaItemInventarioEntity;
import co.edu.uniandes.csw.bodega.persistence.api.IBodegaPersistence;
import javax.inject.Inject;

public abstract class _BodegaMasterLogicService implements _IBodegaMasterLogicService {

    @Inject
    protected IBodegaPersistence bodegaPersistance;
    @Inject
    protected IBodegaMasterPersistence bodegaMasterPersistance;
    @Inject
    protected IItemInventarioPersistence itemInventarioPersistance;

    public BodegaMasterDTO createMasterBodega(BodegaMasterDTO bodega) {
        BodegaDTO persistedBodegaDTO = bodegaPersistance.createBodega(bodega.getBodegaEntity());
        if (bodega.getCreateItemInventario() != null) {
            for (ItemInventarioDTO itemInventarioDTO : bodega.getCreateItemInventario()) {
                ItemInventarioDTO persistedItemInventarioDTO = itemInventarioPersistance.createItemInventario(itemInventarioDTO);
                BodegaItemInventarioEntity bodegaItemInventarioEntity = new BodegaItemInventarioEntity(persistedBodegaDTO.getId(), persistedItemInventarioDTO.getId());
                bodegaMasterPersistance.createBodegaItemInventario(bodegaItemInventarioEntity);
            }
        }
        return bodega;
    }

    public BodegaMasterDTO getMasterBodega(Long id) {
        return bodegaMasterPersistance.getBodega(id);
    }

    public void deleteMasterBodega(Long id) {
        bodegaPersistance.deleteBodega(id);
    }

    public void updateMasterBodega(BodegaMasterDTO bodega) {
        bodegaPersistance.updateBodega(bodega.getBodegaEntity());

        //---- FOR RELATIONSHIP
        // persist new itemInventario
        if (bodega.getCreateItemInventario() != null) {
            for (ItemInventarioDTO itemInventarioDTO : bodega.getCreateItemInventario()) {
                ItemInventarioDTO persistedItemInventarioDTO = itemInventarioPersistance.createItemInventario(itemInventarioDTO);
                BodegaItemInventarioEntity bodegaItemInventarioEntity = new BodegaItemInventarioEntity(bodega.getBodegaEntity().getId(), persistedItemInventarioDTO.getId());
                bodegaMasterPersistance.createBodegaItemInventario(bodegaItemInventarioEntity);
            }
        }
        // update itemInventario
        if (bodega.getUpdateItemInventario() != null) {
            for (ItemInventarioDTO itemInventarioDTO : bodega.getUpdateItemInventario()) {
                itemInventarioPersistance.updateItemInventario(itemInventarioDTO);
            }
        }
        // delete itemInventario
        if (bodega.getDeleteItemInventario() != null) {
            for (ItemInventarioDTO itemInventarioDTO : bodega.getDeleteItemInventario()) {
                bodegaMasterPersistance.deleteBodegaItemInventario(bodega.getBodegaEntity().getId(), itemInventarioDTO.getId());
                itemInventarioPersistance.deleteItemInventario(itemInventarioDTO.getId());
            }
        }
    }
}
