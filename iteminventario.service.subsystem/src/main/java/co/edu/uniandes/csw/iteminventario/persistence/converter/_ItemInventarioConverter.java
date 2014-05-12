
package co.edu.uniandes.csw.iteminventario.persistence.converter;

import java.util.ArrayList;
import java.util.List;

import co.edu.uniandes.csw.iteminventario.logic.dto.ItemInventarioDTO;
import co.edu.uniandes.csw.iteminventario.persistence.entity.ItemInventarioEntity;

public abstract class _ItemInventarioConverter {


	public static ItemInventarioDTO entity2PersistenceDTO(ItemInventarioEntity entity){
		if (entity != null) {
			ItemInventarioDTO dto = new ItemInventarioDTO();
				dto.setId(entity.getId());
				dto.setCantidad(entity.getCantidad());
				dto.setName(entity.getName());
				dto.setProductoId(entity.getProductoId());
			return dto;
		}else{
			return null;
		}
	}
	
	public static ItemInventarioEntity persistenceDTO2Entity(ItemInventarioDTO dto){
		if(dto!=null){
			ItemInventarioEntity entity=new ItemInventarioEntity();
			entity.setId(dto.getId());
			entity.setCantidad(dto.getCantidad());
			entity.setName(dto.getName());
			entity.setProductoId(dto.getProductoId());
			return entity;
		}else {
			return null;
		}
	}
	
	public static List<ItemInventarioDTO> entity2PersistenceDTOList(List<ItemInventarioEntity> entities){
		List<ItemInventarioDTO> dtos=new ArrayList<ItemInventarioDTO>();
		for(ItemInventarioEntity entity:entities){
			dtos.add(entity2PersistenceDTO(entity));
		}
		return dtos;
	}
	
	public static List<ItemInventarioEntity> persistenceDTO2EntityList(List<ItemInventarioDTO> dtos){
		List<ItemInventarioEntity> entities=new ArrayList<ItemInventarioEntity>();
		for(ItemInventarioDTO dto:dtos){
			entities.add(persistenceDTO2Entity(dto));
		}
		return entities;
	}
}