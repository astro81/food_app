<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menu Items</title>
    <style>
        /* Reset and base styles */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            background-color: #f9f9f9;
            color: #333;
            padding: 20px;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }

        h1 {
            font-size: 28px;
            margin-bottom: 30px;
            color: #333;
            font-weight: 700;
        }

        /* Menu item card styles */
        .menu-items {
            display: grid;
            grid-template-columns: 1fr;
            gap: 24px;
        }

        @media (min-width: 768px) {
            .menu-items {
                grid-template-columns: 1fr;
            }
        }

        @media (min-width: 992px) {
            .menu-items {
                grid-template-columns: repeat(2, 1fr);
            }
        }

        .menu-item {
            width: fit-content;
            display: flex;
            flex-direction: column;
            border-radius: 8px;
            overflow: hidden;
            background-color: white;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
            position: relative;
        }

        .menu-item:hover {
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
            transform: translateY(-3px);
        }

        /*@media (min-width: 768px) {*/
        /*    .menu-item {*/
        /*        flex-direction: row;*/
        /*    }*/
        /*}*/

        /* Image styles */
        .image-box {
            position: relative;
            height: 200px;
            width: 100%;
            overflow: hidden;
        }

        @media (min-width: 768px) {
            .image-box {
                width: 300px;
                height: 300px;
                flex-shrink: 0;
            }
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

        /* Item info styles */
        .item-info {
            padding: 16px;
            display: flex;
            flex-direction: column;
            flex-grow: 1;
            position: relative;
        }

        .item-header {
            display: flex;
            align-items: flex-start;
            justify-content: space-between;
            margin-bottom: 8px;
        }

        .item-name {
            font-size: 20px;
            font-weight: bold;
            color: #333;
        }

        .item-price {
            font-size: 18px;
            font-weight: 600;
            color: #10b981; /* emerald-600 equivalent */
        }

        .owner-badge {
            display: inline-block;
            background-color: #f3f4f6; /* gray-100 equivalent */
            color: #4b5563; /* gray-600 equivalent */
            font-size: 12px;
            padding: 4px 8px;
            border-radius: 9999px;
            margin-bottom: 8px;
        }

        /* Hidden content that shows on hover */
        .hidden-content {
            max-height: 0;
            opacity: 0;
            overflow: hidden;
            transition: all 0.3s ease;
        }

        .menu-item:hover .hidden-content {
            max-height: 160px;
            opacity: 1;
            margin-bottom: 8px;
        }

        .item-description {
            color: #4b5563; /* gray-600 equivalent */
            font-size: 14px;
            line-height: 1.5;
            margin-bottom: 8px;
        }

        .tags-container {
            display: flex;
            flex-wrap: wrap;
            gap: 8px;
            margin-bottom: 8px;
        }

        .item-category {
            display: inline-block;
            background-color: #fef3c7; /* amber-100 equivalent */
            color: #92400e; /* amber-800 equivalent */
            font-size: 12px;
            padding: 4px 12px;
            border-radius: 9999px;
        }

        .item-availability {
            display: inline-block;
            font-size: 12px;
            padding: 4px 12px;
            border-radius: 9999px;
        }

        .available {
            background-color: #dcfce7; /* green-100 equivalent */
            color: #166534; /* green-800 equivalent */
        }

        .unavailable {
            background-color: #fee2e2; /* red-100 equivalent */
            color: #991b1b; /* red-800 equivalent */
        }

        /* Action buttons */
        .actions {
            display: flex;
            flex-wrap: wrap;
            gap: 8px;
            margin-top: auto;
            padding-top: 12px;
            border-top: 1px solid #f3f4f6; /* gray-100 equivalent */
        }

        .btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            padding: 6px 12px;
            border-radius: 6px;
            font-size: 14px;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.2s ease;
            text-decoration: none;
            border: none;
        }

        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .btn-icon {
            width: 16px;
            height: 16px;
            margin-right: 4px;
        }

        .btn-default {
            background-color: #10b981; /* emerald-600 equivalent */
            color: white;
            margin-left: auto;
        }

        .btn-outline {
            background-color: transparent;
            color: #333;
            border: 1px solid #e5e7eb; /* gray-200 equivalent */
        }

        .btn-danger {
            background-color: #ef4444; /* red-500 equivalent */
            color: white;
        }

        /* Icons */
        .icon-edit {
            background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='24' height='24' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3E%3Cpath d='M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7'%3E%3C/path%3E%3Cpath d='M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z'%3E%3C/path%3E%3C/svg%3E");
            background-size: contain;
            background-repeat: no-repeat;
            display: inline-block;
        }

        .icon-trash {
            background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='24' height='24' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3E%3Cpath d='M3 6h18'%3E%3C/path%3E%3Cpath d='M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6'%3E%3C/path%3E%3Cpath d='M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2'%3E%3C/path%3E%3Cline x1='10' y1='11' x2='10' y2='17'%3E%3C/line%3E%3Cline x1='14' y1='11' x2='14' y2='17'%3E%3C/line%3E%3C/svg%3E");
            background-size: contain;
            background-repeat: no-repeat;
            display: inline-block;
        }

        .icon-bag {
            background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='24' height='24' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3E%3Cpath d='M6 2L3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4z'%3E%3C/path%3E%3Cline x1='3' y1='6' x2='21' y2='6'%3E%3C/line%3E%3Cpath d='M16 10a4 4 0 0 1-8 0'%3E%3C/path%3E%3C/svg%3E");
            background-size: contain;
            background-repeat: no-repeat;
            display: inline-block;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Our Menu</h1>

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
                    <div class="item-header">
                        <h3 class="item-name">${item.foodName}</h3>
                        <span class="item-price">$${item.foodPrice}</span>
                    </div>

                    <c:if test="${isAdmin and not empty item.vendorId}">
                        <span class="owner-badge">Vendor ID: ${item.vendorId}</span>
                    </c:if>

                    <div class="hidden-content">
                        <p class="item-description">${item.foodDescription}</p>

                        <div class="tags-container">
                            <span class="item-category">${item.foodCategory}</span>
                            <c:choose>
                                <c:when test="${item.foodAvailability eq 'Available'}">
                                    <span class="item-availability available">${item.foodAvailability}</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="item-availability unavailable">${item.foodAvailability}</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <div class="actions">
                        <c:if test="${isAdmin or (isVendor and item.vendorId eq user.userId)}">
                            <a href="${pageContext.request.contextPath}/menu/edit?food_id=${item.foodId}" class="btn btn-outline">
                                <span class="btn-icon icon-edit"></span>
                                Edit
                            </a>
                            <form action="${pageContext.request.contextPath}/menu/delete" method="post" style="display:inline;">
                                <input type="hidden" name="food_id" value="${item.foodId}">
                                <button type="submit" class="btn btn-danger">
                                    <span class="btn-icon icon-trash"></span>
                                    Delete
                                </button>
                            </form>
                        </c:if>

                        <form method="post" action="${pageContext.request.contextPath}/menu" target="hiddenFrame" style="display:inline;">
                            <input type="hidden" name="food_id" value="${item.foodId}">
                            <button type="submit" class="btn btn-default">
                                <span class="btn-icon icon-bag"></span>
                                Order
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<!-- Hidden iframe for form submission without page refresh -->
<iframe name="hiddenFrame" style="display:none;"></iframe>
</body>
</html>