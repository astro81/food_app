<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Navigation Bar</title>
    <style>
        * {
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        .navbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: white;
            padding: 10px 20px;
            border-bottom: 1px solid #ddd;
        }

        .logo {
            height: 50px;
        }

        .logo img {
            height: 100%;
        }

        .nav-links {
            display: flex;
            justify-content: right;
            flex-grow: 1;
        }

        .nav-links a {
            color: #333;
            text-decoration: none;
            margin: 0 20px;
            font-size: 20px;
            font-weight: 500;
        }

        .cart {
            font-size: 24px;
        }

        .cart a {
            color: #000;
            text-decoration: none;
        }
        .login{
            background-color: #EBD9c7;
            color: black;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
            font-size: 16px;
        }
        .login:hover{
            background-color: #A68667 ;
            color: black;
        }
    </style>
</head>
<body>
<nav class="navbar">
    <div class="logo">
        <img src="${pageContext.request.contextPath}/images/logo.png" alt="Order Now Logo" style="border-radius: 8px;">
    </div>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/index.jsp">Home</a>
        <a href="${pageContext.request.contextPath}/menu">Menu</a>
        <a href="${pageContext.request.contextPath}/profile.jsp">Profile</a>
        <a href="${pageContext.request.contextPath}/user/login"><button class="login">Login</button></a>
    </div>
    <div class="cart">
        <a href="${pageContext.request.contextPath}/cart.jsp">🛒</a>
    </div>
</nav>

<!-- Page content goes here -->

</body>
</html>