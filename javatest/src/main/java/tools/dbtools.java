package main.java.tools;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Assert;

import com.mysql.cj.jdbc.StatementImpl;
import com.mysql.cj.xdevapi.Statement;

public class dbtools {

	public static Connection conn = null;
	private static String url = "jdbc:mysql://192.168.200.198:3306/opensns";
	private static String user = "opensns";
	private static String password = "opensns";

	private static String url1 = "jdbc:mysql://192.168.200.198:3306/opensns";
	private static String user1 = "opensns";
	private static String password1 = "opensns";

	private static String url2 = "jdbc:mysql://192.168.200.39:3306/zentao";
	private static String user2 = "root";
	private static String password2 = "123456";

	public static Connection conn(String DB) {

		try {
			if (DB == "test") {
				conn = DriverManager.getConnection(url, user, password);
			} else if (DB == "fzzl_test") {
				conn = DriverManager.getConnection(url1, user1, password1);
			} else if (DB == "zentao") {
				conn = DriverManager.getConnection(url2, user2, password2);
			}

			if (!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");

			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void queryDB(String DB, String sql, String checkpoint, String expresult) {
		try {
			conn(DB);
			java.sql.Statement st = conn.createStatement();
			System.out.println(sql);
			ResultSet obj = ((java.sql.Statement) st).executeQuery(sql);
			if (obj.next()) {
				String res = obj.getString(checkpoint);
				assertEquals(res, expresult);
				System.out.println("Data Checking Success");
			} else {
				Assert.fail("Error!Data Not Found");
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	//
	public static void updateDB(String DB, String sql) {
		try {
			conn(DB);
			java.sql.Statement st = conn.createStatement();
			System.out.println(sql);
			int obj = ((java.sql.Statement) st).executeUpdate(sql);
			if (obj == 1) {
				System.out.println("Data Updating Success");
			} else {
				Assert.fail("Error!Updating Failed");
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// ɾ��ĳ������
	public static void delsqlDB(String DB, String sql) {
		try {
			conn(DB);
			java.sql.Statement st = conn.createStatement();
			int obj = ((java.sql.Statement) st).executeUpdate(sql);
			System.out.println(obj);
			if (obj >= 0) {
				System.out.println("Data Delete Success");
			} else {
				Assert.fail("Error!Delete Failed");
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//����ִ��sql 
	public static void exesql(String DB, String sql) {
		try {
			conn(DB);
			java.sql.Statement st = conn.createStatement();
			//System.out.println(sql);
			boolean res = ((java.sql.Statement) st).execute(sql);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	//for zentao
	public static ResultSet executeSql(String DB,String sql) {
		try {
			conn(DB);
			Statement statement = (Statement) conn.createStatement();
			ResultSet resultSet = ((StatementImpl) statement).executeQuery(sql);
			conn.close();
			if (!conn.isClosed()) {
				conn.close();
			}
			return resultSet;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

}
