package cs9322.rest.coffee.cashier.model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import cs9322.rest.coffee.cashier.data.Jdbc;
@XmlRootElement
public class OrderData {

	
	String id;
	String type;
	String cost;
	String additions = "";
	String payment_uri;
	String payment_type;
	String card_details;
	String p_status;
	String c_status;
	
	public String getPayment_uri() {
		return payment_uri;
	}
	public void setPayment_uri(String payment_uri) {
		this.payment_uri = payment_uri;
	}
	public int insertOrder() throws SQLException, ClassNotFoundException{
		Connection conn = Jdbc.getConn();
		Statement stmt = conn.createStatement();
		System.out.println("insert into orders (type, cost, additions) values ('"+type+"','"+cost+"','"+additions+"') ");
		stmt.execute("insert into orders (type, cost, additions) values ('"+type+"','"+cost+"','"+additions+"') ");
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		int oid = rs.getInt(1);
		Jdbc.closeConn();
		return oid;
	}
	public void updateOrder() throws SQLException, ClassNotFoundException{
		Connection conn = Jdbc.getConn();
		int id = Integer.parseInt(this.id);
		Statement stmt = conn.createStatement();
		stmt.execute("update orders set type='"+type+"', additions='"+additions+"'where id = '"+id+"'");
		Jdbc.closeConn();
	}
	public void updateCoffee() throws SQLException, ClassNotFoundException{
		Connection conn = Jdbc.getConn();
		int id = Integer.parseInt(this.id);
		Statement stmt = conn.createStatement();
		stmt.execute("update orders set c_status='"+c_status+"'where id = '"+id+"'");
		Jdbc.closeConn();
	}
	public void updatePayment() 
	throws SQLException, ClassNotFoundException{
		Connection conn = Jdbc.getConn();
		Statement stmt = conn.createStatement();
		int id = Integer.parseInt(this.id);
		stmt.execute("update orders set payment='"+payment_type+"', carddetail='"+card_details+"', p_status = '"+p_status+"'where id = '"+id+"'");
		Jdbc.closeConn();
	}
	public void deleteOrder() throws SQLException, ClassNotFoundException{
		Connection conn = Jdbc.getConn();
		int id = Integer.parseInt(this.id);
		Statement stmt = conn.createStatement();
		stmt.execute("delete from orders where id = '"+id+"'");
		Jdbc.closeConn();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getAdditions() {
		return additions;
	}
	public void setAdditions(String additions) {
		this.additions = additions;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	public String getCard_details() {
		return card_details;
	}
	public void setCard_details(String card_details) {
		this.card_details = card_details;
	}
	public String getP_status() {
		return p_status;
	}
	public void setP_status(String p_status) {
		this.p_status = p_status;
	}
	public String getC_status() {
		return c_status;
	}
	public void setC_status(String c_status) {
		this.c_status = c_status;
	}
	public void prepared()  throws SQLException, ClassNotFoundException{
		Connection conn = Jdbc.getConn();
		Statement stmt = conn.createStatement();
		stmt.execute("update orders set c_status = 'prepared' where id = '"+id+"'");
		Jdbc.closeConn();
	}
	
}
