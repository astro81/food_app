<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Menu Management</title>
    <style>
        .notification { color: green; margin: 10px 0; padding: 10px; background: #e8f5e9; }
        .error { color: red; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #f2f2f2; }
        .actions { white-space: nowrap; }
        .filter-section { background: #f5f5f5; padding: 15px; margin-bottom: 20px; }
        .btn { padding: 8px 12px; text-decoration: none; background: #4CAF50; color: white; border-radius: 4px; }
        .btn-danger { background: #f44336; }
    </style>
</head>
<body>
<h1>Menu Items</h1>

<c:if test="${not empty NOTIFICATION}">
    <div class="notification">${NOTIFICATION}</div>
    <c:remove var="NOTIFICATION" scope="session" />
</c:if>

<div class="filter-section">
    <h2>Filters</h2>
    <form action="${pageContext.request.contextPath}/menu/filter" method="get">
        <label for="category">Category:</label>
        <select name="filterCategory" id="category">
            <option value="">All Categories</option>
            <option value="meals" ${filterCategory eq 'meals' ? 'selected' : ''}>Meals</option>
            <option value="snacks" ${filterCategory eq 'snacks' ? 'selected' : ''}>Snacks</option>
            <option value="drinks" ${filterCategory eq 'drinks' ? 'selected' : ''}>Drinks</option>
            <option value="sweets" ${filterCategory eq 'sweets' ? 'selected' : ''}>Sweets</option>
        </select>

        <label for="availability">Availability:</label>
        <select name="filterAvailability" id="availability">
            <option value="">All</option>
            <option value="available" ${filterAvailability eq 'available' ? 'selected' : ''}>Available</option>
            <option value="out_of_order" ${filterAvailability eq 'out_of_order' ? 'selected' : ''}>Out of Order</option>
        </select>

        <button type="submit" class="btn">Filter</button>
        <a href="${pageContext.request.contextPath}/menu" class="btn">Clear Filters</a>
    </form>
</div>

<c:if test="${isAdmin}">
    <p><a href="${pageContext.request.contextPath}/menu/add" class="btn">Add New Menu Item</a></p>
</c:if>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Description</th>
        <th>Price</th>
        <th>Category</th>
        <th>Availability</th>
        <c:if test="${isAdmin}">
            <th>Actions</th>
        </c:if>
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
            <c:if test="${isAdmin}">
                <td class="actions">
                    <a href="${pageContext.request.contextPath}/menu/edit?food_id=${item.foodId}" class="btn">Edit</a>
                    <form action="${pageContext.request.contextPath}/menu/delete" method="post" style="display:inline;">
                        <input type="hidden" name="food_id" value="${item.foodId}">
                        <button type="submit" class="btn btn-danger">Delete</button>
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>