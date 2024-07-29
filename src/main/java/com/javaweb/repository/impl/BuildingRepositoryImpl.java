package com.javaweb.repository.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.javaweb.Builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.CheckNumberutil;
import com.javaweb.utils.CheckStringutil;
import com.javaweb.utils.JDBCConnectionUtil;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {	
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
	
	// normalWhere la khong phai join bang nao vao bang building va khong WHERE cac so sanh >=, <=, in
	public static void normalWhere(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
//		for(Map.Entry<String, Object> item : params.entrySet()) {
//			// kiem tra neu ko phai cac dieu kien de join bang thi thuc hien
//			if(!item.getKey().equals("staffId") && !item.getKey().equals("typeCode") && !item.getKey().startsWith("rentArea") && !item.getKey().startsWith("rentPrice")) {
//				String value = item.getValue().toString();
//				if(CheckNumberutil.isNumber(value)) {
//					where.append(" AND b." + item.getKey() + " = " + value + " ");
//				}
//				else {
//					where.append(" AND b." + item.getKey() + " LIKE '%" + value + "%' ");
//				}
//			}
//		}
		
		// duyet doi tuong
		try {
			Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
			for(Field item : fields) {
				item.setAccessible(true);
				String fieldName = item.getName();
				if(!fieldName.equals("staffId") && !fieldName.equals("typeCode") && !fieldName.startsWith("rentArea") && !fieldName.startsWith("rentPrice")) {
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
	
	// specialWhere la phai join nhieu bang
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
		if(rentAreaTo != null || rentAreaFrom != null) {
			if(rentAreaFrom != null) {
				where.append(" AND b.value >= " + rentPriceFrom);
			}
			if(rentAreaTo != null) {
				where.append(" AND b.value <= " + rentPriceTo);
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
	
	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
		String s = "SELECT b.id, b.name, b.districtid, b.street, b.ward, b.numberofbasement, b.floorarea, b.rentprice, b.rentpricedescription, b.managername, b.managerphonenumber, b.servicefee, b.brokeragefee FROM building b ";
		StringBuilder where = new StringBuilder(s);
		jointable(buildingSearchBuilder, where);
		where.append(" WHERE 1=1 ");
		normalWhere(buildingSearchBuilder, where);
		specialWhere(buildingSearchBuilder, where);
		where.append(" GROUP BY b.id;");
		List<BuildingEntity> result = new ArrayList<BuildingEntity>();
		try (
			 Connection conn = JDBCConnectionUtil.getConnection();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(where.toString());
			) {
				while(rs.next()) {
					BuildingEntity buildingEntity = new BuildingEntity();
					buildingEntity.setId(rs.getLong("b.id"));
					buildingEntity.setName(rs.getString("b.name"));
					buildingEntity.setWard(rs.getString("b.ward"));
					buildingEntity.setDistrictid(rs.getLong("b.districtid"));
					buildingEntity.setStreet(rs.getString("b.street"));
					buildingEntity.setFloorarea(rs.getLong("b.floorarea"));
					buildingEntity.setRentprice(rs.getLong("b.rentprice"));
					buildingEntity.setServicefee(rs.getLong("b.servicefee"));
					buildingEntity.setBrokeragefee(rs.getLong("b.brokeragefee"));
					buildingEntity.setManagername(rs.getString("b.managername"));
					buildingEntity.setManagerphonenumber(rs.getString("b.managerphonenumber"));
					result.add(buildingEntity);
				}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return result;
	}

}
