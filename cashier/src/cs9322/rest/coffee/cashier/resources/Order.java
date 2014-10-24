package cs9322.rest.coffee.cashier.resources;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import cs9322.rest.coffee.cashier.model.OrderBean;


@Path("/order")
public class Order {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	@POST
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String newOrder(
			@FormParam("type") String type,
			@FormParam("additions") String additions,
			@Context HttpServletResponse servletResponse
	) throws IOException {
		OrderBean o = new OrderBean(type);
		if (additions != null){
			o.setAdditions(additions);
		}
		//todo calculate the cost
		//insert into database
		//get the id
		return  "<?xml version=\"1.0\"?>" + "<msg>" + o.getType()+"   "+ o.getAdditions() + "</msg>";
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.TEXT_XML)
	public String getOrder(@PathParam("id") String id) {
		return  "<?xml version=\"1.0\"?>" + "<msg>" + id + "</msg>";
	}
	@GET
	@Produces(MediaType.TEXT_XML)
	public String getOrders() {
		return "<?xml version=\"1.0\"?>" + "<msg>" + "all orders" + "</msg>";
	}
	
}
