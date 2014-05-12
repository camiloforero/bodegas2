package co.edu.uniandes.csw.pedido.master.logic.dto;

import co.edu.uniandes.csw.orden.logic.dto.OrdenDTO;
import co.edu.uniandes.csw.pedido.logic.dto.PedidoDTO;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class _PedidoMasterDTO {

 
    protected PedidoDTO pedidoEntity;
    protected Long id;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public PedidoDTO getPedidoEntity() {
        return pedidoEntity;
    }

    public void setPedidoEntity(PedidoDTO pedidoEntity) {
        this.pedidoEntity = pedidoEntity;
    }
    
    public List<OrdenDTO> createOrden;
    public List<OrdenDTO> updateOrden;
    public List<OrdenDTO> deleteOrden;
    public List<OrdenDTO> listOrden;	
	
	
	
    public List<OrdenDTO> getCreateOrden(){ return createOrden; };
    public void setCreateOrden(List<OrdenDTO> createOrden){ this.createOrden=createOrden; };
    public List<OrdenDTO> getUpdateOrden(){ return updateOrden; };
    public void setUpdateOrden(List<OrdenDTO> updateOrden){ this.updateOrden=updateOrden; };
    public List<OrdenDTO> getDeleteOrden(){ return deleteOrden; };
    public void setDeleteOrden(List<OrdenDTO> deleteOrden){ this.deleteOrden=deleteOrden; };
    public List<OrdenDTO> getListOrden(){ return listOrden; };
    public void setListOrden(List<OrdenDTO> listOrden){ this.listOrden=listOrden; };	
	
	
}

