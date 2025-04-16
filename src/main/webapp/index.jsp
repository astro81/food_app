<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%@ include file="WEB-INF/templates/navbar.jsp" %>--%>
<c:import url="WEB-INF/templates/navbar.jsp" />

<!DOCTYPE html>
<html>
<head>
    <title>Home Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            margin-top: 50px;
        }
        .login-link {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 15px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .login-link:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<h1><%= "Welcome to Our Website!" %></h1>
</body>
</html>