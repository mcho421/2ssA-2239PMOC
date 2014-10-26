package cs9322.rest.coffee.cashier.data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Jdbc {
	
	private static Connection conn = null;
	public static void init_db() throws SQLException{
			conn = DriverManager.getConnection("jdbc:sqlite:asst2.db");
			Statement statement = conn.createStatement();
			statement.executeUpdate("drop table if exists orders");
			statement.executeUpdate("create table orders (id integer not null primary key, "
					+ "type string, cost string, additions string, payment string, carddetail string,"
					+ "p_status string default 'no', c_status string default 'not prepared')");
			conn.close();
	}
	public static Connection getConn() throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:/Users/mathew/Developer/uni/postgrad/soa/ass2/cashier/WebContent/WEB-INF/asst2.db");
		if(conn != null)
			System.out.println("connnected");
		return conn;
	}
	public static void closeConn() throws SQLException {
		if (conn != null)
			conn.close();
	}
	
	
	
}


