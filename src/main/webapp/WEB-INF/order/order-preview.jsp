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
    <c:forEach items="${order.items}" var="orderItem">
        <tr>
            <td>${orderItem.item.foodName}</td>
            <td><fmt:formatNumber value="${orderItem.item.foodPrice}" type="currency"/></td>
            <td>${orderItem.quantity}</td>
            <td><fmt:formatNumber value="${orderItem.item.foodPrice * orderItem.quantity}" type="currency"/></td>
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