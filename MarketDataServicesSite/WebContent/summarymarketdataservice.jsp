<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Summary Market Data Service</title>
</head>
<body>
<a href="index.jsp">Home</a>
<hr>
<h1>Summary Market Data Service</h1>
This service summarises the contents of an imported Market Data file. 
It shows the security code, period of market data events, the types of events, the currency and the file size.

<h2>SOAP WSDL</h2>
<a href="${wsdl}">
${wsdl}
</a>

<h2>Demo</h2>
<form action="SummaryServlet">
<table>
<tr>
<td>EventSetId :</td><td> <input type = "text" name="EventSetId" > </td>
</tr>
<tr><td><font color =red>${newEventSetId}</font></td></tr>
<tr>
</tr>
<tr><td><font color =red>${sec}</font></td></tr>
<tr>
</tr>
<tr><td><font color =red>${startDate}</font></td></tr>
<tr>
</tr>
<tr><td><font color =red>${endDate}</font></td></tr>
<tr>
</tr>
<tr><td><font color =red>${marketType}</font></td></tr>
<tr>
</tr>
<tr><td><font color =red>${currencyCode}</font></td></tr>
<tr>
</tr>
<tr><td><font color =red>${fileSize}</font></td></tr>
<tr>
</tr>
<tr><td><font color =red>${message}</font></td></tr>
<tr>
<td><input type="submit" value ="submit"></td>		
</table>			
</form>

<h2>Parameters</h2>
<h3>Operation: summaryMarketData</h3>
<table width="85%" border=1>
	<tr><td>Name</td><td>Type</td><td>Description</td><td>Example</td></tr>
	<tr><td>eventSetId</td><td>String</td><td>An ID that refers to a Market Data file on the server.</td><td>123</td></tr>
</table>

<h2>Error Codes</h2>
<h3>Operation: currencyConvertMarketData</h3>
<table width="85%" border=1>
	<tr><td>Fault Type</td><td>Fault Message</td><td>Cause</td><td>Resolution</td></tr>
	<tr><td>InvalidEventSetId</td><td>EventSetId does not exist</td><td>Market Data file corresponding to EventSetId does not exist.</td><td>Please check whether you have the correct EventSetId.</td></tr>
	<tr><td>ProgramError</td><td>Error reading or writing file</td><td>There was an error reading or writing files on the server.</td><td>Please contact the administrator to fix the problem.</td></tr>
</table>

</body>
</html>