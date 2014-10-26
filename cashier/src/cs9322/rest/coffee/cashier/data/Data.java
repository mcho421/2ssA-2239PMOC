package cs9322.rest.coffee.cashier.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs9322.rest.coffee.cashier.model.OrderData;

public class Data {

	public static OrderData getOrder(int id) throws SQLException, ClassNotFoundException {
		OrderData order = null;
		Connection conn = Jdbc.getConn();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from orders where id = '"+id+"'");
		if(rs.next()) {
			order = new OrderData();
			order.setId(Integer.toString(rs.getInt("id")));
			order.setType(rs.getString("type"));
			order.setAdditions(rs.getString("additions"));
			order.setCost(rs.getString("cost"));
			order.setPayment_uri("http://localhost:8080/cs9322.rest.coffee.cashier/rest/payment/"+id);
			order.setP_status(rs.getString("p_status"));
			order.setC_status(rs.getString("c_status"));
		}
		Jdbc.closeConn();
		return order;
	}
	public static OrderData getPayment(int id) throws SQLException, ClassNotFoundException {
		OrderData order = null;
		Connection conn = Jdbc.getConn();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from orders where id = '"+id+"'");
		if(rs.next()) {
			order = new OrderData();
			order.setAdditions(null);
			order.setId(null);
			order.setP_status(rs.getString("p_status"));
			if(order.getP_status().equals("yes")){
				order.setPayment_type(rs.getString("payment"));
				order.setCard_details(rs.getString("carddetail"));
			}
		}
		Jdbc.closeConn();
		return order;
	}
	
	public static List<OrderData> getAllOrders(String key) throws SQLException, ClassNotFoundException {
		List<OrderData> orders = new ArrayList<OrderData>();
		OrderData order = null;
		Connection conn = Jdbc.getConn();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from orders");
		while(rs.next()) {
			order = new OrderData();
			String id = Integer.toString(rs.getInt("id"));
			order.setId(id);
			order.setType(rs.getString("type"));
			order.setAdditions(rs.getString("additions"));
			order.setCost(rs.getString("cost"));
			order.setPayment_uri("http://localhost:8080/cs9322.rest.coffee.cashier/rest/payment/"+id);
			order.setC_status(rs.getString("c_status"));
			if(key.equals("barista")) {
				if(!order.getC_status().equals("released")) {
					order.setC_status(null);
					orders.add(order);
				}
					
			}
			else{
				order.setC_status(null);
				orders.add(order);
			}
		}
		Jdbc.closeConn();
		return orders;
	}
	
	
}
