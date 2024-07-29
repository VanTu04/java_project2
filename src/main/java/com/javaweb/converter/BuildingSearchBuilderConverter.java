package com.javaweb.converter;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.javaweb.Builder.BuildingSearchBuilder;
import com.javaweb.utils.MapUtil;

@Component
public class BuildingSearchBuilderConverter {
	public BuildingSearchBuilder toBuildingSearchBuilder(Map<String,Object> params, List<String> typeCode) {
		BuildingSearchBuilder buildingSearchBuilder = new BuildingSearchBuilder.Builder()
																			   .setName(MapUtil.getObject(params, "name", String.class))
																			   .setFloorArea(MapUtil.getObject(params, "floorarea", Long.class))
																			   .setWard(MapUtil.getObject(params, "ward", String.class))
																			   .setStreet(MapUtil.getObject(params, "street", String.class))
																			   .setDistrictcode(MapUtil.getObject(params, "districtid", Long.class))
																			   .setNumberOfBasement(MapUtil.getObject(params, "numberofbasement", Integer.class))
																			   .setTypeCode(typeCode)
																			   .setManagerName(MapUtil.getObject(params, "managername", String.class))
																			   .setManagerPhoneNumber(MapUtil.getObject(params, "managerphonenumber", String.class))
																			   .setRentPriceFrom(MapUtil.getObject(params, "rentpricefrom", Long.class))
																			   .setRentPriceTo(MapUtil.getObject(params, "rentpriceto", Long.class))
																			   .setAreaFrom(MapUtil.getObject(params, "rentareafrom", Long.class))
																			   .setAreaTo(MapUtil.getObject(params, "rentareato", Long.class))
																			   .setStaffId(MapUtil.getObject(params, "staffid", Long.class))
																			   .Build();														
		return buildingSearchBuilder;
	}
}
