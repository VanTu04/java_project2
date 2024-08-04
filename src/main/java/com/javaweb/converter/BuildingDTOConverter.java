package com.javaweb.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.RentareaEntity;

@Component
public class BuildingDTOConverter {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public BuildingDTO toBuildingDTO(BuildingEntity item) {
		// modelMapper dùng để map tự động các field trùng tên giống nhau
		BuildingDTO b = modelMapper.map(item, BuildingDTO.class);
		b.setAddress(item.getStreet() + ", " + item.getWard() + ", " + item.getDistrict().getName());
		List<RentareaEntity> rentareaEntities = item.getRentareaEntities();
		String rentResult = rentareaEntities.stream().map(it -> it.getValue().toString()).collect(Collectors.joining(","));
		b.setRentarea(rentResult);
		return b;
	}
}
