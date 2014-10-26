<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cashier - New Order</title>
</head>
<body>
<h1>New Order</h1>
<form action="CashierServlet" method="post">
<input type="hidden" name="action" value="new-order">
<input type="hidden" name="page" value="response">
<table>
<tr><td>type: </td><td><input type="text" name="type"><br></td></tr>
<tr><td>additions: </td><td><input type="text" name="additions"><br></td></tr>
<tr><td colspan="2"><input type="submit" value="Create"></td></tr>
</table>
</form>
</body>
</html>