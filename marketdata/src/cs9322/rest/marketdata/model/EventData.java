package cs9322.rest.marketdata.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import cs9322.rest.marketdata.data.Jdbc;

public class EventData {

	String eventSetId;
	String xmlLocation;
	public void insertEvent() throws SQLException {
		Connection conn = Jdbc.getConn();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("insert into events (eventsetid, xmllocation) values ('"+eventSetId+"','"+xmlLocation+"'')");
		Jdbc.closeConn();
	}
	public String getEventSetId() {
		return eventSetId;
	}
	public void setEventSetId(String eventSetId) {
		this.eventSetId = eventSetId;
	}
	public String getXmlLocation() {
		return xmlLocation;
	}
	public void setXmlLocation(String xmlLocation) {
		this.xmlLocation = xmlLocation;
	}
}
