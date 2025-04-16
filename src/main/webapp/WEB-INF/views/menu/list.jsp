<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Restaurant Menu</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/menu.css">
</head>
<body>
<h1>Restaurant Menu</h1>

<%@ include file="/WEB-INF/menu/fragments/notification.jsp" %>

<%@ include file="/WEB-INF/menu/fragments/filter.jsp" %>

<!-- Admin-only Add Item Button -->
<c:if test="${sessionScope.user.userRole == 'admin'}">
    <a href="${pageContext.request.contextPath}/menu/add" class="button add">Add New Menu Item</a>
</c:if>

<!-- Menu Items Display -->
<div class="menu-container">
    <c:forEach var="item" items="${menuItems}">
        <div class="menu-item">
            <h3>${item.foodName}</h3>
            <p>${item.foodDescription}</p>
            <p class="price">$${item.foodPrice}</p>
            <p class="category">Category: ${item.foodCategory}</p>
            <span class="availability ${item.foodAvailability}">
                    ${item.foodAvailability}
            </span>

            <%@ include file="/WEB-INF/menu/fragments/controls.jsp" %>
        </div>
    </c:forEach>
</div>

<!-- Navigation Links -->
<div class="navigation-links">
    <a href="${pageContext.request.contextPath}/user/profile" class="button">Back to Profile</a>
    <a href="${pageContext.request.contextPath}/user/logout" class="button">Logout</a>
</div>
</body>
</html>