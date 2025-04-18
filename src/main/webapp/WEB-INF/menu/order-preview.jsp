<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/templates/navbar.jsp" />

<!DOCTYPE html>
<html>
<head>
    <title>Order Preview</title>
    <style>
        .order-container { max-width: 800px; margin: 20px auto; padding: 20px; border: 1px solid #ddd; }
        .order-header { text-align: center; margin-bottom: 20px; }
        .order-items { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
        .order-items th, .order-items td { padding: 10px; border-bottom: 1px solid #ddd; text-align: left; }
        .order-total { text-align: right; font-weight: bold; font-size: 1.2em; }
        .btn { padding: 8px 12px; text-decoration: none; background: #4CAF50; color: white; border-radius: 4px; }
        .btn-confirm { background-color: #2196F3; }
    </style>
</head>
<body>
<div class="order-container">
    <div class="order-header">
        <h1>Order Preview</h1>
        <p>Review your order before confirming</p>
    </div>

    <table class="order-items">
        <thead>
        <tr>
            <th>Item</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Subtotal</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${order.items}" var="item">
            <tr>
                <td>${item.foodName}</td>
                <td>$${item.foodPrice}</td>
                <td>1</td>
                <td>$${item.foodPrice}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="order-total">
        <p>Total: $${order.items.stream().map(item -> item.getFoodPrice()).sum()}</p>
    </div>

    <div style="text-align: center;">
        <form action="${pageContext.request.contextPath}/confirm-order" method="post">
            <button type="submit" class="btn btn-confirm">Confirm Order</button>
            <a href="${pageContext.request.contextPath}/menu" class="btn">Back to Menu</a>
        </form>
    </div>
</div>
</body>
</html>