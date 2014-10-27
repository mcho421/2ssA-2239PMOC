package cs9322.rest.marketdata.resource;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import cs9322.rest.marketdata.data.Data;
import cs9322.rest.marketdata.data.Jsefa;
import cs9322.rest.marketdata.model.EventData;
import cs9322.rest.marketdata.model.MarketData;

@Path("{eventSetID}")
public class Events {
	
	static String url_pattern = "";

	@PUT
	public Response createEvent(
			@PathParam("eventSetId") String eventSetId) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
		EventData ed = Data.getEvent(eventSetId);
		if(ed != null)
			return Response.status(Response.Status.OK.getStatusCode()).build();
		else {
			String csv_url = url_pattern+ eventSetId;
			String xml_url = Jsefa.convert_csv(csv_url);
			EventData event = new EventData();
			event.setEventSetId(eventSetId);
			event.setXmlLocation(xml_url);
			event.insertEvent();
			
			return Response.status(Response.Status.CREATED.getStatusCode()).build();
		}
	}
	@GET
	@Path("/xml")
	@Produces(MediaType.APPLICATION_XML)
	public Response getEvent (@PathParam("eventSetId") String eventSetId) throws SQLException, FileNotFoundException {
		EventData ed = Data.getEvent(eventSetId);
		if(ed == null)
			return Response.status(Response.Status.NOT_FOUND.getStatusCode()).build();
		else {
			String xml_url = ed.getXmlLocation();
			List<MarketData> result = Jsefa.deserialize_xml(xml_url);
			return Response.status(Response.Status.OK.getStatusCode()).entity(result).build();
		}
	}
	@GET
	@Path("/trade/xml")
	@Produces(MediaType.APPLICATION_XML)
	public Response getTradeEventXml (@PathParam("eventSetId") String eventSetId) throws SQLException, FileNotFoundException {
		EventData ed = Data.getEvent(eventSetId);
		if(ed == null)
			return Response.status(Response.Status.NOT_FOUND.getStatusCode()).build();
		else {
			String xml_url = ed.getXmlLocation();
			String new_url = xml_url+"to be implemented";
			List<MarketData> result = Jsefa.deserialize_xml(new_url);
			return Response.status(Response.Status.OK.getStatusCode()).entity(result).build();
		}
	}
	@GET
	@Path("/trade/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTradeEventJson (@PathParam("eventSetId") String eventSetId) throws SQLException, FileNotFoundException {
		EventData ed = Data.getEvent(eventSetId);
		if(ed == null)
			return Response.status(Response.Status.NOT_FOUND.getStatusCode()).build();
		else {
			String xml_url = ed.getXmlLocation();
			String new_url = xml_url+"to be implemented";
			List<MarketData> result = Jsefa.deserialize_xml(new_url);
			return Response.status(Response.Status.OK.getStatusCode()).entity(result).build();
		}
	}
	@GET
	@Path("/trade/totalprice")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getTotalPrice (@PathParam("eventSetId") String eventSetId) throws SQLException, FileNotFoundException {
		EventData ed = Data.getEvent(eventSetId);
		if(ed == null)
			return Response.status(Response.Status.NOT_FOUND.getStatusCode()).build();
		else {
			String xml_url = ed.getXmlLocation();
			String total_price = xml_url+"to be implemented";
			//List<MarketData> result = Jsefa.deserialize_xml(new_url);
			return Response.status(Response.Status.OK.getStatusCode()).entity(total_price).build();
		}
	}
	@GET
	@Path("/quote/xml")
	@Produces(MediaType.APPLICATION_XML)
	public Response getQuoteEventXml (@PathParam("eventSetId") String eventSetId) throws SQLException, FileNotFoundException {
		EventData ed = Data.getEvent(eventSetId);
		if(ed == null)
			return Response.status(Response.Status.NOT_FOUND.getStatusCode()).build();
		else {
			String xml_url = ed.getXmlLocation();
			String new_url = xml_url+"to be implemented";
			List<MarketData> result = Jsefa.deserialize_xml(new_url);
			return Response.status(Response.Status.OK.getStatusCode()).entity(result).build();
		}
	}
	@GET
	@Path("/quote/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getQuoteEventJson (@PathParam("eventSetId") String eventSetId) throws SQLException, FileNotFoundException {
		EventData ed = Data.getEvent(eventSetId);
		if(ed == null)
			return Response.status(Response.Status.NOT_FOUND.getStatusCode()).build();
		else {
			String xml_url = ed.getXmlLocation();
			String new_url = xml_url+"to be implemented";
			List<MarketData> result = Jsefa.deserialize_xml(new_url);
			return Response.status(Response.Status.OK.getStatusCode()).entity(result).build();
		}
	}
}
