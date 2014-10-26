<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cashier - Pay for Order ${id}</title>
</head>
<body>
<h1>Pay for Order ${id}</h1>
<form action="CashierServlet" method="post">
<input type="hidden" name="action" value="new-payment">
<input type="hidden" name="page" value="response">
<input type="hidden" name="id" value="${id}">
<table>
<tr><td>Payment Type: </td><td><input type="text" name="payment_type"><br></td></tr>
<tr><td>Card Details: </td><td><input type="text" name="card_details"><br></td></tr>
<tr><td colspan="2"><input type="submit" value="Pay"></td></tr>
</table>
</form>
</body>
</html>