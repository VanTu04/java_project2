package com.javaweb.repository;

import java.util.List;

import com.javaweb.repository.entity.RentareaEntity;

public interface RentareaRepository {
	public abstract List<RentareaEntity> getValueByBuildingId(Long id);
}
