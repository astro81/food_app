<%-- WEB-INF/menu/index.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@500;700&family=Inter:wght@400;500&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">

    <title>Menu Management</title>
    <style>
        body {
            width: 100%;
            height: 100%;
            overflow-y: scroll;
            overflow-x: hidden;
        }

        .wrapper {
            height: 100%;
            width: 100%;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }

        .menu-hero {
            height: 100dvh;
            width: 100dvw;
            outline: 1px solid coral;
        }

        .menu-section {
            height: 100%;
            width: 100dvw;
            display: flex;
            justify-content: center;
            align-items: center;
            outline: 1px solid seagreen;
        }

        .menu-container {
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .menu-items {
            display: grid;
            grid-template-columns: repeat(4, minmax(400px, 1fr));
            gap: 12px;
        }

        .menu-item {
            display: flex;
            flex-direction: column;
            border-radius: 8px;
            overflow: hidden;
            background-color: white;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
            position: relative;
        }

        .menu-item:hover {
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            transform: translateY(-3px);
        }

        .image-box {
            width: 300px;
            height: 100%;
            overflow: hidden;
        }

        .menu-image {
            width: 100%;
            height: 100%;
            object-fit: cover;
            transition: transform 0.3s ease;
        }

        .menu-item:hover .menu-image {
            transform: scale(1.05);
        }

        .item-info {
            padding: 15px;
            display: flex;
            flex-direction: column;
            flex-grow: 1;
        }

        .item-name {
            font-size: 1.25rem;
            font-weight: bold;
            margin-bottom: 5px;
        }

        .item-price {
            font-size: 1.1rem;
            font-weight: 600;
            color: #2e7d32;
            margin-bottom: 10px;
        }

        .owner-badge {
            display: inline-block;
            background-color: #f1f1f1;
            color: #666;
            font-size: 0.75rem;
            padding: 3px 8px;
            border-radius: 12px;
            margin-bottom: 8px;
        }

        .item-description {
            color: #666;
            margin-bottom: 10px;
            max-height: 0;
            overflow: hidden;
            transition: max-height 0.3s ease, opacity 0.3s ease, margin 0.3s ease;
            opacity: 0;
        }

        .menu-item:hover .item-description {
            max-height: 100px;
            opacity: 1;
            margin-bottom: 10px;
        }

        .item-category, .item-availability {
            display: inline-block;
            font-size: 0.75rem;
            padding: 3px 8px;
            border-radius: 12px;
            margin-right: 5px;
            margin-bottom: 10px;
            max-height: 0;
            overflow: hidden;
            transition: max-height 0.3s ease, opacity 0.3s ease, margin 0.3s ease;
            opacity: 0;
        }

        .menu-item:hover .item-category,
        .menu-item:hover .item-availability {
            max-height: 20px;
            opacity: 1;
            margin-bottom: 10px;
        }

        .item-category {
            background-color: #fff3e0;
            color: #e65100;
        }

        .item-availability {
            background-color: #e8f5e9;
            color: #2e7d32;
        }

        .item-availability.unavailable {
            background-color: #ffebee;
            color: #c62828;
        }

        .actions {
            display: flex;
            flex-wrap: wrap;
            gap: 8px;
            margin-top: auto;
            padding-top: 10px;
            border-top: 1px solid #eee;
        }

        .btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            padding: 6px 12px;
            font-size: 0.875rem;
            font-weight: 500;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.2s ease;
            text-decoration: none;
            border: none;
        }

        .btn:hover {
            opacity: 0.9;
        }

        .btn-default {
            background-color: #2e7d32;
            color: white;
            margin-left: auto;
        }

        .btn-outline {
            background-color: transparent;
            color: #333;
            border: 1px solid #ddd;
        }

        .btn-danger {
            background-color: #c62828;
            color: white;
        }

        /* Responsive adjustments */
        @media (max-width: 768px) {
            .menu-grid {
                grid-template-columns: 1fr;
            }

            .image-box {
                height: 200px;
            }
        }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/components/navbar.jsp" %>

<c:if test="${not empty NOTIFICATION}">
    <div class="notification">${NOTIFICATION}</div>
    <c:remove var="NOTIFICATION" scope="session" />
</c:if>

<div class="wrapper">
<section class="menu-hero">

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

</section>


<section class="menu-section">

    <div class="menu-container">

        <div class="menu-items">

            <c:forEach items="${menuItems}" var="item">
                <div class="menu-item">
                    <div class="image-box">
                        <c:choose>
                            <c:when test="${not empty item.foodImage}">
                                <img src="${pageContext.request.contextPath}/images/${item.foodImage}" alt="${item.foodName}" class="menu-image">
                            </c:when>
                            <c:otherwise>
                                <img src="https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?q=80&w=1470&auto=format&fit=crop" alt="Default food image" class="menu-image">
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="item-info">
                        <div class="item-name">${item.foodName}</div>

                        <c:if test="${isAdmin and not empty item.vendorId}">
                            <span class="owner-badge">Vendor ID: ${item.vendorId}</span>
                        </c:if>

                        <div class="item-price">$${item.foodPrice}</div>

                        <div class="item-description">${item.foodDescription}</div>

                        <div class="item-category">${item.foodCategory}</div>

                        <div class="item-availability ${item.foodAvailability ne 'Available' ? 'unavailable' : ''}">
                                ${item.foodAvailability}
                        </div>

                        <div class="actions">
                            <c:if test="${isAdmin or (isVendor and item.vendorId eq user.userId)}">
                                <a href="${pageContext.request.contextPath}/menu/edit?food_id=${item.foodId}" class="btn btn-outline">Edit</a>
                                <form action="${pageContext.request.contextPath}/menu/delete" method="post" style="display:inline;">
                                    <input type="hidden" name="food_id" value="${item.foodId}">
                                    <button type="submit" class="btn btn-danger">Delete</button>
                                </form>
                            </c:if>
                            <form method="post" action="${pageContext.request.contextPath}/menu" target="hiddenFrame" style="display:inline;">
                                <input type="hidden" name="food_id" value="${item.foodId}">
                                <button type="submit" class="btn btn-default">Order</button>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>

        </div>

    </div>


</section>

<iframe name="hiddenFrame" style="display:none;"></iframe>
</div>
</body>
</html>