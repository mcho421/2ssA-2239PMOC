<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Barista</title>
</head>
<body>
<h1>Barista Application</h1>
<button onclick="location.reload()">Refresh</button>
<p>
<table>
<c:forEach items="${orders}" var="order">
    <tr>
        <td><a href="BaristaServlet?page=view-order&id=${order.id}">Order ${order.id}</a></td>
        <td valign="top"><form action="BaristaServlet"><input type="hidden" name="id" value="${order.id}"><input type="hidden" name="action" value="prepare-order"><input type="hidden" name="page" value="response"><input type="submit" value="Prepare"></form></td>
        <td valign="top"><form action="BaristaServlet"><input type="hidden" name="id" value="${order.id}"><input type="hidden" name="page" value="view-payment"><input type="submit" value="Check Payment"></form></td>
        <td valign="top"><form action="BaristaServlet"><input type="hidden" name="id" value="${order.id}"><input type="hidden" name="action" value="release-order"><input type="hidden" name="page" value="response"><input type="submit" value="Release"></form></td>
        <td valign="top">${order.type}</td>
        <td valign="top">${order.additions}</td>
        <td valign="top">${order.p_status}</td>
        <td valign="top">${order.c_status}</td>
    </tr>
</c:forEach>
</table>

<script type="text/javascript">
  setTimeout(function(){
    location = ''
  },4000)
</script>

</body>
</html>