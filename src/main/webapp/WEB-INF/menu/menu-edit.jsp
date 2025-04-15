<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${empty menuItem ? 'Add New Menu Item' : 'Edit Menu Item'}</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; }
        h1 { color: #2c3e50; }
        .form-container {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
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
        input[type="number"],
        textarea,
        select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        textarea {
            height: 100px;
        }
        .button-group {
            margin-top: 20px;
            display: flex;
            gap: 10px;
        }
        .button {
            display: inline-block;
            padding: 8px 16px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            border: none;
            cursor: pointer;
        }
        .submit { background-color: #2ecc71; }
        .cancel { background-color: #7f8c8d; }
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
<h1>${empty menuItem ? 'Add New Menu Item' : 'Edit Menu Item'}</h1>

<c:if test="${not empty NOTIFICATION}">
    <div class="notification">${NOTIFICATION}</div>
</c:if>

<div class="form-container">
    <form action="${pageContext.request.contextPath}/menu" method="post">
        <input type="hidden" name="action" value="${empty menuItem ? 'add' : 'edit'}">

        <c:if test="${not empty menuItem}">
            <input type="hidden" name="food_id" value="${menuItem.foodId}">
        </c:if>

        <div class="form-group">
            <label for="food_name">Food Name:</label>
            <input type="text" id="food_name" name="food_name" value="${menuItem.foodName}" required>
        </div>

        <div class="form-group">
            <label for="food_description">Description:</label>
            <textarea id="food_description" name="food_description" required>${menuItem.foodDescription}</textarea>
        </div>

        <div class="form-group">
            <label for="food_price">Price ($):</label>
            <input type="number" id="food_price" name="food_price" value="${menuItem.foodPrice}" step="0.01" min="0" required>
        </div>

        <div class="form-group">
            <label for="food_category">Category:</label>
            <select id="food_category" name="food_category" required>
                <option value="" disabled ${empty menuItem ? 'selected' : ''}>Select a category</option>
                <option value="meals" ${menuItem.foodCategory == 'meals' ? 'selected' : ''}>Meals</option>
                <option value="snacks" ${menuItem.foodCategory == 'snacks' ? 'selected' : ''}>Snacks</option>
                <option value="sweets" ${menuItem.foodCategory == 'sweets' ? 'selected' : ''}>Sweets</option>
                <option value="drinks" ${menuItem.foodCategory == 'drinks' ? 'selected' : ''}>Drinks</option>
            </select>
        </div>

        <div class="form-group">
            <label for="food_availability">Availability:</label>
            <select id="food_availability" name="food_availability" required>
                <option value="available" ${empty menuItem || menuItem.foodAvailability == 'available' ? 'selected' : ''}>Available</option>
                <option value="out_of_order" ${menuItem.foodAvailability == 'out_of_order' ? 'selected' : ''}>Out of Order</option>
            </select>
        </div>

        <div class="button-group">
            <button type="submit" class="button submit">Save</button>
            <a href="${pageContext.request.contextPath}/menu" class="button cancel">Cancel</a>
        </div>
    </form>
</div>
</body>
</html>