package org.fipecafi.tools.filter.demo.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DerbyUtil {
	private static String driverClass = "org.apache.derby.jdbc.EmbeddedDriver";
	private static String protocol = "jdbc:derby:";
	private static String dbName = "sampleFilterDB";
	public static Connection getConnection() throws Exception{
		Properties props = new Properties(); // connection properties
		// providing a user name and password is optional in the embedded
		// and derbyclient frameworks
		props.put("user", "user1");
		props.put("password", "user1");
		Class.forName(driverClass).newInstance();
		Connection conn = DriverManager.getConnection(protocol + dbName + ";create=true", props);
		conn.setAutoCommit(false);
		return conn;
	}
	public static void initDerby(){
		PreparedStatement psInsert = null;
		Statement s = null;
		Connection conn = null;
		try {
			conn = DerbyUtil.getConnection();
			/*
			 * Creating a statement object that we can use for running various
			 * SQL statements commands against the database.
			 */
			s = conn.createStatement();
			// We create a table...
			try{
				s.execute("drop table location");
			}catch(Exception e1){
				e1.printStackTrace();
			}
			s.execute("create table location(housenum int, address varchar(40))");
			psInsert = conn.prepareStatement("insert into location values (?, ?)");

			psInsert.setInt(1, 1956);
			psInsert.setString(2, "Webster St.");
			psInsert.executeUpdate();
			System.out.println("Inserted 1956 Webster");

			psInsert.setInt(1, 1910);
			psInsert.setString(2, "Union St.");
			psInsert.executeUpdate();

			psInsert.setInt(1, 180);
			psInsert.setString(2, "Grand Ave.");
			psInsert.executeUpdate();

			psInsert.setInt(1, 300);
			psInsert.setString(2, "Lakeshore Ave.");
			psInsert.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
