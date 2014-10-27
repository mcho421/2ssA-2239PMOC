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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.transform.TransformerException;

import cs9322.rest.marketdata.data.DataOperation;
import cs9322.rest.marketdata.data.FilterXml;
import cs9322.rest.marketdata.data.Jsefa;
import cs9322.rest.marketdata.init.Constants;
import cs9322.rest.marketdata.model.EventData;
import cs9322.rest.marketdata.model.Data;

@Path("/{eventSetId}")
public class Events {
	
	static String url_pattern = "/Users/mathew/Desktop/";
	private static int counter = 1;

	@PUT
	public Response createEvent(
			@PathParam("eventSetId") String eventSetId) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
		EventData ed = DataOperation.getEvent(eventSetId);
		if(ed != null)
			return Response.status(Response.Status.OK.getStatusCode()).build();
		else {
			String csv_url = url_pattern+ eventSetId +".csv";
			String xml_url = Jsefa.convert_csv(csv_url);
			EventData event = new EventData();
			event.setEventSetId(eventSetId);
			event.setXmlLocation(xml_url);
			event.insertEvent();
			
			return Response.status(Response.Status.CREATED.getStatusCode()).build();
		}
	}
	public List<Data> getEvent(String eventSetId) throws SQLException, FileNotFoundException {
		System.out.println(eventSetId);
		EventData ed = DataOperation.getEvent(eventSetId);
		if(ed == null)
			throw new WebApplicationException( Response.status(Response.Status.NOT_FOUND.getStatusCode()).build());
		else {
			String xml_url = ed.getXmlLocation();
			List<Data> result = Jsefa.deserialize_xml(xml_url);
			return result;
		}
	}

	@GET
	@Path("/xml")
	@Produces(MediaType.APPLICATION_XML)
	public List<Data> getEventXml (@PathParam("eventSetId") String eventSetId) throws SQLException, FileNotFoundException {
		return getEvent(eventSetId);
	}

	@GET
	@Path("/json")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Data> getEventJson (@PathParam("eventSetId") String eventSetId) throws SQLException, FileNotFoundException {
		return getEvent(eventSetId);
	}

	public List<Data> getEventType (String eventSetId, String type) throws SQLException, FileNotFoundException, TransformerException {
		EventData ed = DataOperation.getEvent(eventSetId);
		if(ed == null)
			throw new WebApplicationException( Response.status(Response.Status.NOT_FOUND.getStatusCode()).build());
		else {
			String xml_url = ed.getXmlLocation();
			String new_url = Constants.instance.convertedXmlFolderPath + "/" + getNextTempFileName();
			assert(new_url != null);
			FilterXml.filterByType(xml_url, new_url, type);
			List<Data> result = Jsefa.deserialize_xml(new_url);
			return result;
		}
	}

	@GET
	@Path("/trade/xml")
	@Produces(MediaType.APPLICATION_XML)
	public List<Data> getTradeEventXml (@PathParam("eventSetId") String eventSetId) throws SQLException, FileNotFoundException, TransformerException {
		return getEventType(eventSetId, "Trade");
	}

	@GET
	@Path("/trade/json")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Data> getTradeEventJson (@PathParam("eventSetId") String eventSetId) throws SQLException, FileNotFoundException, TransformerException {
		return getEventType(eventSetId, "Trade");
	}

	@GET
	@Path("/quote/xml")
	@Produces(MediaType.APPLICATION_XML)
	public List<Data> getQuoteEventXml (@PathParam("eventSetId") String eventSetId) throws SQLException, FileNotFoundException, TransformerException {
		return getEventType(eventSetId, "Quote");
	}

	@GET
	@Path("/quote/json")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Data> getQuoteEventJson (@PathParam("eventSetId") String eventSetId) throws SQLException, FileNotFoundException, TransformerException {
		return getEventType(eventSetId, "Quote");
	}

	public Response getTotalPrice (String eventSetId, String type) throws SQLException, FileNotFoundException, TransformerException {
		EventData ed = DataOperation.getEvent(eventSetId);
		if(ed == null)
			return Response.status(Response.Status.NOT_FOUND.getStatusCode()).build();
		else {
			String xml_url = ed.getXmlLocation();
			String total_price = FilterXml.totalPrice(xml_url, type);
			return Response.status(Response.Status.OK.getStatusCode()).entity(total_price).build();
		}
	}

	@GET
	@Path("/quote/totalprice")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getTotalPriceQuote (@PathParam("eventSetId") String eventSetId) throws SQLException, FileNotFoundException, TransformerException {
		return getTotalPrice(eventSetId, "Quote");
	}

	@GET
	@Path("/trade/totalprice")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getTotalPriceTrade (@PathParam("eventSetId") String eventSetId) throws SQLException, FileNotFoundException, TransformerException {
		return getTotalPrice(eventSetId, "Trade");
	}
	
	private String getNextTempFileName() {
		return Integer.toString(counter++);
	}
}
