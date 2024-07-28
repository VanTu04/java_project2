package com.javaweb.repository;

import java.util.List;
import java.util.Map;

import com.javaweb.repository.entity.BuildingEntity;

public interface BuildingRepository {
	public abstract List<BuildingEntity> findAll(Map<String, Object> params, List<String> typeCode);
}
