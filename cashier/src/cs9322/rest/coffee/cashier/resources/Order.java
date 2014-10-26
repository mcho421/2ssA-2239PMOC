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


@Path("/order")
public class Order {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateOrder(
			@FormParam("id") String id,
			@FormParam("type") String type,
			@FormParam("additions") String additions,
			@FormParam("c_status") String c_status,
			@Context HttpHeaders headers) throws SQLException, ClassNotFoundException {
		String key = null;
		if(headers.getRequestHeader("key") == null)
			throw new WebApplicationException(Response
					.status(Response.Status.FORBIDDEN.getStatusCode())
					.entity("Unauthorised").build());
		else
		 key = headers.getRequestHeader("key").get(0);
		if(!key.equals("client") && !key.equals("barista"))
			return Response.status(Response.Status.FORBIDDEN.getStatusCode())
					.entity("Unauthorised").build();
		int oid = Integer.parseInt(id);
		OrderData order = Data.getOrder(oid);
		if(order == null) 
			return Response.status(Response.Status.NOT_FOUND.getStatusCode())
					.entity("Order Not Found").build();
		if(key.equals("client")){
			if(order.getP_status().equals("yes")||order.getC_status().equals("prepared")) {
				return  Response.status(Response.Status.FORBIDDEN.getStatusCode())
						.entity("order can not be updated now.").build();
			}
			else if (type.equals(order.getType()) && additions.equals(order.getAdditions())) {
				return Response.status(Response.Status.OK.getStatusCode()).entity(order).build();
			}
			else {
				order.setType(type);
				order.setAdditions(additions);
				order.updateOrder();
				order = Data.getOrder(oid);
				return Response.status(Response.Status.CREATED.getStatusCode()).entity(order).build();
			}
		}
		else {
			if(c_status.equals(order.getC_status()))
				return Response.status(Response.Status.OK.getStatusCode()).build();
			else if (c_status.equals("released") && order.getP_status().equals("no"))
				return  Response.status(Response.Status.FORBIDDEN.getStatusCode())
						.entity("This order has not been paid yet").build();
			else {
					order.setC_status(c_status);
					order.updateCoffee();
					return Response.status(Response.Status.CREATED.getStatusCode()).entity(order).build();
				}
		}
	}
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateOrders(
			@PathParam("id") String id,
			@FormParam("type") String type,
			@FormParam("additions") String additions,
			@FormParam("c_status") String c_status,
			@Context HttpHeaders headers) throws SQLException, ClassNotFoundException {
		String key = null;
		if(headers.getRequestHeader("key") == null)
			throw new WebApplicationException(Response
					.status(Response.Status.FORBIDDEN.getStatusCode())
					.entity("Unauthorised").build());
		else
		 key = headers.getRequestHeader("key").get(0);
		if(!key.equals("client") && !key.equals("barista"))
			return Response.status(Response.Status.FORBIDDEN.getStatusCode())
					.entity("Unauthorised").build();
		int oid = Integer.parseInt(id);
		OrderData order = Data.getOrder(oid);
		if(order == null) 
			return Response.status(Response.Status.NOT_FOUND.getStatusCode())
					.entity("Order Not Found").build();
		if(key.equals("client")){
			if(order.getP_status().equals("yes")||order.getC_status().equals("prepared")) {
				return  Response.status(Response.Status.FORBIDDEN.getStatusCode())
						.entity("order can not be updated now.").build();
			}
			else if (type.equals(order.getType()) && additions.equals(order.getAdditions())) {
				return Response.status(Response.Status.OK.getStatusCode()).entity(order).build();
			}
			else {
				order.setType(type);
				order.setAdditions(additions);
				order.updateOrder();
				order = Data.getOrder(oid);
				return Response.status(Response.Status.CREATED.getStatusCode()).entity(order).build();
			}
		}
		else {
			if(c_status.equals(order.getC_status()))
				return Response.status(Response.Status.OK.getStatusCode()).build();
			else if (c_status.equals("released") && order.getP_status().equals("no"))
				return  Response.status(Response.Status.FORBIDDEN.getStatusCode())
						.entity("This order has not been paid yet").build();
			else {
					order.setC_status(c_status);
					order.updateCoffee();
					return Response.status(Response.Status.CREATED.getStatusCode()).entity(order).build();
				}
		}
	}
	@POST
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newOrder(
			@FormParam("type") String type,
			@FormParam("additions") String additions,
			@Context HttpHeaders headers
	) throws IOException, SQLException, ClassNotFoundException {
		String key = null;
		if(headers.getRequestHeader("key") == null)
			throw new WebApplicationException(Response
					.status(Response.Status.FORBIDDEN.getStatusCode())
					.entity("Unauthorised").build());
		else
		 key = headers.getRequestHeader("key").get(0);
		if(key.equals("client")) {
			OrderData o = new OrderData();
			o.setType(type);
			if (additions != null){
				o.setAdditions(additions);
			}
			//todo calculate the cost
			o.setCost("20");
			int id = 0;
			id = o.insertOrder();
			if(id == 0)
				throw new WebApplicationException(Response
						.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
						.entity("SQLite: insertion failed").build());
			String retStr =  "<?xml version=\"1.0\"?>" + "<uri>" + 
			"http://localhost:8080/cs9322.rest.coffee.cashier/rest/payment/"+id+ 
			"</uri>"+"<cost>"+o.getCost()+"</cost>";
			
			return Response.status(Response.Status.CREATED.getStatusCode()).entity(retStr).build();
		}
		else 
		{
			throw new WebApplicationException(Response
					.status(Response.Status.FORBIDDEN.getStatusCode())
					.entity("Unauthorised").build());
		}
	}
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
	public OrderData getOrder(@PathParam("id") String id, @Context HttpHeaders headers) throws SQLException, ClassNotFoundException {
		String key = null;
		if(headers.getRequestHeader("key")== null)
			throw new WebApplicationException(Response
					.status(Response.Status.FORBIDDEN.getStatusCode())
					.entity("Unauthorised").build());
		else
		 key = headers.getRequestHeader("key").get(0);
		if(key.equals("client") || key.equals("barista")) {
			int oid = Integer.parseInt(id);
			OrderData order = Data.getOrder(oid);
			if(order != null) {
				order.setId(null);
				order.setP_status(null);
				return order;
			}
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
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<OrderData> getOrders(@Context HttpHeaders headers) throws SQLException, ClassNotFoundException {
		String key = null;
		if(headers.getRequestHeader("key") == null)
			throw new WebApplicationException(Response
					.status(Response.Status.FORBIDDEN.getStatusCode())
					.entity("Unauthorised").build());
		else
		 key = headers.getRequestHeader("key").get(0);
		if(key.equals("client") || key.equals("barista")) {
			if(Data.getAllOrders(key) == null)
				throw new WebApplicationException(Response
						.status(Response.Status.OK.getStatusCode()).build());
			else
				return Data.getAllOrders(key);
		}
		else 
			throw new WebApplicationException(Response
					.status(Response.Status.FORBIDDEN.getStatusCode())
					.entity("Unauthorised").build());
		
	}
	@DELETE
	@Path("{id}")
	public Response deleteOrder(@PathParam("id") String id, @Context HttpHeaders headers) throws SQLException, ClassNotFoundException {
		String key = null;
		if(headers.getRequestHeader("key") == null)
			throw new WebApplicationException(Response
					.status(Response.Status.FORBIDDEN.getStatusCode())
					.entity("Unauthorised").build());
		else
		 key = headers.getRequestHeader("key").get(0);
		if(!key.equals("client"))
			return Response.status(Response.Status.FORBIDDEN.getStatusCode())
					.entity("Unauthorised").build();
		int oid = Integer.parseInt(id);
		OrderData order = Data.getOrder(oid);
		if(order == null) 
			return Response.status(Response.Status.NOT_FOUND.getStatusCode())
					.entity("Order Not Found").build();
		else {
			if(order.getC_status().equals("prepared") || order.getP_status().equals("no")) {
				order.deleteOrder();
				return Response.status(Response.Status.OK.getStatusCode()).build();
			}
			else
				return Response.status(Response.Status.FORBIDDEN.getStatusCode())
						.entity("Too late to cancel").build();
		}
	}
	//todo options
	
	@OPTIONS
	@Path("{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public OptionData getOrderOptions(@PathParam("id") String id, @Context HttpHeaders headers) throws SQLException, ClassNotFoundException {
		OptionData options = new OptionData();
		String key = null;
		if(headers.getRequestHeader("key") == null)
			throw new WebApplicationException(Response
					.status(Response.Status.FORBIDDEN.getStatusCode())
					.entity("Unauthorised").build());
		else
		 key = headers.getRequestHeader("key").get(0);
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
			if(order.getC_status().equals("not prepared") && order.getP_status().equals("no")){
				if(key.equals("client")) {
					options.setPut();
					options.setGet();
					options.setDelete();
				}
				else {
					options.setGet();
					options.setPut();
				}
				return options;
			}
			else if (order.getC_status().equals("not prepared") && order.getP_status().equals("yes")) {
				if(key.equals("client")) {
					options.setGet();
				}
				else {
					options.setGet();
					options.setPut();
				}
				return options;
			}
			else if(order.getC_status().equals("prepared") && order.getP_status().equals("no")) {
				if(key.equals("client")) {
					options.setGet();
				}
				else {
					options.setGet();
				}
				return options;			
			}
			else if(order.getC_status().equals("prepared") && order.getP_status().equals("yes")) {
				if(key.equals("client")) {
					options.setGet();
				}
				else {
					options.setGet();
					options.setPut();
				}
				return options;			
			}
			else {
				if(key.equals("client")) {
					options.setGet();
				}
				else {
					options.setGet();
				}
				return options;	
			}
		}
	}
	
}
