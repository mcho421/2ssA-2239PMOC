<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Import/Download Market Data Service</title>
</head>
<body>
<a href="index.jsp">Home</a>
<hr>
<h1>Import/Download Market Data Service</h1>
This service allows users to filter a Market Data file. Users can filter to see the trades of a particular security during a certain time frame.

<h2>SOAP WSDL</h2>
<a href="${wsdl}">
${wsdl}
</a>

<h2>Demo</h2>
<form action="ImportDownloadServlet">
<input type="hidden" name="submitted" value ="true">
<table>
<tr><td><h3>ImportService</h3></td></tr>
<tr>
<td>sec:</td><td> <input type = "text" name="sec"></td>
</tr>
<tr>
<td>startDate:</td><td> <input name="startDate" type="text"></td>
</tr>
<tr>
<td>endDate:</td><td> <input name="endDate" type="text"></td>
</tr>
<tr>
<td>dataSourceURL:</td><td> <input name="dataSourceURL" type="text"></td>
</tr>
<tr><td><font color =red>${message1}</font></td></tr>
<tr>
<td><input type="submit" value ="submit"></td>		
</table>			
</form>

<form action="ImportDownloadServlet">
<input type="hidden" name="submitted" value ="true">
<table>
<tr><td><h3>DownloadService</h3></td></tr>
<tr>
<td>eventSetId:</td><td> <input type = "text" name="eventSetId"></td>
</tr>
<tr><td><font color =red>${message2}</font></td></tr>
<tr>
<td><input type="submit" value ="submit"></td>		
</table>			
</form>

<h2>Parameters</h2>
<h3>Operation: importMarketData</h3>
<table width="85%" border=1>
	<tr><td>Name</td><td>Type</td><td>Description</td><td>Example</td></tr>
	<tr><td>sec</td><td>String</td><td>The security code of the financial instrument to filter for.</td><td>ABCD</td></tr>
	<tr><td>startDate</td><td>dateTime</td><td>Filter market activity occuring after this timestamp (inclusive).</td><td>1992-12-23T11:22:33</td></tr>
	<tr><td>endDate</td><td>dateTime</td><td>Filter market activity before this timestamp (inclusive).</td><td>2014-02-23T23:22:33</td></tr>
	<tr><td>dataSourceURL</td><td>URL</td><td>URL that points to an input Market Data file.</td><td>http://www.cse.unsw.edu.au/~hpaik/9322/assignments/common/files_csv_spec/FinDataSimple.csv</td></tr>
</table>
<br/>The result of this operation is an eventSetId string which can be used to refer to the filtered data file.

<h3>Operation: downloadFile</h3>
<table width="85%" border=1>
	<tr><td>Name</td><td>Type</td><td>Description</td><td>Example</td></tr>
	<tr><td>eventSetId</td><td>String</td><td>An ID that refers to the filtered Market Data file.</td><td>123</td></tr>
</table>
<br/>The result of this operation is a URL to download the filtered file.

<h2>Error Codes</h2>
<h3>Operation: importMarketData</h3>
<table width="85%" border=1>
	<tr><td>Fault Type</td><td>Fault Message</td><td>Cause</td><td>Resolution</td></tr>
	<tr><td>InvalidEventSetId</td><td>EventSetId does not exist</td><td>Market Data file corresponding to EventSetId does not exist.</td><td>Please check whether you have the correct EventSetId.</td></tr>
	<tr><td>InvalidURL</td><td>Invalid URL</td><td>The format or the content of the URL is not correct</td><td>Please input the correct URL</td></tr>
	<tr><td>InvalidSECCode</td><td>Invalid security code</td><td>The security code format is not correct</td><td>Please input the correct security code</td></tr>
	<tr><td>Invalid Date input</td><td>Invalid date value: wrong type: </td><td>Input date format is not correct</td><td>Please input the correct date format</td></tr>
	<tr><td>ProgramError</td><td>Error reading or writing file</td><td>There was an error reading or writing files on the server.</td><td>Please contact the administrator to fix the problem.</td></tr>
	<tr><td>ProgramError</td><td>Start date must be before the end date</td><td>There is a conflict between input dates</td><td>Please input the correct dates</td></tr>
	
</table>

</body>
</html>