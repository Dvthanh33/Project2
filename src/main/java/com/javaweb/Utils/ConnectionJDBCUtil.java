package com.javaweb.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJDBCUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/estateadvance";
    private static final String USER = "root";
    private static final String PASS = "";
    
    public static Connection getConnection() {
    	Connection conn = null;
    	try {
    		conn = DriverManager.getConnection(DB_URL, USER, PASS);
    	}
    	catch(SQLException e) {
    		e.printStackTrace();
    	}
		return conn;
    }
}
