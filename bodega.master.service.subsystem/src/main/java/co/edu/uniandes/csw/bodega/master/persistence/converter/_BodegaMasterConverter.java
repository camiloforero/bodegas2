package co.edu.uniandes.csw.bodega.master.persistence.converter;
import co.edu.uniandes.csw.bodega.master.persistence.entity.BodegaItemInventarioEntity;
import co.edu.uniandes.csw.iteminventario.logic.dto.ItemInventarioDTO;
import co.edu.uniandes.csw.iteminventario.persistence.converter.ItemInventarioConverter;
import co.edu.uniandes.csw.bodega.logic.dto.BodegaDTO;
import co.edu.uniandes.csw.bodega.master.logic.dto.BodegaMasterDTO;
import co.edu.uniandes.csw.bodega.persistence.converter.BodegaConverter;
import co.edu.uniandes.csw.bodega.persistence.entity.BodegaEntity;
import java.util.ArrayList;
import java.util.List;

public abstract class _BodegaMasterConverter {

    public static BodegaMasterDTO entity2PersistenceDTO(BodegaEntity bodegaEntity 
    ,List<BodegaItemInventarioEntity> bodegaItemInventarioEntity 
    ) {
        BodegaDTO bodegaDTO = BodegaConverter.entity2PersistenceDTO(bodegaEntity);
        ArrayList<ItemInventarioDTO> itemInventarioEntities = new ArrayList<ItemInventarioDTO>(bodegaItemInventarioEntity.size());
        for (BodegaItemInventarioEntity bodegaItemInventario : bodegaItemInventarioEntity) {
            itemInventarioEntities.add(ItemInventarioConverter.entity2PersistenceDTO(bodegaItemInventario.getItemInventarioEntity()));
        }
        BodegaMasterDTO respDTO  = new BodegaMasterDTO();
        respDTO.setBodegaEntity(bodegaDTO);
        respDTO.setListItemInventario(itemInventarioEntities);
        return respDTO;
    }

}