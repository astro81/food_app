<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Order Preview</title>
</head>
<body>
<h1>Your Order</h1>
<table border="1">
    <tr>
        <th>Item</th>
        <th>Price</th>
        <th>Quantity</th>
        <th>Subtotal</th>
    </tr>
    <c:forEach items="${order.itemsWithQuantities}" var="entry">
        <tr>
            <td>${entry.key.foodName}</td>
            <td><fmt:formatNumber value="${entry.key.foodPrice}" type="currency"/></td>
            <td>${entry.value}</td>
            <td><fmt:formatNumber value="${order.subtotals[entry.key]}" type="currency"/></td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="3"><strong>Total:</strong></td>
        <td><strong><fmt:formatNumber value="${order.total}" type="currency"/></strong></td>
    </tr>
</table>

<form action="confirm-order" method="post">
    <button type="submit">Confirm Order</button>
</form>
<a href="menu">Back to Menu</a>
</body>
</html>