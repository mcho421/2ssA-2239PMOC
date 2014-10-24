package cs9322.rest.coffee.cashier.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Data {

	public static int insertOrder(String type, String cost, String additions) throws SQLException{
		Connection conn = Jdbc.getConn();
		Statement stmt = conn.createStatement();
		stmt.execute("insert into orders (type, cost, additions) values ('"+type+"','"+cost+"','"+additions+"') ");
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		int id = rs.getInt("id");
		Jdbc.closeConn();
		return id;
	}
	public static void updateOrder(int id, String type, String additions ) throws SQLException{
		Connection conn = Jdbc.getConn();
		Statement stmt = conn.createStatement();
		stmt.execute("update orders set type='"+type+"', additions='"+additions+"'where id = '"+id+"'");
		Jdbc.closeConn();
	}
	public static void updatePayment(int id, String payment_type, String card_details ) 
	throws SQLException{
		Connection conn = Jdbc.getConn();
		Statement stmt = conn.createStatement();
		stmt.execute("update orders set payment='"+payment_type+"', carddetail='"+card_details+"'where id = '"+id+"'");
		Jdbc.closeConn();
	}
	public static void deleteOrder(int id) throws SQLException{
		Connection conn = Jdbc.getConn();
		Statement stmt = conn.createStatement();
		stmt.execute("delete from orders where id = '"+id+"'");
		Jdbc.closeConn();
	}
	public static void preparing(int id)  throws SQLException{
		Connection conn = Jdbc.getConn();
		Statement stmt = conn.createStatement();
		stmt.execute("update orders set c_status = 'preparing' where id = '"+id+"'");
		Jdbc.closeConn();
	}
	public static void prepared(int id)  throws SQLException{
		Connection conn = Jdbc.getConn();
		Statement stmt = conn.createStatement();
		stmt.execute("update orders set c_status = 'prepared' where id = '"+id+"'");
		Jdbc.closeConn();
	}
	
	
}
