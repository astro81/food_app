<%-- WEB-INF/menu/menu-edit.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${empty menuItem ? 'Add' : 'Edit'} Menu Item</title>
    <style>
        :root {
            --primary-color: #4a6fa5;
            --secondary-color: #166088;
            --success-color: #28a745;
            --danger-color: #dc3545;
            --light-color: #f8f9fa;
            --dark-color: #343a40;
            --border-color: #ced4da;
            --border-radius: 0.375rem;
            --box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.1);
        }

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            background-color: #f5f7fa;
            color: #333;
            line-height: 1.6;
            padding: 20px;
        }

        .form-container {
            max-width: 700px;
            margin: 30px auto;
            padding: 30px;
            background: white;
            border-radius: var(--border-radius);
            box-shadow: var(--box-shadow);
        }

        h1 {
            color: var(--primary-color);
            margin-bottom: 25px;
            text-align: center;
            font-weight: 600;
        }

        .notification {
            color: var(--success-color);
            margin: 15px 0;
            padding: 12px;
            background: #e8f5e9;
            border-left: 4px solid var(--success-color);
            border-radius: var(--border-radius);
            font-weight: 500;
        }

        .error {
            color: var(--danger-color);
            font-size: 0.875rem;
            margin-top: 5px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            color: var(--dark-color);
        }

        input, textarea, select {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid var(--border-color);
            border-radius: var(--border-radius);
            font-size: 1rem;
            transition: border-color 0.3s, box-shadow 0.3s;
        }

        input:focus, textarea:focus, select:focus {
            border-color: var(--primary-color);
            outline: none;
            box-shadow: 0 0 0 0.2rem rgba(74, 111, 165, 0.25);
        }

        textarea {
            height: 120px;
            resize: vertical;
        }

        .btn {
            display: inline-block;
            padding: 10px 20px;
            font-size: 1rem;
            font-weight: 500;
            text-align: center;
            border-radius: var(--border-radius);
            cursor: pointer;
            transition: all 0.3s ease;
            text-decoration: none;
            border: none;
        }

        .btn-primary {
            background-color: var(--primary-color);
            color: white;
        }

        .btn-primary:hover {
            background-color: var(--secondary-color);
            transform: translateY(-1px);
        }

        .btn-cancel {
            background-color: var(--danger-color);
            color: white;
            margin-left: 10px;
        }

        .btn-cancel:hover {
            background-color: #c82333;
            transform: translateY(-1px);
        }

        .actions {
            margin-top: 25px;
            display: flex;
            justify-content: flex-end;
        }

        .image-preview-container {
            margin-top: 15px;
        }

        .image-preview {
            max-width: 100%;
            max-height: 250px;
            border-radius: var(--border-radius);
            border: 1px solid var(--border-color);
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
            display: ${not empty menuItem.foodImage ? 'block' : 'none'};
            margin-top: 10px;
        }

        .current-image-label {
            font-weight: 500;
            color: var(--dark-color);
            margin-bottom: 5px;
        }

        .file-input-container {
            position: relative;
            margin-top: 10px;
        }

        .file-input-label {
            display: block;
            padding: 8px 12px;
            background-color: var(--light-color);
            border: 1px dashed var(--border-color);
            border-radius: var(--border-radius);
            text-align: center;
            cursor: pointer;
            transition: all 0.3s;
        }

        .file-input-label:hover {
            background-color: #e9ecef;
            border-color: var(--primary-color);
        }

        .file-input {
            position: absolute;
            width: 1px;
            height: 1px;
            padding: 0;
            margin: -1px;
            overflow: hidden;
            clip: rect(0, 0, 0, 0);
            border: 0;
        }

        @media (max-width: 768px) {
            .form-container {
                padding: 20px;
            }

            .actions {
                flex-direction: column;
            }

            .btn-cancel {
                margin-left: 0;
                margin-top: 10px;
            }
        }
    </style>
</head>
<body>
<div class="form-container">
    <h1>${empty menuItem ? 'Add' : 'Edit'} Menu Item</h1>

<%--    <c:if test="${not empty NOTIFICATION}">--%>
<%--        <div class="notification">${NOTIFICATION}</div>--%>
<%--    </c:if>--%>

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
            <label>Image:</label>
            <div class="file-input-container">
                <label for="food_image" class="file-input-label">
                    Choose an image file...
                </label>
                <input type="file" id="food_image" name="food_image" accept="image/*" class="file-input">
            </div>
            <c:if test="${not empty menuItem.foodImage}">
                <div class="image-preview-container">
                    <div class="current-image-label">Current Image:</div>
                    <img src="${pageContext.request.contextPath}/images/${menuItem.foodImage}"
                         alt="Current Image" class="image-preview">
                    <input type="hidden" name="existing_image" value="${menuItem.foodImage}">
                </div>
            </c:if>
        </div>

        <div class="actions">
            <button type="submit" class="btn btn-primary">Save</button>
            <a href="${pageContext.request.contextPath}/menu" class="btn btn-cancel">Cancel</a>
        </div>
    </form>
</div>

<script>
    document.getElementById('food_image').addEventListener('change', function(e) {
        const preview = document.querySelector('.image-preview');
        const fileLabel = document.querySelector('.file-input-label');

        if (this.files && this.files[0]) {
            fileLabel.textContent = this.files[0].name;
            const reader = new FileReader();
            reader.onload = function(e) {
                if (!preview) {
                    // Create new preview if it doesn't exist
                    const container = document.querySelector('.image-preview-container') ||
                        document.querySelector('.form-group:last-child');
                    const newPreview = document.createElement('img');
                    newPreview.className = 'image-preview';
                    newPreview.style.display = 'block';
                    container.appendChild(newPreview);
                    preview = newPreview;
                }
                preview.src = e.target.result;
                preview.style.display = 'block';
            }
            reader.readAsDataURL(this.files[0]);
        } else {
            fileLabel.textContent = 'Choose an image file...';
        }
    });
</script>
</body>
</html>