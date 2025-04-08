<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
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
        h2 {
            color: #333;
        }
        .user-info {
            margin: 20px 0;
            padding: 15px;
            background-color: #f9f9f9;
            border-radius: 4px;
        }
        .logout-btn {
            background-color: #d9534f;
            color: white;
            padding: 8px 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
        }
        .logout-btn:hover {
            background-color: #c9302c;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Welcome to Your Account</h2>

    <%-- Check if user is logged in --%>
    <% if(session.getAttribute("user") == null) { %>
    <p>You are not logged in. <a href="login.jsp">Please login</a></p>
    <% } else {
        model.UserModel user = (model.UserModel) session.getAttribute("user");
    %>
    <div class="user-info">
        <p><strong>Name:</strong> <%= user.getUserName() %></p>
        <p><strong>Email:</strong> <%= user.getUserMail() %></p>
        <p><strong>Phone:</strong> <%= user.getUserPhone() %></p>
        <p><strong>Address:</strong> <%= user.getUserAddress() %></p>
    </div>

    <a href="${pageContext.request.contextPath}/user/logout" class="logout-btn">Logout</a>
    <% } %>
</div>
</body>
</html>
