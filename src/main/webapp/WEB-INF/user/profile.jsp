<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/templates/navbar.jsp" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
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
        .user-info {
            margin: 20px 0;
            padding: 15px;
            background-color: #f9f9f9;
            border-radius: 4px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"], input[type="password"], textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
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
        .btn-danger {
            background-color: #d9534f;
            color: white;
        }
        .btn-danger:hover {
            background-color: #c9302c;
        }
        .notification {
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 4px;
        }
        .success {
            background-color: #dff0d8;
            color: #3c763d;
        }
        .error {
            background-color: #f2dede;
            color: #a94442;
        }
        .edit-form {
            display: none;
            margin-top: 20px;
            padding: 15px;
            background-color: #f0f0f0;
            border-radius: 4px;
        }

        /* Order history styles */
        .order-history {
            margin: 30px 0;
        }
        .order-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        .order-table th, .order-table td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }
        .order-table th {
            background-color: #f5f5f5;
        }
        .order-row:hover {
            background-color: #f9f9f9;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>User Profile</h2>

    <%-- Notification area --%>
    <% if (request.getAttribute("NOTIFICATION") != null) { %>
    <div class="notification ${requestScope.NOTIFICATION.contains('success') ? 'success' : 'error'}">
        <%= request.getAttribute("NOTIFICATION") %>
    </div>
    <% } %>
    <% if (request.getParameter("notification") != null) { %>
    <div class="notification success">
        <%= request.getParameter("notification") %>
    </div>
    <% } %>

    <%-- Check if user is logged in --%>
    <% if(session.getAttribute("user") == null) { %>
    <p>You are not logged in. <a href="${pageContext.request.contextPath}/user/login">Please login</a></p>
    <% } else {
        model.UserModel user = (model.UserModel) session.getAttribute("user");
    %>
    <div class="user-info">
        <p><strong>Name:</strong> <span id="display-name"><%= user.getUserName() %></span></p>
        <p><strong>Email:</strong> <%= user.getUserMail() %></p>
        <p><strong>Phone:</strong> <span id="display-phone"><%= user.getUserPhone() %></span></p>
        <p><strong>Address:</strong> <span id="display-address"><%= user.getUserAddress() %></span></p>
    </div>

    <button id="edit-btn" class="btn btn-primary">Edit Profile</button>
    <a href="${pageContext.request.contextPath}/user/logout" class="btn btn-danger">Logout</a>

    <div id="edit-form" class="edit-form">
        <form action="${pageContext.request.contextPath}/user/profile" method="post">
            <input type="hidden" name="action" value="update">

            <div class="form-group">
                <label for="user_name">Name:</label>
                <input type="text" id="user_name" name="user_name" value="<%= user.getUserName() %>" required>
            </div>

            <div class="form-group">
                <label for="user_phone">Phone:</label>
                <input type="text" id="user_phone" name="user_phone" value="<%= user.getUserPhone() %>">
            </div>

            <div class="form-group">
                <label for="user_address">Address:</label>
                <textarea id="user_address" name="user_address"><%= user.getUserAddress() %></textarea>
            </div>

            <div class="form-group">
                <label for="user_passwd">New Password (leave blank to keep current):</label>
                <input type="password" id="user_passwd" name="user_passwd">
            </div>

            <button type="submit" class="btn btn-primary">Save Changes</button>
            <button type="button" id="cancel-btn" class="btn btn-danger">Cancel</button>
        </form>

        <hr>

        <form action="${pageContext.request.contextPath}/user/profile" method="post"
              onsubmit="return confirm('Are you sure you want to delete your account? This cannot be undone.');">
            <input type="hidden" name="action" value="delete">
            <button type="submit" class="btn btn-danger">Delete Account</button>
        </form>
    </div>

    <!-- Order History Section -->
    <div class="order-history">
        <h3>Order History</h3>
        <c:choose>
            <c:when test="${empty orderHistory}">
                <p>You don't have any completed orders yet.</p>
            </c:when>
            <c:otherwise>
                <table class="order-table">
                    <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Date</th>
                        <th>Total</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="order" items="${orderHistory}">
                        <tr class="order-row">
                            <td>${order.orderId}</td>
                            <td><fmt:formatDate value="${order.orderDate}" pattern="MMM dd, yyyy HH:mm" /></td>
                            <td>$<fmt:formatNumber value="${order.total}" pattern="#,##0.00" /></td>
                            <td>${order.status}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/order/details?orderId=${order.orderId}"
                                   class="btn btn-primary">View Details</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>

    <script>
        document.getElementById('edit-btn').addEventListener('click', function() {
            document.getElementById('edit-form').style.display = 'block';
            this.style.display = 'none';
        });

        document.getElementById('cancel-btn').addEventListener('click', function() {
            document.getElementById('edit-form').style.display = 'none';
            document.getElementById('edit-btn').style.display = 'inline-block';
        });
    </script>
    <% } %>
</div>
</body>
</html>