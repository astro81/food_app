<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit Menu Item</title>
    <style>
        .error { color: red; }
        form {
            max-width: 500px;
            margin: 20px auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        label {
            display: block;
            margin-bottom: 10px;
        }
        input, select {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<h1>Edit Menu Item</h1>

<c:if test="${not empty errorMessage}">
    <p class="error">${errorMessage}</p>
    <c:remove var="errorMessage" scope="session"/>
</c:if>

<form action="${pageContext.request.contextPath}/menu" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="food_id" value="${menuItem.foodId}">

    <label>Name:
        <input type="text" name="food_name" value="${menuItem.foodName}" required>
    </label>

    <label>Description:
        <input type="text" name="food_description" value="${menuItem.foodDescription}">
    </label>

    <label>Price:
        <input type="number" step="0.01" name="food_price" value="${menuItem.foodPrice}" required>
    </label>

    <label>Category:
        <select name="food_category" required>
            <option value="meals" ${menuItem.foodCategory == 'meals' ? 'selected' : ''}>Meals</option>
            <option value="snacks" ${menuItem.foodCategory == 'snacks' ? 'selected' : ''}>Snacks</option>
            <option value="sweets" ${menuItem.foodCategory == 'sweets' ? 'selected' : ''}>Sweets</option>
            <option value="drinks" ${menuItem.foodCategory == 'drinks' ? 'selected' : ''}>Drinks</option>
        </select>
    </label>

    <label>Availability:
        <select name="food_availability" required>
            <option value="available" ${menuItem.foodAvailability == 'available' ? 'selected' : ''}>Available</option>
            <option value="out_of_order" ${menuItem.foodAvailability == 'out_of_order' ? 'selected' : ''}>Out of Order</option>
        </select>
    </label>

    <button type="submit">Update Item</button>
    <a href="${pageContext.request.contextPath}/menu" style="margin-left: 10px;">Cancel</a>
</form>
</body>
</html>