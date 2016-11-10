package com.cninfo.download.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBC {
	public static Connection getConn() throws Exception {
	 	Connection conn = null;
	 	Class.forName("com.mysql.jdbc.Driver");
	 	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/investdata", "root","admin");
	 	return conn;
	}
}
