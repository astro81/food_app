<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Registration</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 500px;
            margin: 0 auto;
            background: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            color: #333;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"],
        input[type="email"],
        input[type="password"],
        textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
            font-size: 16px;
        }
        button:hover {
            background-color: #45a049;
        }
        .notification {
            margin: 15px 0;
            padding: 10px;
            border-radius: 4px;
            text-align: center;
        }
        .success {
            background-color: #dff0d8;
            color: #3c763d;
        }
        .error {
            background-color: #f2dede;
            color: #a94442;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>User Registration</h2>

    <%-- Notification Message --%>
    <% if(request.getAttribute("NOTIFICATION") != null) { %>
    <div class="notification <%= request.getAttribute("NOTIFICATION").toString().contains("Success") ? "success" : "error" %>">
        <%= request.getAttribute("NOTIFICATION") %>
    </div>
    <% } %>

    <form action="register" method="post">
        <div class="form-group">
            <label for="userName">Full Name:</label>
            <input type="text" id="userName" name="user_name" required>
        </div>

        <div class="form-group">
            <label for="userMail">Email:</label>
            <input type="email" id="userMail" name="user_mail" required>
        </div>

        <div class="form-group">
            <label for="userPasswd">Password:</label>
            <input type="password" id="userPasswd" name="user_passwd" required>
        </div>

        <div class="form-group">
            <label for="userPhone">Phone Number:</label>
            <input type="text" id="userPhone" name="user_phone" required>
        </div>

        <div class="form-group">
            <label for="userAddress">Address:</label>
            <textarea id="userAddress" name="user_address" rows="3" required></textarea>
        </div>

        <div class="login-link" style="text-align: center; margin-top: 15px;">
            Already have an account? <a href="login.jsp">Login here</a>
        </div>

        <button type="submit">Register</button>
    </form>
</div>
</body>
</html>