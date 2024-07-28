package com.javaweb.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCConnectionUtil {
	static final String url = "jdbc:mysql://localhost:3306/estatebasic";
	static final String username = "root";
	static final String password = "0978477143";
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
