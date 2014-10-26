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
		} else if (action.equals("update-order")) {
            String idParam = request.getParameter("id");
			try {
                int idInt = Integer.parseInt(idParam);
                String typeParam = request.getParameter("type");
                String additionsParam = request.getParameter("additions");
                assert(typeParam != null);
                assert(additionsParam != null);

                Form form = new Form();
                form.add("id", idInt);
                form.add("type", typeParam);
                form.add("additions", additionsParam);
                // TODO: add .path(id) and xml
                ClientResponse cresponse = addBaristaKey(service.path("rest").path("order"))
                		.type(MediaType.APPLICATION_FORM_URLENCODED).put(ClientResponse.class, form);
                setResponse(request, cresponse);
                    
			} catch (NumberFormatException e) {
                page = "/error.jsp";
                request.setAttribute("errmsg", "Order id not an integer: " + request.getParameter("id"));
			}
		} else if (action.equals("new-order")) {
            String newType = request.getParameter("type");
            String newAdditions = request.getParameter("additions");
            assert(newType != null);
            assert(newAdditions != null);

            Form form = new Form();
            form.add("type", newType);
            form.add("additions", newAdditions);
            ClientResponse cresponse = addBaristaKey(service.path("rest").path("order"))
                    .type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, form);
            setResponse(request, cresponse);
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
		} else if (action.equals("new-payment")) {
            String idParam = request.getParameter("id");
			try {
                int idInt = Integer.parseInt(idParam);
                String paymentTypeParam = request.getParameter("payment_type");
                String cardDetailsParam = request.getParameter("card_details");
                assert(paymentTypeParam != null);
                assert(cardDetailsParam != null);

                Form form = new Form();
                form.add("payment_type", paymentTypeParam);
                form.add("card_details", cardDetailsParam);
                ClientResponse cresponse = addBaristaKey(service.path("rest").path("payment").path(idParam))
                		.type(MediaType.APPLICATION_FORM_URLENCODED).put(ClientResponse.class, form);
                setResponse(request, cresponse);
                    
			} catch (NumberFormatException e) {
                page = "/error.jsp";
                request.setAttribute("errmsg", "Order id not an integer: " + request.getParameter("id"));
			}
		} else if (action.equals("options-order")) {
            String idParam = request.getParameter("id"); // TODO: check if pos integer
            assert(idParam != null);

            ClientResponse cresponse = addBaristaKey(service.path("rest").path("order").path(idParam))
                    .options(ClientResponse.class);
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
				"http://localhost:8080/cs9322.rest.coffee.cashier").build();
	}
	
	private static Builder addBaristaKey(WebResource service) {
		return service.header("key", "barista");
	}

}
