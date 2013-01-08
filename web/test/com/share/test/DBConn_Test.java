package com.share.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 测试类：数据库连接
 * 
 * @author Ancai
 * @since 2012-4-3
 * @version 1.0
 */
public class DBConn_Test {
	
	public static Connection getDBConn(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/webdb","cloud", "lac999");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(DBConn_Test.getDBConn());
	}

}
