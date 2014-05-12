
package co.edu.uniandes.csw.bodega.persistence.converter;

import java.util.ArrayList;
import java.util.List;

import co.edu.uniandes.csw.bodega.logic.dto.BodegaDTO;
import co.edu.uniandes.csw.bodega.persistence.entity.BodegaEntity;

public abstract class _BodegaConverter {


	public static BodegaDTO entity2PersistenceDTO(BodegaEntity entity){
		if (entity != null) {
			BodegaDTO dto = new BodegaDTO();
				dto.setId(entity.getId());
				dto.setName(entity.getName());
			return dto;
		}else{
			return null;
		}
	}
	
	public static BodegaEntity persistenceDTO2Entity(BodegaDTO dto){
		if(dto!=null){
			BodegaEntity entity=new BodegaEntity();
			entity.setId(dto.getId());
			entity.setName(dto.getName());
			return entity;
		}else {
			return null;
		}
	}
	
	public static List<BodegaDTO> entity2PersistenceDTOList(List<BodegaEntity> entities){
		List<BodegaDTO> dtos=new ArrayList<BodegaDTO>();
		for(BodegaEntity entity:entities){
			dtos.add(entity2PersistenceDTO(entity));
		}
		return dtos;
	}
	
	public static List<BodegaEntity> persistenceDTO2EntityList(List<BodegaDTO> dtos){
		List<BodegaEntity> entities=new ArrayList<BodegaEntity>();
		for(BodegaDTO dto:dtos){
			entities.add(persistenceDTO2Entity(dto));
		}
		return entities;
	}
}