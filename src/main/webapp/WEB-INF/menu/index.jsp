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
        .add-btn {
            display: inline-block;
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            text-decoration: none;
            border-radius: 4px;
            margin-bottom: 20px;
        }
        .add-btn:hover {
            background-color: #45a049;
        }
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

<!-- Add New Item Link -->
<a href="${pageContext.request.contextPath}/menu?action=add" class="add-btn">Add New Menu Item</a>

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
                <a href="?action=edit&food_id=${item.foodId}">Edit</a>
                <form action="" method="post" style="display: inline;">
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