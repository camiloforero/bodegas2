package co.edu.uniandes.csw.bodega.master.logic.dto;

import co.edu.uniandes.csw.iteminventario.logic.dto.ItemInventarioDTO;
import co.edu.uniandes.csw.bodega.logic.dto.BodegaDTO;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class _BodegaMasterDTO {

 
    protected BodegaDTO bodegaEntity;
    protected Long id;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public BodegaDTO getBodegaEntity() {
        return bodegaEntity;
    }

    public void setBodegaEntity(BodegaDTO bodegaEntity) {
        this.bodegaEntity = bodegaEntity;
    }
    
    public List<ItemInventarioDTO> createItemInventario;
    public List<ItemInventarioDTO> updateItemInventario;
    public List<ItemInventarioDTO> deleteItemInventario;
    public List<ItemInventarioDTO> listItemInventario;	
	
	
	
    public List<ItemInventarioDTO> getCreateItemInventario(){ return createItemInventario; };
    public void setCreateItemInventario(List<ItemInventarioDTO> createItemInventario){ this.createItemInventario=createItemInventario; };
    public List<ItemInventarioDTO> getUpdateItemInventario(){ return updateItemInventario; };
    public void setUpdateItemInventario(List<ItemInventarioDTO> updateItemInventario){ this.updateItemInventario=updateItemInventario; };
    public List<ItemInventarioDTO> getDeleteItemInventario(){ return deleteItemInventario; };
    public void setDeleteItemInventario(List<ItemInventarioDTO> deleteItemInventario){ this.deleteItemInventario=deleteItemInventario; };
    public List<ItemInventarioDTO> getListItemInventario(){ return listItemInventario; };
    public void setListItemInventario(List<ItemInventarioDTO> listItemInventario){ this.listItemInventario=listItemInventario; };	
	
	
}

