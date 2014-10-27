

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
 * Servlet implementation class ImportDownloadServlet
 */
@WebServlet("/ImportDownloadServlet")
public class ImportDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//static String evenSetId;
	static String sec;
	static String startDate;
	static String endDate;
	static String dataSourceURL ;
	static String eventSetId;
	static String message1, message2;
	static String url;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImportDownloadServlet() {
        super();
        url = Constants.instance.getImportDownloadUrl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	eventSetId = request.getParameter("eventSetId");
    	sec = request.getParameter("sec");
    	startDate = request.getParameter("startDate");
    	endDate = request.getParameter("endDate");
    	dataSourceURL = request.getParameter("dataSourceURL");
    	String submitted = request.getParameter("submitted");
    	
    	if (submitted != null) {
			if(eventSetId == null) {
				System.out.println("Invoking Import service");
				ImportService();
				request.setAttribute("message1", message1);
			}
			else {
				System.out.println("Invoking Dowload service");
				DownloadService();
				request.setAttribute("message2", message2);
			}
    	}
    	request.setAttribute("wsdl", Constants.instance.getImportDownloadUrl());
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/importdownloadservice.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    private void ImportService() {
    	try {
			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			// Send SOAP Message to SOAP Server
			//String url = "http://localhost:8080/axis2/services/ImportDownloadServices?wsdl";
			SOAPMessage soapResponse = soapConnection.call(createImportSOAPRequest(), url);

			// Process the SOAP Response
			//printSOAPResponse(soapResponse);
			SOAPBody body = soapResponse.getSOAPBody();
			System.out.println("response, body and returnlist:");
			System.out.println(soapResponse);

			System.out.println(body);
	        NodeList returnList = body.getElementsByTagNameNS(Constants.instance.namespace, "importMarketDataResponse");
			System.out.println(returnList);

	        if(returnList.getLength() != 0) {
	        	NodeList innerResultList = returnList.item(0).getChildNodes();
	        	String msg = innerResultList.item(0).getTextContent().trim();
	        	message1 = "EventSetId:"+msg;
	        	System.out.println(msg);
	        }
	        else {
	        	String msg = null;
	        	SOAPFault fault = body.getFault();
	        	assert(fault!=null);
	        	System.out.println(fault.getDetail());
	        	NodeList detailList = fault.getElementsByTagName("detail");
	        	NodeList innerResultList = detailList.item(0).getChildNodes();
	        	if(innerResultList.getLength() != 0){
	        		NodeList deepResultList = innerResultList.item(0).getChildNodes();
	        	    msg = deepResultList.item(1).getTextContent().trim();
	        	}
	        	else {
	        		detailList = fault.getElementsByTagName("faultstring");
	        		msg = detailList.item(0).getTextContent().trim();
	        	}
	        		message1 = "Fault :"+ msg;
	        }
			soapConnection.close();
		} catch (Exception e) {
			System.err.println("Error occurred while sending SOAP Request to Server");
			e.printStackTrace();
		}
    }
    private void DownloadService() {
    	try {
			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			// Send SOAP Message to SOAP Server
//			String url = "http://localhost:8080/axis2/services/ImportDownloadServices?wsdl";
			SOAPMessage soapResponse = soapConnection.call(createDownloadSOAPRequest(), url);

			// Process the SOAP Response
			SOAPBody body = soapResponse.getSOAPBody();
	        NodeList returnList = body.getElementsByTagNameNS(Constants.instance.namespace, "downloadFileResponse");
	        if(returnList.getLength() != 0) {
	        	NodeList innerResultList = returnList.item(0).getChildNodes();
	        	String msg = innerResultList.item(0).getTextContent().trim();
	        	message2 = "dataURL:"+msg;
	        	System.out.println(msg);
	        }
	        else {
	        	String msg = null;
	        	SOAPFault fault = body.getFault();
	        	NodeList detailList = fault.getElementsByTagName("detail");
	        	NodeList innerResultList = detailList.item(0).getChildNodes();
	        	if(innerResultList.getLength() != 0){
	        		NodeList deepResultList = innerResultList.item(0).getChildNodes();
	        	    msg = deepResultList.item(1).getTextContent().trim();
	        	}
	        	else {
	        		detailList = fault.getElementsByTagName("faultstring");
	        		msg = detailList.item(0).getTextContent().trim();
	        	}
	        		message2 = "Fault :"+ msg;
	        }
			soapConnection.close();
		} catch (Exception e) {
			System.err.println("Error occurred while sending SOAP Request to Server");
			e.printStackTrace();
		}
    }
	private static SOAPMessage createImportSOAPRequest() throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		envelope.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema" );

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement("importMarketData","","http://sltf.unsw.edu.au/services");
		
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("sec");
		System.out.println(sec);
		System.out.println(soapBodyElem);
		System.out.println(soapBodyElem1);
		assert(soapBodyElem1 != null);
		soapBodyElem1.addTextNode(sec);
		SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("startDate");
		soapBodyElem2.addTextNode(startDate);
		SOAPElement soapBodyElem3 = soapBodyElem.addChildElement("endDate");
		soapBodyElem3.addTextNode(endDate);
		SOAPElement soapBodyElem4 = soapBodyElem.addChildElement("dataSourceURL");
		soapBodyElem4.addTextNode(dataSourceURL);

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
	private static SOAPMessage createDownloadSOAPRequest() throws Exception {
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
		SOAPElement soapBodyElem = soapBody.addChildElement("downloadFile","","http://sltf.unsw.edu.au/services");
		
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("eventSetId");
		if(eventSetId == null)
			eventSetId = "";
		soapBodyElem1.addTextNode(eventSetId);
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

}
