<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Menu Management</title>
    <style>
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 8px; text-align: left; border-bottom: 1px solid #ddd; }
        .success { color: green; }
        .error { color: red; }
    </style>
</head>
<body>
<h1>Menu Management</h1>

<!-- Display success/error messages -->
<c:if test="${not empty successMessage}">
    <p class="success">${successMessage}</p>
    <c:remove var="successMessage" scope="session"/>
</c:if>
<c:if test="${not empty errorMessage}">
    <p class="error">${errorMessage}</p>
    <c:remove var="errorMessage" scope="session"/>
</c:if>

<!-- Add New Item Form -->
<h2>Add New Menu Item</h2>
<form action="menu" method="post">
    <input type="hidden" name="action" value="create">
    <label>Name: <input type="text" name="food_name" required></label>
    <label>Description: <input type="text" name="food_description"></label>
    <label>Price: <input type="number" step="0.01" name="food_price" required></label>
    <label>Category:
        <select name="food_category" required>
            <option value="meals">Meals</option>
            <option value="snacks">Snacks</option>
            <option value="sweets">Sweets</option>
            <option value="drinks">Drinks</option>
        </select>
    </label>
    <label>Availability:
        <select name="food_availability" required>
            <option value="available">Available</option>
            <option value="out_of_order">Out of Order</option>
        </select>
    </label>
    <button type="submit">Add Item</button>
</form>

<!-- Menu Items Table -->
<h2>Current Menu Items</h2>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Description</th>
        <th>Price</th>
        <th>Category</th>
        <th>Availability</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${menuItems}" var="item">
        <tr>
            <td>${item.foodId}</td>
            <td>${item.foodName}</td>
            <td>${item.foodDescription}</td>
            <td>$${item.foodPrice}</td>
            <td>${item.foodCategory}</td>
            <td>${item.foodAvailability}</td>
            <td>
                <a href="menu?action=edit&food_id=${item.foodId}">Edit</a>
                <form action="menu" method="post" style="display: inline;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="food_id" value="${item.foodId}">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>