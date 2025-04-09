<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Menu Item</title>
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
    </style>
</head>
<body>
<div class="form-container">
    <h1>Add New Menu Item</h1>

    <%-- Display messages from servlet --%>
    <% String message = (String) request.getAttribute("message"); %>
    <% String messageType = (String) request.getAttribute("messageType"); %>
    <% if (message != null) { %>
    <div class="message <%= messageType %>">
        <%= message %>
    </div>
    <% } %>

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

<script>
    // Basic client-side validation
    document.querySelector('form').addEventListener('submit', function(e) {
        var priceInput = document.getElementById('food_price');
        var price = parseFloat(priceInput.value);

        if (isNaN(price) {
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