


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.w3c.dom.NodeList;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/CurrencyConvertServlet")
public class CurrencyConvertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String evenSetId;
	static String targetCurrency;
	static String message;
	static String url;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CurrencyConvertServlet() {
        super();
        url = Constants.instance.getCurrencyConvertUrl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		evenSetId = request.getParameter("EventSetId");
		targetCurrency = request.getParameter("TargetCurrency");
		
		try {
			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			// Send SOAP Message to SOAP Server
			
			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);

			// Process the SOAP Response
			//printSOAPResponse(soapResponse);
			SOAPBody body = soapResponse.getSOAPBody();
	        NodeList returnList = body.getElementsByTagNameNS(Constants.instance.namespace, Constants.instance.currencyConvertMarketDataResponseName);

	        if(returnList.getLength() != 0) {
	        	NodeList innerResultList = returnList.item(0).getChildNodes();
	        	String msg = innerResultList.item(0).getTextContent().trim();
	        	message = "EventSetId:"+msg;
	        	System.out.println(msg);
	        }
	        else {
	        	SOAPFault fault = body.getFault();
	        	NodeList detailList = fault.getElementsByTagName("detail");
	        	NodeList innerResultList = detailList.item(0).getChildNodes();
	        	NodeList deepResultList = innerResultList.item(0).getChildNodes();
	        	String msg = deepResultList.item(1).getTextContent().trim();
	        	message = "Fault :"+ msg;
	        	System.out.println(msg);
	        }
			soapConnection.close();
		} catch (Exception e) {
			System.err.println("Error occurred while sending SOAP Request to Server");
			e.printStackTrace();
		}
		request.setAttribute("message", message);
    	request.setAttribute("wsdl", Constants.instance.getCurrencyConvertUrl());
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/currencyconvertservice.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	private static SOAPMessage createSOAPRequest() throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		//String serverURI = "http://localhost:8080/axis2/";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		envelope.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema" );

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();
		//soapBody.addNamespaceDeclaration("ns", "http://sltf.unsw.edu.au/services");
		//soapBodyElem.addNamespaceDeclaration("xmlns","http://sltf.unsw.edu.au/services");
		SOAPElement soapBodyElem = soapBody.addChildElement(Constants.instance.currencyConvertMarketDataRequestName,"","http://sltf.unsw.edu.au/services");
		
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("eventSetId");
		soapBodyElem1.addTextNode(evenSetId);
		SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("targetCurrency");
		soapBodyElem2.addTextNode(targetCurrency);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.setHeader("Content-Type", "text/xml");
		headers.addHeader("SOAPAction", "currencyConvertMarketData");

		soapMessage.saveChanges();

		/* Print the request message */
		System.out.print("Request SOAP Message = ");
		soapMessage.writeTo(System.out);
		System.out.println();

		return soapMessage;
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

