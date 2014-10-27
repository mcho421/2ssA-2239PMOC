package cs9322.rest.marketdata.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cs9322.rest.marketdata.model.EventData;

public class Data {

	public static EventData getEvent(String eventSetId) throws SQLException {
		EventData ed = null;
		Connection conn = Jdbc.getConn();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from events where eventsetid = '"+eventSetId+"'");
		if(rs.next()) {
			ed = new EventData();
			ed.setEventSetId(rs.getString("eventsetid"));
			ed.setXmlLocation(rs.getString("xmllocation"));
		}
		Jdbc.closeConn();
		return ed;
	}

}
