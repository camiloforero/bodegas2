
package co.edu.uniandes.csw.log.persistence.converter;

import java.util.ArrayList;
import java.util.List;

import co.edu.uniandes.csw.log.logic.dto.LogDTO;
import co.edu.uniandes.csw.log.persistence.entity.LogEntity;

public abstract class _LogConverter {


	public static LogDTO entity2PersistenceDTO(LogEntity entity){
		if (entity != null) {
			LogDTO dto = new LogDTO();
				dto.setId(entity.getId());
				dto.setName(entity.getName());
				dto.setCantidad(entity.getCantidad());
				dto.setEntra(entity.getEntra());
				dto.setJustificacion(entity.getJustificacion());
				dto.setBodegaId(entity.getBodegaId());
				dto.setProductoId(entity.getProductoId());
			return dto;
		}else{
			return null;
		}
	}
	
	public static LogEntity persistenceDTO2Entity(LogDTO dto){
		if(dto!=null){
			LogEntity entity=new LogEntity();
			entity.setId(dto.getId());
			entity.setName(dto.getName());
			entity.setCantidad(dto.getCantidad());
			entity.setEntra(dto.getEntra());
			entity.setJustificacion(dto.getJustificacion());
			entity.setBodegaId(dto.getBodegaId());
			entity.setProductoId(dto.getProductoId());
			return entity;
		}else {
			return null;
		}
	}
	
	public static List<LogDTO> entity2PersistenceDTOList(List<LogEntity> entities){
		List<LogDTO> dtos=new ArrayList<LogDTO>();
		for(LogEntity entity:entities){
			dtos.add(entity2PersistenceDTO(entity));
		}
		return dtos;
	}
	
	public static List<LogEntity> persistenceDTO2EntityList(List<LogDTO> dtos){
		List<LogEntity> entities=new ArrayList<LogEntity>();
		for(LogDTO dto:dtos){
			entities.add(persistenceDTO2Entity(dto));
		}
		return entities;
	}
}