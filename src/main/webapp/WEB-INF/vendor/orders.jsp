<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Vendor Orders</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<%@ include file="/WEB-INF/components/navbar.jsp" %>

<div class="container">
    <h1>Your Orders</h1>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty orders}">
            <p>No orders found for your items.</p>
        </c:when>
        <c:otherwise>
            <table class="orders-table">
                <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Date</th>
                    <th>Customer</th>
                    <th>Items</th>
                    <th>Quantity</th>
                    <th>Subtotal</th>
                    <th>Status</th>
                    <th>Total</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${orders}" var="order">
                    <c:if test="${not empty order.itemsWithQuantities}">
                        <tr>
                            <td>${order.orderId}</td>
                            <td>${order.orderDate}</td>
                            <td>Customer #${order.userId}</td>
                            <td>
                                <ul>
                                    <c:forEach items="${order.itemsWithQuantities}" var="item">
                                        <c:if test="${not empty item.menuItem}">
                                            <li>${item.menuItem.foodName}</li>
                                        </c:if>
                                    </c:forEach>
                                </ul>
                            </td>
                            <td>
                                <ul>
                                    <c:forEach items="${order.itemsWithQuantities}" var="item">
                                        <c:if test="${not empty item.menuItem}">
                                            <li>${item.quantity}</li>
                                        </c:if>
                                    </c:forEach>
                                </ul>
                            </td>
                            <td>
                                <ul>
                                    <c:forEach items="${order.itemsWithQuantities}" var="item">
                                        <c:if test="${not empty item.menuItem}">
                                            <li>$${item.subtotal}</li>
                                        </c:if>
                                    </c:forEach>
                                </ul>
                            </td>
                            <td>${order.status}</td>
                            <td>$${order.total}</td>
                        </tr>
                    </c:if>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>

    <a href="${pageContext.request.contextPath}/vendor/dashboard" class="btn">Back to Dashboard</a>
</div>

</body>
</html>