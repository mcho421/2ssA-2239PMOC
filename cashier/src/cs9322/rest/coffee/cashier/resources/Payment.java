package cs9322.rest.coffee.cashier.resources;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import cs9322.rest.coffee.cashier.data.Data;
import cs9322.rest.coffee.cashier.model.OptionData;
import cs9322.rest.coffee.cashier.model.OrderData;


@Path("/payment")
public class Payment {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response payOrder(@PathParam("id") String id,
			@FormParam("payment_type") String payment_type,
			@FormParam("card_details") String card_details,
			@Context HttpHeaders headers) throws SQLException, ClassNotFoundException {
		String key = headers.getRequestHeader("key").get(0);
		if(!key.equals("client"))
			return Response.status(Response.Status.FORBIDDEN.getStatusCode())
					.entity("Unauthorised").build();
		int oid = Integer.parseInt(id);
		OrderData order = Data.getOrder(oid);
		if(order == null) 
			return Response.status(Response.Status.NOT_FOUND.getStatusCode())
					.entity("Order Not Found").build();
		else {
			if(order.getP_status().equals("no")) {
				order.setP_status("yes");
				order.setPayment_type(payment_type);
				order.setCard_details(card_details);
				order.updatePayment();
				
				return Response.status(Response.Status.CREATED.getStatusCode()).build();
			}
			else
				return Response.status(Response.Status.FORBIDDEN.getStatusCode())
						.entity("It has been paid").build();
			}
	}
	
	@GET
	@Path("{id}")
	public OrderData getPayment(@PathParam("id") String id, @Context HttpHeaders headers) throws SQLException, ClassNotFoundException {
		String key = headers.getRequestHeader("key").get(0);
		if(key.equals("client") || key.equals("barista")) {
			int oid = Integer.parseInt(id);
			OrderData order = Data.getPayment(oid);
			if(order != null)
				return order;
			else
				throw new WebApplicationException(Response
						.status(Response.Status.NOT_FOUND.getStatusCode())
						.entity("Order Not Found").build());
		}
		else
			throw new WebApplicationException(Response
					.status(Response.Status.FORBIDDEN.getStatusCode())
					.entity("Unauthorised").build());
	}
	//todo options
	@OPTIONS
	@Path("{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public OptionData getPaymentOptions(@PathParam("id") String id, @Context HttpHeaders headers) throws SQLException, ClassNotFoundException {
		OptionData options = new OptionData();
		String key = headers.getRequestHeader("key").get(0);
		if(!key.equals("client") && !key.equals("barista"))
			throw new WebApplicationException(Response
						.status(Response.Status.FORBIDDEN.getStatusCode())
						.entity("Unauthorised").build());
		else {
			int oid = Integer.parseInt(id);
			OrderData order = Data.getOrder(oid);
			if(order == null)
				throw new WebApplicationException(Response
				.status(Response.Status.NOT_FOUND.getStatusCode())
				.entity("Order Not Found").build());
			if(key.equals("client")) {
				options.setGet();
				if(order.getP_status().equals("no"))
					options.setPut();
				return options;
			}
			else {
				options.setGet();
				return options;
				
			}
		}
	}
}
