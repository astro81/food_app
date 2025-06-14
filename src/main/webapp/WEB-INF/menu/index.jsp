<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

    <title>Menu Items</title>
    <style>
        /* Reset and base styles */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            width: 100%;
            height: 100%;
            overflow-y: scroll;
            overflow-x: hidden;
            font-family: 'Inter', sans-serif;
        }

        .wrapper {
            height: 100%;
            width: 100%;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }

        .menu-container {
            margin: 0 auto;
            padding: 20px;
            width: 100%;
            max-width: 1200px;
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
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 24px;
        }

        .menu-item {
            width: 100%;
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

        /* Image styles */
        .image-box {
            position: relative;
            height: 200px;
            width: 100%;
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
            color: #10b981;
        }

        .owner-badge {
            display: none;
            background-color: #f3f4f6;
            color: #4b5563;
            font-size: 12px;
            padding: 4px 8px;
            border-radius: 9999px;
            margin-bottom: 8px;
        }

        .menu-item:hover .owner-badge {
            display: inline-block;
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
            color: #4b5563;
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
            background-color: #fef3c7;
            color: #92400e;
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
            background-color: #dcfce7;
            color: #166534;
        }

        .unavailable {
            background-color: #fee2e2;
            color: #991b1b;
        }

        /* Action buttons */
        .actions {
            display: flex;
            flex-wrap: wrap;
            gap: 8px;
            margin-top: auto;
            padding-top: 12px;
            border-top: 1px solid #f3f4f6;
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
            background-color: #10b981;
            color: white;
            margin-left: auto;
        }

        .btn-outline {
            background-color: transparent;
            color: #333;
            border: 1px solid #e5e7eb;
        }

        .btn-danger {
            background-color: #ef4444;
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

        .icon-filter {
            background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='24' height='24' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3E%3Cpolygon points='22 3 2 3 10 12.46 10 19 14 21 14 12.46 22 3'%3E%3C/polygon%3E%3C/svg%3E");
            background-size: contain;
            background-repeat: no-repeat;
            display: inline-block;
            width: 20px;
            height: 20px;
        }

        .menu-hero {
            height: 70vh;
            width: 100%;
            position: relative;
            display: flex;
            align-items: center;
            justify-content: center;
            background: linear-gradient(rgba(0, 0, 0, 0.6), rgba(0, 0, 0, 0.4)),
            url('https://images.unsplash.com/photo-1414235077428-338989a2e8c0?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80');
            background-size: cover;
            background-position: center;
            padding: 2rem;
            color: white;
            text-align: center;
        }

        .hero-content {
            max-width: 1200px;
            width: 100%;
            margin: 0 auto;
            position: relative;
        }

        .hero-title {
            font-size: 3.5rem;
            font-weight: 700;
            margin-bottom: 1.5rem;
            text-shadow: 0 2px 4px rgba(0,0,0,0.3);
            font-family: 'Montserrat', sans-serif;
        }

        .hero-subtitle {
            font-size: 1.5rem;
            margin-bottom: 2.5rem;
            max-width: 700px;
            margin-left: auto;
            margin-right: auto;
            text-shadow: 0 1px 3px rgba(0,0,0,0.3);
        }

        .search-container {
            max-width: 700px;
            margin: 0 auto 2rem;
            position: relative;
        }

        .search-input {
            width: 100%;
            padding: 1.2rem 1.5rem;
            border-radius: 50px;
            border: none;
            font-size: 1.1rem;
            box-shadow: 0 4px 15px rgba(0,0,0,0.2);
            transition: all 0.3s ease;
        }

        .search-input:focus {
            outline: none;
            box-shadow: 0 4px 20px rgba(0,0,0,0.3);
        }

        .search-button {
            position: absolute;
            right: 5px;
            top: 5px;
            background-color: #10b981;
            color: white;
            border: none;
            border-radius: 50px;
            padding: 0.8rem 1.5rem;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .search-button:hover {
            background-color: #0d9e6e;
            transform: translateY(-2px);
        }

        .filter-toggle {
            position: absolute;
            top: 20px;
            right: 20px;
            background-color: rgba(255, 255, 255, 0.9);
            border: none;
            border-radius: 50%;
            width: 40px;
            height: 40px;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
            z-index: 10;
        }

        .filter-toggle:hover {
            background-color: white;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        }

        .filter-section {
            position: absolute;
            top: 70px;
            right: 20px;
            background-color: rgba(255, 255, 255, 0.95);
            border-radius: 10px;
            padding: 1.5rem;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            width: 300px;
            z-index: 10;
            display: none;
            animation: fadeIn 0.3s ease;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-10px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .filter-section.show {
            display: block;
        }

        .filter-section h2 {
            color: #333;
            margin-bottom: 1rem;
            font-size: 1.5rem;
        }

        .filter-form {
            display: flex;
            flex-direction: column;
            gap: 1rem;
        }

        .filter-group {
            display: flex;
            flex-direction: column;
        }

        .filter-group label {
            margin-bottom: 0.5rem;
            font-weight: 500;
            color: #555;
        }

        .filter-group select,
        .filter-group input {
            padding: 0.7rem;
            border-radius: 5px;
            border: 1px solid #ddd;
            font-size: 1rem;
            width: 100%;
        }

        .filter-actions {
            display: flex;
            gap: 1rem;
            margin-top: 1rem;
        }

        .filter-btn {
            padding: 0.7rem 1.5rem;
            border-radius: 5px;
            border: none;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            flex: 1;
            text-align: center;
        }

        .filter-btn.apply {
            background-color: #10b981;
            color: white;
        }

        .filter-btn.reset {
            background-color: #f3f4f6;
            color: #333;
            text-decoration: none;
        }

        .filter-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }

        .action-buttons {
            display: flex;
            gap: 1rem;
            justify-content: center;
            margin-top: 1.5rem;
        }

        @media (max-width: 768px) {
            .filter-section {
                width: calc(100% - 40px);
                right: 20px;
                left: 20px;
                margin: 0 auto;
            }

            .hero-title {
                font-size: 2.5rem;
            }

            .hero-subtitle {
                font-size: 1.2rem;
            }
        }
    </style>
</head>
<body>

<%@ include file="/WEB-INF/components/navbar.jsp" %>

<div class="wrapper">

    <section class="menu-hero">
        <div class="hero-content">
            <button class="filter-toggle" id="filterToggle">
                <span class="icon-filter"></span>
            </button>

            <h1 class="hero-title">Discover Our Delicious Menu</h1>
            <p class="hero-subtitle">Explore a wide variety of culinary delights prepared with the freshest ingredients</p>

            <div class="search-container">
                <form action="${pageContext.request.contextPath}/menu" method="get">
                    <input type="text" class="search-input" name="query"
                           placeholder="Search for dishes, cuisines, or ingredients..."
                           value="${param.query}">
                    <button type="submit" class="search-button">Search</button>
                </form>
            </div>

            <div class="filter-section" id="filterSection">
                <h2>Filter Menu Items</h2>
                <form action="${pageContext.request.contextPath}/menu" method="get" class="filter-form">
                    <input type="hidden" name="query" value="${param.query}">

                    <div class="filter-group">
                        <label for="category">Category</label>
                        <select id="category" name="category" class="form-control">
                            <option value="">All Categories</option>
                            <option value="Meals" ${param.category == 'Meals' ? 'selected' : ''}>Meals</option>
                            <option value="Snacks" ${param.category == 'Snacks' ? 'selected' : ''}>Snacks</option>
                            <option value="Sweets" ${param.category == 'Sweets' ? 'selected' : ''}>Sweets</option>
                            <option value="Drinks" ${param.category == 'Drinks' ? 'selected' : ''}>Drinks</option>
                        </select>
                    </div>

                    <div class="filter-group">
                        <label for="price-range">Price Range</label>
                        <select id="price-range" name="price-range" class="form-control">
                            <option value="">Any Price</option>
                            <option value="0-10" ${param['price-range'] == '0-10' ? 'selected' : ''}>$0 - $10</option>
                            <option value="10-20" ${param['price-range'] == '10-20' ? 'selected' : ''}>$10 - $20</option>
                            <option value="20-50" ${param['price-range'] == '20-50' ? 'selected' : ''}>$20 - $50</option>
                            <option value="50+" ${param['price-range'] == '50+' ? 'selected' : ''}>$50+</option>
                        </select>
                    </div>

                    <div class="filter-group">
                        <label for="availability">Availability</label>
                        <select id="availability" name="availability" class="form-control">
                            <option value="">All Items</option>
                            <option value="available" ${param.availability == 'available' ? 'selected' : ''}>Available Now</option>
                        </select>
                    </div>

                    <div class="filter-group">
                        <label for="sort">Sort By</label>
                        <select id="sort" name="sort" class="form-control">
                            <option value="name-asc" ${param.sort == 'name-asc' ? 'selected' : ''}>Name (A-Z)</option>
                            <option value="name-desc" ${param.sort == 'name-desc' ? 'selected' : ''}>Name (Z-A)</option>
                            <option value="price-asc" ${param.sort == 'price-asc' ? 'selected' : ''}>Price (Low to High)</option>
                            <option value="price-desc" ${param.sort == 'price-desc' ? 'selected' : ''}>Price (High to Low)</option>
                        </select>
                    </div>

                    <div class="filter-actions">
                        <a href="${pageContext.request.contextPath}/menu" class="filter-btn reset">Reset</a>
                        <button type="submit" class="filter-btn apply">Apply Filters</button>
                    </div>
                </form>
            </div>

            <div class="action-buttons">
                <c:if test="${isAdmin or isVendor}">
                    <a href="${pageContext.request.contextPath}/menu/add" class="btn" style="background-color: #10b981; color: white; padding: 0.8rem 1.5rem; border-radius: 5px; font-weight: 600;">
                        Add New Menu Item
                    </a>
                </c:if>

                <c:if test="${not (isAdmin or isVendor)}">
                    <form action="${pageContext.request.contextPath}/make-order" method="get" style="display:inline;">
                        <button type="submit" class="btn" style="background-color: #2196F3; color: white; padding: 0.8rem 1.5rem; border-radius: 5px; font-weight: 600;">
                            Make Order
                        </button>
                    </form>
                </c:if>
            </div>
        </div>
    </section>

    <section class="menu-container">
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
    </section>
</div>

<jsp:include page="/templates/footer.jsp" />

<!-- Hidden iframe for form submission without page refresh -->
<iframe name="hiddenFrame" style="display:none;"></iframe>

<script>
    // Toggle filter section visibility
    document.getElementById('filterToggle').addEventListener('click', function() {
        document.getElementById('filterSection').classList.toggle('show');
    });

    // Close filter section when clicking outside
    document.addEventListener('click', function(event) {
        const filterSection = document.getElementById('filterSection');
        const filterToggle = document.getElementById('filterToggle');

        if (filterSection.classList.contains('show') &&
            !filterSection.contains(event.target) &&
            !filterToggle.contains(event.target)) {
            filterSection.classList.remove('show');
        }
    });
</script>
</body>
</html>