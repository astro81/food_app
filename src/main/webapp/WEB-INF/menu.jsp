<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Menu Management</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            line-height: 1.6;
        }
        .form-container {
            max-width: 500px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #f9f9f9;
        }
        h1 {
            text-align: center;
            color: #333;
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
            height: 80px;
        }
        .radio-group {
            margin-top: 5px;
        }
        .radio-option {
            margin-right: 15px;
        }
        .button-group {
            margin-top: 20px;
            text-align: right;
        }
        button {
            padding: 8px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button[type="submit"] {
            background-color: #4CAF50;
            color: white;
        }
        button[type="reset"] {
            background-color: #f44336;
            color: white;
            margin-right: 10px;
        }
        .required:after {
            content: " *";
            color: red;
        }
        .message {
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 4px;
            text-align: center;
        }
        .success {
            background-color: #dff0d8;
            color: #3c763d;
            border: 1px solid #d6e9c6;
        }
        .error {
            background-color: #f2dede;
            color: #a94442;
            border: 1px solid #ebccd1;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 30px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .price-column {
            text-align: right;
        }
        .availability-available {
            color: green;
            font-weight: bold;
        }
        .availability-out {
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="form-container">
    <h1>Menu Management</h1>

    <%-- Display messages from servlet --%>
    <c:if test="${not empty successMessage}">
        <div class="message success">
                ${successMessage}
        </div>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <div class="message error">
                ${errorMessage}
        </div>
    </c:if>

    <h2>Add New Menu Item</h2>
    <form action="menu-item" method="post">
        <div class="form-group">
            <label for="food_name" class="required">Food Name</label>
            <input type="text" id="food_name" name="food_name" required>
        </div>

        <div class="form-group">
            <label for="food_description">Description</label>
            <textarea id="food_description" name="food_description"></textarea>
        </div>

        <div class="form-group">
            <label for="food_price" class="required">Price</label>
            <input type="number" id="food_price" name="food_price"
                   step="0.01" min="0" required>
        </div>

        <div class="form-group">
            <label for="food_category" class="required">Category</label>
            <select id="food_category" name="food_category" required>
                <option value="" selected disabled>Select a category</option>
                <option value="meals">Meals</option>
                <option value="snacks">Snacks</option>
                <option value="sweets">Sweets</option>
                <option value="drinks">Drinks</option>
            </select>
        </div>

        <div class="form-group">
            <label class="required">Availability</label>
            <div class="radio-group">
                    <span class="radio-option">
                        <input type="radio" id="available" name="food_availability"
                               value="available" checked required>
                        <label for="available">Available</label>
                    </span>
                <span class="radio-option">
                        <input type="radio" id="out_of_order" name="food_availability"
                               value="out_of_order">
                        <label for="out_of_order">Out of Order</label>
                    </span>
            </div>
        </div>

        <div class="button-group">
            <button type="reset">Reset</button>
            <button type="submit">Add Item</button>
        </div>
    </form>
</div>

<div style="max-width: 1000px; margin: 30px auto;">
    <h2>Current Menu Items</h2>
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th class="price-column">Price</th>
            <th>Category</th>
            <th>Availability</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${not empty menuItems}">
                <c:forEach items="${menuItems}" var="item">
                    <tr>
                        <td>${item.foodName}</td>
                        <td>${item.foodDescription}</td>
                        <td class="price-column">$${String.format("%.2f", item.foodPrice)}</td>
                        <td>
                            <c:choose>
                                <c:when test="${item.foodCategory eq 'meals'}">🍽️ Meals</c:when>
                                <c:when test="${item.foodCategory eq 'snacks'}">🍟 Snacks</c:when>
                                <c:when test="${item.foodCategory eq 'sweets'}">🍰 Sweets</c:when>
                                <c:when test="${item.foodCategory eq 'drinks'}">🥤 Drinks</c:when>
                                <c:otherwise>${item.foodCategory}</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${item.foodAvailability eq 'available'}">
                                    <span class="availability-available">Available</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="availability-out">Out of Order</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="5" style="text-align: center;">No menu items found</td>
                </tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div>

<script>
    // Basic client-side validation
    document.querySelector('form').addEventListener('submit', function(e) {
        var priceInput = document.getElementById('food_price');
        var price = parseFloat(priceInput.value);

        if (isNaN(price)) {
            alert('Please enter a valid price');
            priceInput.focus();
            e.preventDefault();
            return false;
        }

        if (price <= 0) {
            alert('Price must be greater than 0');
            priceInput.focus();
            e.preventDefault();
            return false;
        }

        return true;
    });
</script>
</body>
</html>
