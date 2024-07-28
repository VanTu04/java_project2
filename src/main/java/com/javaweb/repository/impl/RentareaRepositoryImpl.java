package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.RentareaRepository;
import com.javaweb.repository.entity.RentareaEntity;
import com.javaweb.utils.JDBCConnectionUtil;

@Repository
public class RentareaRepositoryImpl implements RentareaRepository {

	@Override
	public List<RentareaEntity> getValueByBuildingId(Long id) {
		List<RentareaEntity> rentareaEntities = new ArrayList<RentareaEntity>();
		try(
			Connection conn = JDBCConnectionUtil.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM rentarea WHERE buildingid = " + id);
			) {
			while(rs.next()) {
				RentareaEntity rentareaEntity = new RentareaEntity();
				rentareaEntity.setId(rs.getString("id"));
				rentareaEntity.setValue(rs.getString("value"));
				rentareaEntity.setBuildingid(rs.getString("buildingid"));
				rentareaEntities.add(rentareaEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rentareaEntities;
	}

}
