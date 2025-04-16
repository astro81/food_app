<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${empty menuItem ? 'Add New Menu Item' : 'Edit Menu Item'}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/menu.css">
</head>
<body>
<h1>${empty menuItem ? 'Add New Menu Item' : 'Edit Menu Item'}</h1>

<%@ include file="/WEB-INF/menu/fragments/notification.jsp" %>

<div class="form-container">
    <form action="${pageContext.request.contextPath}/menu/${empty menuItem ? 'add' : 'edit'}" method="post">
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
            <a href="${pageContext.request.contextPath}/menu/view" class="button cancel">Cancel</a>
        </div>
    </form>
</div>
</body>
</html>