package com.javaweb.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.RentareaRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.RentareaEntity;
import com.javaweb.repository.entity.districtEntity;

@Component
public class BuildingDTOConverter {
	@Autowired
	private DistrictRepository districtRepository;
	
	@Autowired
	private RentareaRepository rentareaRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public BuildingDTO toBuildingDTO(BuildingEntity item) {
		BuildingDTO b = modelMapper.map(item, BuildingDTO.class);
		districtEntity de = districtRepository.findnamedistrict(item.getDistrictid());
		b.setAddress(item.getWard() + ", " + item.getStreet() + ", " + de.getName());
		List<RentareaEntity> rentareaEntities = rentareaRepository.getValueByBuildingId(item.getId());
		String rentResult = rentareaEntities.stream().map(it -> it.getValue().toString()).collect(Collectors.joining(","));
		b.setRentarea(rentResult);
		return b;
	}
}
