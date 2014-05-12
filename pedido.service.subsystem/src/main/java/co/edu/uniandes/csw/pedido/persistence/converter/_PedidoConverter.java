
package co.edu.uniandes.csw.pedido.persistence.converter;

import java.util.ArrayList;
import java.util.List;

import co.edu.uniandes.csw.pedido.logic.dto.PedidoDTO;
import co.edu.uniandes.csw.pedido.persistence.entity.PedidoEntity;

public abstract class _PedidoConverter {


	public static PedidoDTO entity2PersistenceDTO(PedidoEntity entity){
		if (entity != null) {
			PedidoDTO dto = new PedidoDTO();
				dto.setId(entity.getId());
				dto.setCantidad(entity.getCantidad());
				dto.setName(entity.getName());
				dto.setProductoId(entity.getProductoId());
			return dto;
		}else{
			return null;
		}
	}
	
	public static PedidoEntity persistenceDTO2Entity(PedidoDTO dto){
		if(dto!=null){
			PedidoEntity entity=new PedidoEntity();
			entity.setId(dto.getId());
			entity.setCantidad(dto.getCantidad());
			entity.setName(dto.getName());
			entity.setProductoId(dto.getProductoId());
			return entity;
		}else {
			return null;
		}
	}
	
	public static List<PedidoDTO> entity2PersistenceDTOList(List<PedidoEntity> entities){
		List<PedidoDTO> dtos=new ArrayList<PedidoDTO>();
		for(PedidoEntity entity:entities){
			dtos.add(entity2PersistenceDTO(entity));
		}
		return dtos;
	}
	
	public static List<PedidoEntity> persistenceDTO2EntityList(List<PedidoDTO> dtos){
		List<PedidoEntity> entities=new ArrayList<PedidoEntity>();
		for(PedidoDTO dto:dtos){
			entities.add(persistenceDTO2Entity(dto));
		}
		return entities;
	}
}