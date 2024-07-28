package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.entity.districtEntity;
import com.javaweb.utils.JDBCConnectionUtil;

@Repository
public class DistrictRepositoryImpl implements DistrictRepository {	
	@Override
	public districtEntity findnamedistrict(Long id) {
		districtEntity d = new districtEntity();
		String sql = "SELECT * FROM district WHERE id =" + id;
		try (
				 Connection conn = JDBCConnectionUtil.getConnection();
				 Statement stmt = conn.createStatement();
				 ResultSet rs = stmt.executeQuery(sql);
				) {
				
					while(rs.next()) {
						d.setId(rs.getLong("id"));
						d.setName(rs.getString("name"));
					}
			} catch (Exception e) {
				e.getStackTrace();
			}
		return d;
	}
}
