package com.javaweb.api;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.model.BuildingDTO;
import com.javaweb.model.BuildingRequestDTO;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.districtEntity;
import com.javaweb.service.BuildingService;

@RestController
@Transactional
public class BuildingApi {
	
	@Autowired
	private BuildingService buildingService;
	
	@PersistenceContext
	EntityManager entityManager;
	
	@GetMapping(value = "/api/building/")
	public List<BuildingDTO> example(@RequestParam Map<String, Object> params,
									 @RequestParam(value = "typeCode", required = false) List<String> typeCode) {
		List<BuildingDTO> result = buildingService.findAll(params, typeCode);
		return result;
	}
	
	@PutMapping(value = "/api/building/")
	public void createBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
		// phan nay cho xuong repository xu ly
		BuildingEntity buildingEntity = new BuildingEntity();
		buildingEntity.setName(buildingRequestDTO.getName());
		buildingEntity.setStreet(buildingRequestDTO.getStreet());
		buildingEntity.setWard(buildingRequestDTO.getWard());
		districtEntity districtentity = new districtEntity();
		districtentity.setId(buildingRequestDTO.getDistrictid());
		buildingEntity.setDistrict(districtentity);
		buildingEntity.setWard(buildingRequestDTO.getWard());
		buildingEntity.setFloorarea(buildingRequestDTO.getFloorarea());
		buildingEntity.setRentprice(buildingRequestDTO.getRentprice());
		entityManager.persist(buildingEntity);
	}
	
	@PatchMapping(value = "/api/building/")
	public void mergeBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
		// phan nay cho xuong repository xu ly
		BuildingEntity buildingEntity = new BuildingEntity();
		buildingEntity.setId(1L);
		buildingEntity.setName(buildingRequestDTO.getName());
		buildingEntity.setStreet(buildingRequestDTO.getStreet());
		buildingEntity.setWard(buildingRequestDTO.getWard());
		districtEntity districtentity = new districtEntity();
		districtentity.setId(buildingRequestDTO.getDistrictid());
		buildingEntity.setDistrict(districtentity);
		buildingEntity.setWard(buildingRequestDTO.getWard());
		buildingEntity.setFloorarea(buildingRequestDTO.getFloorarea());
		buildingEntity.setRentprice(buildingRequestDTO.getRentprice());
		entityManager.merge(buildingEntity);
	}
	
	@DeleteMapping(value = "/api/building/{id}")
	public void createBuilding(@PathVariable Long id) {
		// phan nay cho xuong repository xu ly
		BuildingEntity buildingEntity = entityManager.find(BuildingEntity.class, id);
		entityManager.remove(buildingEntity);
	}
}
