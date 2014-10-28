package cs9322.rest.marketdata.data;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Jdbc {
	
	private static Connection conn = null;
	public static void init_db() throws SQLException {
		new File(System.getProperty("catalina.base")+"/dbs").mkdirs();
		System.out.println(System.getProperty("catalina.base")+"/dbs");
        getConn();
        Statement statement = conn.createStatement();
        statement.executeUpdate(
            "create table if not exists events (" +
                "eventsetid string not null primary key, " +
                "xmllocation string)");
        closeConn();
	}
	public static Connection getConn() throws SQLException {
        Context ctx;

		try {
			ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/asst2marketdata");
//    		conn = DriverManager.getConnection("jdbc:sqlite:" + System.getProperty("catalina.base")+"/dbs/asst2marketdata.db");
            conn = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		}
        
//		conn = DriverManager.getConnection("jdbc:sqlite:/Users/mathew/Developer/uni/postgrad/soa/ass2/cashier/WebContent/WEB-INF/asst2.db");
		if(conn != null)
			System.out.println("connnected");
		return conn;
	}
	public static void closeConn() throws SQLException {
		if (conn != null)
			conn.close();
	}
	
	
	
}
