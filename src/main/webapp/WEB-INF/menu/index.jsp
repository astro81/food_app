<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <!-- Google Fonts and styles remain the same -->
    <title>Menu Management</title>
    <style>
        /* Previous styles remain the same */
        .owner-badge {
            background: #e3f2fd;
            color: #1976d2;
            padding: 3px 6px;
            border-radius: 3px;
            font-size: 0.8em;
            margin-left: 5px;
        }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/components/navbar.jsp" %>

<h1>Menu Items</h1>

<c:if test="${not empty NOTIFICATION}">
    <div class="notification">${NOTIFICATION}</div>
    <c:remove var="NOTIFICATION" scope="session" />
</c:if>

<div class="filter-section">
    <h2>Filters</h2>
    <form action="${pageContext.request.contextPath}/menu/filter" method="get">
        <!-- Filter controls remain the same -->
    </form>
</div>

<c:if test="${isAdmin or isVendor}">
    <p><a href="${pageContext.request.contextPath}/menu/add" class="btn">Add New Menu Item</a></p>
</c:if>

<c:if test="${not (isAdmin or isVendor)}">
    <form action="${pageContext.request.contextPath}/make-order" method="get" style="display:inline;">
        <button type="submit" class="btn" style="background-color: #2196F3;">Make Order</button>
    </form>
</c:if>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Image</th>
        <th>Name</th>
        <th>Description</th>
        <th>Price</th>
        <th>Category</th>
        <th>Availability</th>
        <c:if test="${isAdmin or isVendor}">
            <th>Actions</th>
        </c:if>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${menuItems}" var="item">
        <tr>
            <td>${item.foodId}</td>
            <td>
                <c:if test="${not empty item.foodImage}">
                    <img src="${pageContext.request.contextPath}/images/${item.foodImage}"
                         alt="${item.foodName}" class="menu-image">
                </c:if>
            </td>
            <td>${item.foodName}
                <c:if test="${isAdmin and not empty item.vendorId}">
                    <span class="owner-badge">Vendor ID: ${item.vendorId}</span>
                </c:if>
            </td>
            <td>${item.foodDescription}</td>
            <td>$${item.foodPrice}</td>
            <td>${item.foodCategory}</td>
            <td>${item.foodAvailability}</td>
            <td class="actions">
                <c:if test="${isAdmin or (isVendor and item.vendorId eq user.userId)}">
                    <a href="${pageContext.request.contextPath}/menu/edit?food_id=${item.foodId}" class="btn">Edit</a>
                    <form action="${pageContext.request.contextPath}/menu/delete" method="post" style="display:inline;">
                        <input type="hidden" name="food_id" value="${item.foodId}">
                        <button type="submit" class="btn btn-danger">Delete</button>
                    </form>
                </c:if>
                <form method="post" action="${pageContext.request.contextPath}/menu" target="hiddenFrame" style="display:inline;">
                    <input type="hidden" name="food_id" value="${item.foodId}">
                    <button type="submit" class="btn">Print</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
    <iframe name="hiddenFrame" style="display:none;"></iframe>
</table>
</body>
</html>