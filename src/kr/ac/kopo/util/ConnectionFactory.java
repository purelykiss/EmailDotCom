package kr.ac.kopo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Scanner;

public class ConnectionFactory {
	public Connection getConnection() throws Exception{
		Connection conn = null;
		
		//1단계:
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		//2단계:
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "hr";
		String password = "hr";
		conn = DriverManager.getConnection(url, user, password);
		conn.setAutoCommit(true);
		
		return conn;
	}
	
	
	
	
}
