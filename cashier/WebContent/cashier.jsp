<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cashier</title>
</head>
<body>
<h1>Cashier Application</h1>
<form action="CashierServlet"><input type="hidden" name="page" value="new-order"><input type="submit" value="New Order"></form>
<p>
<table>
<c:forEach items="${orders}" var="order">
    <tr>
        <td><a href="CashierServlet?page=view-order&id=${order.id}">Order ${order.id}</a>
        <c:if test="${order.p_status == 'yes'}">
        <br><a href="CashierServlet?page=view-payment&id=${order.id}">&nbsp;- Payment ${order.id}</a>
        </c:if>
        </td>
        <td valign="top"><form action="CashierServlet"><input type="hidden" name="id" value="${order.id}"><input type="hidden" name="action" value="cancel-order"><input type="hidden" name="page" value="response"><input type="submit" value="Cancel"></form></td>
        <td valign="top"><form action="CashierServlet"><input type="hidden" name="id" value="${order.id}"><input type="hidden" name="page" value="update-order"><input type="submit" value="Update"></form></td>
        <td valign="top"><form action="CashierServlet"><input type="hidden" name="id" value="${order.id}"><input type="hidden" name="page" value="new-payment"><input type="submit" value="Pay"></form></td>
        <td valign="top"><form action="CashierServlet"><input type="hidden" name="id" value="${order.id}"><input type="hidden" name="action" value="options-order"><input type="hidden" name="page" value="response"><input type="submit" value="Options"></form></td>
        <td valign="top">${order.type}</td>
        <td valign="top">${order.additions}</td>
        <td valign="top">${order.p_status}</td>
        <td valign="top">${order.c_status}</td>
    </tr>
</c:forEach>
</table>

</body>
</html>