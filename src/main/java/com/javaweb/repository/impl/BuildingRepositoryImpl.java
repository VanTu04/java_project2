package com.javaweb.repository.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.javaweb.Builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

@Repository
@Primary
public class BuildingRepositoryImpl implements BuildingRepository{
	
	public static void jointable(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
		Long staffid = buildingSearchBuilder.getStaffId();
		if(staffid != null) {
			where.append(" INNER JOIN assignmentbuilding asb ON b.id = asb.buildingid ");
		}
		Long rentAreaTo = buildingSearchBuilder.getAreaTo();
		Long rentAreaFrom = buildingSearchBuilder.getAreaFrom();
		if(rentAreaTo != null || rentAreaFrom != null) {
			where.append(" INNER JOIN rentarea r ON b.id = r.buildingid ");
		}
		List<String> typeCode = buildingSearchBuilder.getTypeCode();
		if(typeCode != null && typeCode.size() != 0) {
			where.append(" INNER JOIN buildingrenttype bt ON b.id = bt.buildingid INNER JOIN renttype t ON bt.renttypeid = t.id ");
		}
	}
	
	public static void normalWhere(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
		try {
			Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
			for(Field item : fields) {
				item.setAccessible(true);
				String fieldName = item.getName();
				if(!fieldName.equals("staffid") && !fieldName.equals("typeCode") && !fieldName.startsWith("area") && !fieldName.startsWith("rentPrice")) {
					Object value = item.get(buildingSearchBuilder);
					if(value != null) {
						if(item.getType().getName().equals("java.lang.Long") || item.getType().getName().equals("java.lang.Integer")) {
							where.append(" AND b." + fieldName + " = " + value + " ");
						}
						else if(item.getType().getName().equals("java.lang.String")){
							where.append(" AND b." + fieldName + " LIKE '%" + value + "%' ");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void specialWhere(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
		Long staffid = buildingSearchBuilder.getStaffId();
		if(staffid != null) {
			where.append(" AND asb.staffid = " + staffid );
		}
		Long rentAreaTo = buildingSearchBuilder.getAreaTo();
		Long rentAreaFrom = buildingSearchBuilder.getAreaFrom();
		if(rentAreaTo != null || rentAreaFrom != null) {
			if(rentAreaFrom != null) {
				where.append(" AND r.value >= " + rentAreaFrom);
			}
			if(rentAreaTo != null) {
				where.append(" AND r.value <= " + rentAreaTo);
			}
		}
		Long rentPriceTo = buildingSearchBuilder.getRentPriceTo();
		Long rentPriceFrom = buildingSearchBuilder.getRentPriceFrom();
		if(rentPriceTo != null || rentPriceFrom != null) {
			if(rentPriceFrom != null) {
				where.append(" AND b.rentprice >= " + rentPriceFrom);
			}
			if(rentPriceTo != null) {
				where.append(" AND b.rentprice <= " + rentPriceTo);
			}
		}
		List<String> typeCode = buildingSearchBuilder.getTypeCode();
		if(typeCode != null && typeCode.size() != 0) {
			List<String> code = new ArrayList<String>();
			for(String item : typeCode) {
				code.add("'" + item + "'");
			}			
			where.append(" AND t.code IN(" + String.join(",", code) + ") ");
		}
	}
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
		//JPQL
//		String sql = " FROM BuildingEntity b ";
//		Query query = entityManager.createQuery(sql, BuildingEntity.class);
		
		//SQL Native
//		String sql = "SELECT * FROM building WHERE building.name like '%a%' ";
//		Query query = entityManager.createNativeQuery(sql, BuildingEntity.class);
//		return query.getResultList();
		
		String s = "SELECT b.id, b.name, b.districtid, b.street, b.ward, b.numberofbasement, b.floorarea, b.rentprice, b.rentpricedescription, b.managername, b.managerphonenumber, b.servicefee, b.brokeragefee FROM building b ";
		StringBuilder where = new StringBuilder(s);
		jointable(buildingSearchBuilder, where);
		where.append(" WHERE 1=1 ");
		normalWhere(buildingSearchBuilder, where);
		specialWhere(buildingSearchBuilder, where);
		where.append(" GROUP BY b.id;");
		Query query = entityManager.createNativeQuery(where.toString(), BuildingEntity.class);
		return query.getResultList();
	}

}
