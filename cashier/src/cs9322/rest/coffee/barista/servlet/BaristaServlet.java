package cs9322.rest.coffee.barista.servlet;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;

import cs9322.rest.coffee.cashier.model.OrderData;

/**
 * Servlet implementation class BaristaServlet
 */
@WebServlet("/BaristaServlet")
public class BaristaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BaristaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get main parameters from user
		String pageParam = request.getParameter("page");
		String action = request.getParameter("action");
        String id = request.getParameter("id");
		Boolean forward = true;
		System.out.println(pageParam);
		System.out.println(action);
        System.out.println(id);

        // connect to REST service
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(getBaseURI());
		
		String page = "/barista.jsp";
		if (pageParam == null) {
			page = "/barista.jsp";
			List<OrderData> orders = addBaristaKey(service.path("rest").path("order")).
					accept(MediaType.APPLICATION_XML).get(new GenericType<List<OrderData>>(){});
			assert(orders != null);
			request.setAttribute("orders", orders);
		} else if (pageParam.equals("view-order")) {
			if (id != null) {
                page = "/barista-response.jsp";
                ClientResponse cresponse = addBaristaKey(service.path("rest").path("order").path(id))
                		.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
                setResponse(request, cresponse);
			} else {
                page = "/error.jsp";
                request.setAttribute("errmsg", "Order id not specified on View Order page");
			}
		} else if (pageParam.equals("view-payment")) {
			if (id != null) {
                page = "/barista-response.jsp";
                ClientResponse cresponse = addBaristaKey(service.path("rest").path("payment").path(id))
                		.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
                setResponse(request, cresponse);
			} else {
                page = "/error.jsp";
                request.setAttribute("errmsg", "Order id not specified on View Order page");
			}
		} else if (pageParam.equals("response")) {
			page = "/barista-response.jsp";
		} else {
			page = "/error.jsp";
			request.setAttribute("errmsg", "Page not found: " + pageParam);
		}

		if (action == null) {
            // do nothing
		} else if (action.equals("prepare-order")) {
            String idParam = request.getParameter("id"); // TODO: check if pos integer
            assert(idParam != null);

            Form form = new Form();
            form.add("c_status", "prepared");
            ClientResponse cresponse = addBaristaKey(service.path("rest").path("order").path(idParam))
                    .type(MediaType.APPLICATION_FORM_URLENCODED).put(ClientResponse.class, form);
            setResponse(request, cresponse);
		} else if (action.equals("release-order")) {
            String idParam = request.getParameter("id"); // TODO: check if pos integer
            assert(idParam != null);

            Form form = new Form();
            form.add("c_status", "released");
            ClientResponse cresponse = addBaristaKey(service.path("rest").path("order").path(idParam))
                    .type(MediaType.APPLICATION_FORM_URLENCODED).put(ClientResponse.class, form);
            setResponse(request, cresponse);
		}

		if (forward) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);	
		} else {
			// do nothing
//            response.sendRedirect(page);
		}
	}
	
	private static void setResponse(HttpServletRequest request, ClientResponse cresponse) {
        request.setAttribute("head", cresponse.toString());
        String body = "";
        try {
            body = cresponse.getEntity(String.class);
        } catch (Exception e) {
            body = "empty";
        }
        System.out.println("Form response: " + body);
        request.setAttribute("body", body);
	}
	
	private static URI getBaseURI() {
		return UriBuilder.fromUri(
				//"http://localhost:8080/cs9322.rest.coffee").build();
				"http://mcho421.srvr:8880/cs9322.rest.coffee").build();

	}
	
	private static Builder addBaristaKey(WebResource service) {
		return service.header("key", "barista");
	}

}
