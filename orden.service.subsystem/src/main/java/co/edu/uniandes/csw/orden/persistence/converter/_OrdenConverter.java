
package co.edu.uniandes.csw.orden.persistence.converter;

import java.util.ArrayList;
import java.util.List;

import co.edu.uniandes.csw.orden.logic.dto.OrdenDTO;
import co.edu.uniandes.csw.orden.persistence.entity.OrdenEntity;

public abstract class _OrdenConverter {


	public static OrdenDTO entity2PersistenceDTO(OrdenEntity entity){
		if (entity != null) {
			OrdenDTO dto = new OrdenDTO();
				dto.setId(entity.getId());
				dto.setName(entity.getName());
				dto.setTipo(entity.getTipo());
				dto.setEstado(entity.getEstado());
				dto.setCantidad(entity.getCantidad());
			return dto;
		}else{
			return null;
		}
	}
	
	public static OrdenEntity persistenceDTO2Entity(OrdenDTO dto){
		if(dto!=null){
			OrdenEntity entity=new OrdenEntity();
			entity.setId(dto.getId());
			entity.setName(dto.getName());
			entity.setTipo(dto.getTipo());
			entity.setEstado(dto.getEstado());
			entity.setCantidad(dto.getCantidad());
			return entity;
		}else {
			return null;
		}
	}
	
	public static List<OrdenDTO> entity2PersistenceDTOList(List<OrdenEntity> entities){
		List<OrdenDTO> dtos=new ArrayList<OrdenDTO>();
		for(OrdenEntity entity:entities){
			dtos.add(entity2PersistenceDTO(entity));
		}
		return dtos;
	}
	
	public static List<OrdenEntity> persistenceDTO2EntityList(List<OrdenDTO> dtos){
		List<OrdenEntity> entities=new ArrayList<OrdenEntity>();
		for(OrdenDTO dto:dtos){
			entities.add(persistenceDTO2Entity(dto));
		}
		return entities;
	}
}