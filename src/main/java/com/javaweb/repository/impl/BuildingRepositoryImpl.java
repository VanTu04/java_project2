package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.CheckNumberutil;
import com.javaweb.utils.CheckStringutil;
import com.javaweb.utils.JDBCConnectionUtil;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {	
	public static void jointable(Map<String, Object> params, List<String> typeCode, StringBuilder where) {
		String staffid = (String)params.get("staffId");
		if(CheckStringutil.isString(staffid)) {
			where.append(" INNER JOIN assignmentbuilding asb ON b.id = asb.buildingid ");
		}
		String rentAreaTo = (String)params.get("rentAreaTo");
		String rentAreaFrom = (String)params.get("rentAreaFrom");
		if(CheckStringutil.isString(rentAreaTo) || CheckStringutil.isString(rentAreaFrom)) {
			where.append(" INNER JOIN rentarea r ON b.id = r.buildingid ");
		}
		if(typeCode != null && typeCode.size() != 0) {
			where.append(" INNER JOIN buildingrenttype bt ON b.id = bt.buildingid INNER JOIN renttype t ON bt.renttypeid = t.id ");
		}
	}
	
	// normalWhere la khong phai join bang nao vao bang building va khong WHERE cac so sanh >=, <=, in
	public static void normalWhere(Map<String, Object> params, List<String> typeCode, StringBuilder where) {
		for(Map.Entry<String, Object> item : params.entrySet()) {
			// kiem tra neu ko phai cac dieu kien de join bang thi thuc hien
			if(!item.getKey().equals("staffId") && !item.getKey().equals("typeCode") && !item.getKey().startsWith("rentArea") && !item.getKey().startsWith("rentPrice")) {
				String value = item.getValue().toString();
				if(CheckNumberutil.isNumber(value)) {
					where.append(" AND b." + item.getKey() + " = " + value + " ");
				}
				else {
					where.append(" AND b." + item.getKey() + " LIKE '%" + value + "%' ");
				}
			}
		}
	}
	
	// specialWhere la phai join nhieu bang
	public static void specialWhere(Map<String, Object> params, List<String> typeCode, StringBuilder where) {
		String staffid = (String)params.get("staffId");
		if(CheckStringutil.isString(staffid)) {
			where.append(" AND asb.staffid = " + staffid );
		}
		String rentAreaTo = (String)params.get("rentAreaTo");
		String rentAreaFrom = (String)params.get("rentAreaFrom");
		if(CheckStringutil.isString(rentAreaTo) || CheckStringutil.isString(rentAreaFrom)) {
			if(CheckStringutil.isString(rentAreaFrom)) {
				where.append(" AND r.value >= " + rentAreaFrom);
			}
			if(CheckStringutil.isString(rentAreaTo)) {
				where.append(" AND r.value <= " + rentAreaTo);
			}
		}
		String rentPriceTo = (String)params.get("rentPriceTo");
		String rentPriceFrom = (String)params.get("rentPriceFrom");
		if(CheckStringutil.isString(rentPriceTo) || CheckStringutil.isString(rentPriceFrom)) {
			if(rentPriceFrom != null) {
				where.append(" AND b.rentprice >= " + rentPriceFrom);
			}
			if(rentPriceTo != null) {
				where.append(" AND b.rentprice <= " + rentPriceTo);
			}
		}
		if(typeCode != null && typeCode.size() != 0) {
			List<String> code = new ArrayList<String>();
			for(String item : typeCode) {
				code.add("'" + item + "'");
			}			
			where.append(" AND t.code IN(" + String.join(",", code) + ") ");
		}
	}
	
	@Override
	public List<BuildingEntity> findAll(Map<String, Object> params, List<String> typeCode) {
		String s = "SELECT b.id, b.name, b.districtid, b.street, b.ward, b.numberofbasement, b.floorarea, b.rentprice, b.rentpricedescription, b.managername, b.managerphonenumber, b.servicefee, b.brokeragefee FROM building b ";
		StringBuilder where = new StringBuilder(s);
		jointable(params, typeCode, where);
		where.append(" WHERE 1=1 ");
		normalWhere(params, typeCode, where);
		specialWhere(params, typeCode, where);
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
