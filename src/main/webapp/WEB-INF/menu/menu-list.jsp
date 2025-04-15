<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Restaurant Menu</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; }
        h1 { color: #2c3e50; }
        .menu-container { display: flex; flex-wrap: wrap; }
        .menu-item {
            border: 1px solid #ddd;
            border-radius: 8px;
            margin: 10px;
            padding: 15px;
            width: 300px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .menu-item h3 { color: #2c3e50; margin-top: 0; }
        .price { font-weight: bold; color: #e74c3c; }
        .category { color: #7f8c8d; font-style: italic; }
        .availability {
            display: inline-block;
            padding: 3px 8px;
            border-radius: 4px;
            font-size: 12px;
            margin-top: 5px;
        }
        .available { background-color: #2ecc71; color: white; }
        .out_of_order { background-color: #e74c3c; color: white; }
        .admin-controls { margin-top: 10px; display: flex; gap: 10px; }
        .button {
            display: inline-block;
            padding: 5px 10px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            border: none;
            cursor: pointer;
        }
        .delete { background-color: #e74c3c; }
        .add { background-color: #2ecc71; margin-bottom: 20px; }
        .filter-form {
            margin-bottom: 20px;
            padding: 15px;
            background-color: #f9f9f9;
            border-radius: 8px;
        }
        .notification {
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 4px;
            background-color: #f8d7da;
            color: #721c24;
        }
    </style>
</head>
<body>
<h1>Restaurant Menu</h1>

<c:if test="${not empty NOTIFICATION}">
    <div class="notification">${NOTIFICATION}</div>
</c:if>

<!-- Filter Form -->
<div class="filter-form">
    <form action="${pageContext.request.contextPath}/menu" method="get">
        <input type="hidden" name="action" value="filter">

        <label for="filterCategory">Filter by Category:</label>
        <select name="filterCategory" id="filterCategory">
            <option value="">All Categories</option>
            <option value="meals" ${filterCategory == 'meals' ? 'selected' : ''}>Meals</option>
            <option value="snacks" ${filterCategory == 'snacks' ? 'selected' : ''}>Snacks</option>
            <option value="sweets" ${filterCategory == 'sweets' ? 'selected' : ''}>Sweets</option>
            <option value="drinks" ${filterCategory == 'drinks' ? 'selected' : ''}>Drinks</option>
        </select>

        <label for="filterAvailability">Filter by Availability:</label>
        <select name="filterAvailability" id="filterAvailability">
            <option value="">All Items</option>
            <option value="available" ${filterAvailability == 'available' ? 'selected' : ''}>Available</option>
            <option value="out_of_order" ${filterAvailability == 'out_of_order' ? 'selected' : ''}>Out of Order</option>
        </select>

        <button type="submit" class="button">Apply Filter</button>
    </form>
</div>

<!-- Admin-only Add Item Button -->
<c:if test="${sessionScope.user.userRole == 'admin'}">
    <a href="${pageContext.request.contextPath}/menu?action=add" class="button add">Add New Menu Item</a>
</c:if>

<!-- Menu Items Display -->
<div class="menu-container">
    <c:forEach var="item" items="${menuItems}">
        <div class="menu-item">
            <h3>${item.foodName}</h3>
            <p>${item.foodDescription}</p>
            <p class="price">$${item.foodPrice}</p>
            <p class="category">Category: ${item.foodCategory}</p>
            <span class="availability ${item.foodAvailability}">${item.foodAvailability}</span>

            <!-- Admin-only Controls -->
            <c:if test="${sessionScope.user.userRole == 'admin'}">
                <div class="admin-controls">
                    <a href="${pageContext.request.contextPath}/menu?action=edit&food_id=${item.foodId}" class="button">Edit</a>
                    <form action="${pageContext.request.contextPath}/menu" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="food_id" value="${item.foodId}">
                        <button type="submit" class="button delete" onclick="return confirm('Are you sure you want to delete this item?')">Delete</button>
                    </form>
                </div>
            </c:if>
        </div>
    </c:forEach>
</div>

<!-- Navigation Links -->
<div style="margin-top: 20px;">
    <a href="${pageContext.request.contextPath}/user/profile" class="button">Back to Profile</a>
    <a href="${pageContext.request.contextPath}/user/logout" class="button">Logout</a>
</div>
</body>
</html>