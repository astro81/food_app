<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit Menu Item</title>
    <style>
        .error { color: red; }
    </style>
</head>
<body>
<h1>Edit Menu Item</h1>

<c:if test="${not empty errorMessage}">
    <p class="error">${errorMessage}</p>
    <c:remove var="errorMessage" scope="session"/>
</c:if>

<form action="menu" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="food_id" value="${menuItem.foodId}">

    <label>Name:
        <input type="text" name="food_name" value="${menuItem.foodName}" required>
    </label><br>

    <label>Description:
        <input type="text" name="food_description" value="${menuItem.foodDescription}">
    </label><br>

    <label>Price:
        <input type="number" step="0.01" name="food_price" value="${menuItem.foodPrice}" required>
    </label><br>

    <label>Category:
        <select name="food_category" required>
            <option value="meals" ${menuItem.foodCategory == 'meals' ? 'selected' : ''}>Meals</option>
            <option value="snacks" ${menuItem.foodCategory == 'snacks' ? 'selected' : ''}>Snacks</option>
            <option value="sweets" ${menuItem.foodCategory == 'sweets' ? 'selected' : ''}>Sweets</option>
            <option value="drinks" ${menuItem.foodCategory == 'drinks' ? 'selected' : ''}>Drinks</option>
        </select>
    </label><br>

    <label>Availability:
        <select name="food_availability" required>
            <option value="available" ${menuItem.foodAvailability == 'available' ? 'selected' : ''}>Available</option>
            <option value="out_of_order" ${menuItem.foodAvailability == 'out_of_order' ? 'selected' : ''}>Out of Order</option>
        </select>
    </label><br>

    <button type="submit">Update Item</button>
    <a href="menu">Cancel</a>
</form>
</body>
</html>