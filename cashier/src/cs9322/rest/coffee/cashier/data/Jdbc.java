package cs9322.rest.coffee.cashier.data;
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
		new File(System.getProperty("catalina.home")+"/dbs").mkdirs();
        getConn();
        Statement statement = conn.createStatement();
        statement.executeUpdate(
            "create table if not exists orders (" +
                "id integer not null primary key, " +
                "type string, " +
                "cost string, " +
                "additions string default '', " +
                "payment string default '', " +
                "carddetail string default '', " +
                "p_status string default 'no', " +
                "c_status string default 'not prepared')");
        closeConn();
	}
	public static Connection getConn() throws SQLException {
        Context ctx;
		try {
			ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/asst2");
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


