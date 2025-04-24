<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/templates/navbar.jsp" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2, h3 {
            color: #333;
        }
        .order-header {
            margin-bottom: 20px;
            padding: 15px;
            background-color: #f9f9f9;
            border-radius: 4px;
        }
        .btn {
            padding: 8px 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            margin-right: 10px;
        }
        .btn-primary {
            background-color: #337ab7;
            color: white;
        }
        .btn-primary:hover {
            background-color: #286090;
        }
        .items-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        .items-table th, .items-table td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }
        .items-table th {
            background-color: #f5f5f5;
        }
        .items-table tfoot {
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Order Details</h2>

    <div class="order-header">
        <p><strong>Order ID:</strong> ${order.orderId}</p>
        <p><strong>Date:</strong> <fmt:formatDate value="${order.orderDate}" pattern="MMMM dd, yyyy HH:mm" /></p>
        <p><strong>Status:</strong> ${order.status}</p>
    </div>

    <h3>Order Items</h3>
    <table class="items-table">
        <thead>
        <tr>
            <th>Item</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Subtotal</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${order.itemsWithQuantities}" var="entry">
            <tr>
                <td>${entry.key.foodName}</td>
                <td>$<fmt:formatNumber value="${entry.key.foodPrice}" pattern="#,##0.00" /></td>
                <td>${entry.value}</td>
                <td>$<fmt:formatNumber value="${order.subtotals[entry.key]}" pattern="#,##0.00" /></td>
            </tr>
        </c:forEach>
        </tbody>
        <tfoot>
        <tr>
            <td colspan="3" style="text-align: right;"><strong>Total:</strong></td>
            <td>$<fmt:formatNumber value="${order.total}" pattern="#,##0.00" /></td>
        </tr>
        </tfoot>
    </table>

    <div style="margin-top: 20px;">
        <a href="${pageContext.request.contextPath}/user/profile" class="btn btn-primary">Back to Profile</a>
    </div>
</div>
</body>
</html>