<%-- WEB-INF/order/order-preview.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Order Preview</title>
    <style>
        /* Base Styles */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            background-color: #f8f9fa;
            color: #333;
            line-height: 1.6;
            padding: 20px;
        }

        .container {
            max-width: 1000px;
            margin: 0 auto;
            padding: 20px;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
        }

        h1 {
            color: #2c3e50;
            margin-bottom: 30px;
            text-align: center;
            font-weight: 600;
        }

        /* Table Styles */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 30px;
            background-color: white;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
            border-radius: 8px;
            overflow: hidden;
        }

        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #e0e0e0;
        }

        th {
            background-color: #10b981;
            color: white;
            font-weight: 500;
            text-transform: uppercase;
            font-size: 14px;
        }

        tr:nth-child(even) {
            background-color: #f8f9fa;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        /* Total Row */
        tr.total-row {
            background-color: #e8f5e9;
            font-weight: bold;
        }

        /* Button Styles */
        .action-buttons {
            display: flex;
            justify-content: space-between;
            margin-top: 30px;
        }

        .btn {
            padding: 12px 24px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            font-weight: 500;
            transition: all 0.3s ease;
            text-decoration: none;
            display: inline-block;
            text-align: center;
        }

        .btn-confirm {
            background-color: #10b981;
            color: white;
        }

        .btn-confirm:hover {
            background-color: #0d9e6e;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(16, 185, 129, 0.3);
        }

        .btn-back {
            background-color: #f3f4f6;
            color: #333;
        }

        .btn-back:hover {
            background-color: #e5e7eb;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        /* Responsive Table */
        @media (max-width: 768px) {
            table {
                display: block;
                overflow-x: auto;
            }

            th, td {
                padding: 10px;
            }

            .action-buttons {
                flex-direction: column;
                gap: 15px;
            }

            .btn {
                width: 100%;
            }
        }

        /* Price Formatting */
        .price {
            font-weight: 500;
            color: #10b981;
        }

        .total-price {
            font-size: 18px;
            color: #2c3e50;
        }

        /* Empty State */
        .empty-order {
            text-align: center;
            padding: 40px;
            color: #666;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Your Order Summary</h1>

    <c:choose>
        <c:when test="${not empty order.itemsWithQuantities}">
            <table>
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
                        <td class="price"><fmt:formatNumber value="${entry.key.foodPrice}" type="currency"/></td>
                        <td>${entry.value}</td>
                        <td class="price"><fmt:formatNumber value="${order.subtotals[entry.key]}" type="currency"/></td>
                    </tr>
                </c:forEach>
                <tr class="total-row">
                    <td colspan="3"><strong>Total:</strong></td>
                    <td class="total-price"><strong><fmt:formatNumber value="${order.total}" type="currency"/></strong></td>
                </tr>
                </tbody>
            </table>

            <div class="action-buttons">
                <form action="confirm-order" method="post">
                    <button type="submit" class="btn btn-confirm">Confirm Order</button>
                </form>
                <a href="menu" class="btn btn-back">Back to Menu</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="empty-order">
                <h2>Your order is empty</h2>
                <p>You haven't added any items to your order yet.</p>
                <a href="menu" class="btn btn-back" style="margin-top: 20px;">Browse Menu</a>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>