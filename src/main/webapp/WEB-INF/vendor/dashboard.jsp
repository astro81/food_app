<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Vendor Dashboard</title>
</head>
<body>
<h1>Vendor Dashboard</h1>
<nav>
    <ul>
        <li><a href="${pageContext.request.contextPath}/menu">Manage Menu</a></li>
        <li><a href="${pageContext.request.contextPath}/vendor/orders">View Orders</a></li>
    </ul>
</nav>
</body>
</html>