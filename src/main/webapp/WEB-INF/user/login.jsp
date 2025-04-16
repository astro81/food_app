<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/templates/navbar.jsp" />

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>User Login</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f4;
      margin: 0;
      padding: 20px;
    }
    .container {
      max-width: 400px;
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
    input[type="email"],
    input[type="password"] {
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
    .error {
      background-color: #f2dede;
      color: #a94442;
    }
    .register-link {
      text-align: center;
      margin-top: 15px;
    }
  </style>
</head>
<body>
<div class="container">
  <h2>User Login</h2>

  <%-- Notification Message --%>
  <% if(request.getAttribute("NOTIFICATION") != null) { %>
  <div class="notification error">
    <%= request.getAttribute("NOTIFICATION") %>
  </div>
  <% } %>

  <form action="${pageContext.request.contextPath}/user/login" method="post">
    <div class="form-group">
      <label for="email">Email:</label>
      <input type="email" id="email" name="user_mail" required>
    </div>

    <div class="form-group">
      <label for="password">Password:</label>
      <input type="password" id="password" name="user_passwd" required>
    </div>

    <button type="submit">Login</button>
  </form>

  <div class="register-link">
    Don't have an account?
    <a href="${pageContext.request.contextPath}/user/register">Register here</a>
  </div>
</div>
</body>
</html>
