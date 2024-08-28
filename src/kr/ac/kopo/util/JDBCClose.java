package kr.ac.kopo.util;

import java.sql.Connection;
import java.sql.Statement;

public class JDBCClose {
	public static void close(Connection c, Statement s) {
		if(s != null) {
			try {
				s.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(c != null) {
			try {
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
