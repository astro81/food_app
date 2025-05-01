<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${empty menuItem ? 'Add' : 'Edit'} Menu Item</title>
    <style>
        .notification { color: green; margin: 10px 0; padding: 10px; background: #e8f5e9; }
        .error { color: red; }
        .form-container { max-width: 600px; margin: 0 auto; padding: 20px; background: #f9f9f9; border-radius: 5px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input, textarea, select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        textarea { height: 100px; }
        .btn {
            padding: 8px 15px;
            background: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
        }
        .btn-cancel { background: #f44336; }
        .actions { margin-top: 20px; }
        .image-preview {
            max-width: 200px;
            max-height: 200px;
            margin-top: 10px;
            display: ${not empty menuItem.foodImage ? 'block' : 'none'};
        }
    </style>
</head>
<body>
<div class="form-container">
    <h1>${empty menuItem ? 'Add' : 'Edit'} Menu Item</h1>

    <c:if test="${not empty NOTIFICATION}">
        <div class="notification">${NOTIFICATION}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/menu/${empty menuItem ? 'add' : 'edit'}" method="post" enctype="multipart/form-data">
        <c:if test="${not empty menuItem}">
            <input type="hidden" name="food_id" value="${menuItem.foodId}">
        </c:if>

        <!-- Hidden vendor_id field that will be set automatically -->
        <c:if test="${isVendor}">
            <input type="hidden" name="vendor_id" value="${user.userId}">
        </c:if>

        <div class="form-group">
            <label for="food_name">Name:</label>
            <input type="text" id="food_name" name="food_name" value="${menuItem.foodName}" required>
        </div>

        <div class="form-group">
            <label for="food_description">Description:</label>
            <textarea id="food_description" name="food_description" required>${menuItem.foodDescription}</textarea>
        </div>

        <div class="form-group">
            <label for="food_price">Price:</label>
            <input type="number" id="food_price" name="food_price" step="0.01" min="0"
                   value="${menuItem.foodPrice}" required>
        </div>

        <div class="form-group">
            <label for="food_category">Category:</label>
            <select id="food_category" name="food_category" required>
                <option value="meals" ${menuItem.foodCategory eq 'meals' ? 'selected' : ''}>Meals</option>
                <option value="snacks" ${menuItem.foodCategory eq 'snacks' ? 'selected' : ''}>Snacks</option>
                <option value="drinks" ${menuItem.foodCategory eq 'drinks' ? 'selected' : ''}>Drinks</option>
                <option value="sweets" ${menuItem.foodCategory eq 'sweets' ? 'selected' : ''}>Sweets</option>
            </select>
        </div>

        <div class="form-group">
            <label for="food_availability">Availability:</label>
            <select id="food_availability" name="food_availability" required>
                <option value="available" ${menuItem.foodAvailability eq 'available' ? 'selected' : ''}>Available</option>
                <option value="out_of_order" ${menuItem.foodAvailability eq 'out_of_order' ? 'selected' : ''}>Out of Order</option>
            </select>
        </div>

        <div class="form-group">
            <label for="food_image">Image:</label>
            <input type="file" id="food_image" name="food_image" accept="image/*">
            <c:if test="${not empty menuItem.foodImage}">
                <div>Current Image:</div>
                <img src="${pageContext.request.contextPath}/images/${menuItem.foodImage}"
                     alt="Current Image" class="image-preview">
                <input type="hidden" name="existing_image" value="${menuItem.foodImage}">
            </c:if>
        </div>

        <div class="actions">
            <button type="submit" class="btn">Save</button>
            <a href="${pageContext.request.contextPath}/menu" class="btn btn-cancel">Cancel</a>
        </div>
    </form>
</div>

<script>
    document.getElementById('food_image').addEventListener('change', function(e) {
        const preview = document.querySelector('.image-preview');
        if (this.files && this.files[0]) {
            const reader = new FileReader();
            reader.onload = function(e) {
                preview.src = e.target.result;
                preview.style.display = 'block';
            }
            reader.readAsDataURL(this.files[0]);
        }
    });
</script>
</body>
</html>