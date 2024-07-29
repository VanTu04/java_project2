package com.javaweb.repository;

import java.util.List;

import com.javaweb.Builder.BuildingSearchBuilder;
import com.javaweb.repository.entity.BuildingEntity;

public interface BuildingRepository {
	public abstract List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder);
}
