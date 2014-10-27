package cs9322.rest.marketdata.data;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Jdbc {
	
	private static Connection conn = null;
	public static void init_db() throws SQLException {
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
            conn = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		}
        
//		Class.forName("org.sqlite.JDBC");
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
