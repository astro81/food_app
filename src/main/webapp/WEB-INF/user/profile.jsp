<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2, h3 {
            color: #333;
        }
        .user-info {
            margin: 20px 0;
            padding: 15px;
            background-color: #f9f9f9;
            border-radius: 4px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"], input[type="password"], textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .btn {
            padding: 8px 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            margin-right: 10px;
        }
        .btn-primary {
            background-color: #337ab7;
            color: white;
        }
        .btn-primary:hover {
            background-color: #286090;
        }
        .btn-danger {
            background-color: #d9534f;
            color: white;
        }
        .btn-danger:hover {
            background-color: #c9302c;
        }
        .notification {
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 4px;
        }
        .success {
            background-color: #dff0d8;
            color: #3c763d;
        }
        .error {
            background-color: #f2dede;
            color: #a94442;
        }
        .edit-form {
            display: none;
            margin-top: 20px;
            padding: 15px;
            background-color: #f0f0f0;
            border-radius: 4px;
        }

        /* Order history styles */
        .order-history {
            margin: 30px 0;
        }
        .order-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        .order-table th, .order-table td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }
        .order-table th {
            background-color: #f5f5f5;
        }
        .order-row:hover {
            background-color: #f9f9f9;
        }

        .profile-picture {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            object-fit: cover;
            border: 3px solid #ddd;
            margin-bottom: 15px;
        }

        .profile-picture-section {
            margin-bottom: 20px;
            text-align: center;
        }

        /* Modal styles */
        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.4);
        }

        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 400px;
            max-width: 80%;
            border-radius: 5px;
            box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
        }

        .modal-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
        }

        .modal-title {
            font-size: 1.2em;
            font-weight: bold;
            margin: 0;
        }

        .close {
            color: #aaa;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
        }

        .close:hover {
            color: black;
        }

        .modal-footer {
            margin-top: 20px;
            text-align: right;
        }

        .preview-container {
            text-align: center;
            margin: 15px 0;
        }

        #imagePreview {
            max-width: 200px;
            max-height: 200px;
            display: none;
            margin: 0 auto;
            border-radius: 4px;
        }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/components/navbar.jsp" %>

<div class="container">
    <h2>User Profile</h2>

    <%-- Notification area --%>
    <% if (request.getAttribute("NOTIFICATION") != null) { %>
    <div class="notification ${requestScope.NOTIFICATION.contains('success') ? 'success' : 'error'}">
        <%= request.getAttribute("NOTIFICATION") %>
    </div>
    <% } %>
    <% if (request.getParameter("notification") != null) { %>
    <div class="notification success">
        <%= request.getParameter("notification") %>
    </div>
    <% } %>

    <%-- Check if user is logged in --%>
    <% if(session.getAttribute("user") == null) { %>
    <p>You are not logged in. <a href="${pageContext.request.contextPath}/user/login">Please login</a></p>
    <% } else {
        model.UserModel user = (model.UserModel) session.getAttribute("user");
    %>

<%--    <!-- Profile Picture Section -->--%>
<%--    <div class="profile-picture-section">--%>
<%--        <img src="${pageContext.request.contextPath}/user/profile-picture"--%>
<%--             alt="Profile Picture" class="profile-picture">--%>

<%--        <form action="${pageContext.request.contextPath}/user/profile"--%>
<%--              method="post" enctype="multipart/form-data">--%>
<%--            <input type="hidden" name="action" value="update">--%>
<%--            <input type="file" name="profilePicture" accept="image/*">--%>
<%--            <button type="submit" class="btn btn-primary">Update Profile Picture</button>--%>
<%--        </form>--%>
<%--    </div>--%>
    <!-- Profile Picture Section -->
    <div class="profile-picture-section">
        <img src="${pageContext.request.contextPath}/user/profile-picture"
             alt="Profile Picture" class="profile-picture">

        <button id="change-picture-btn" class="btn btn-primary">Change Profile Picture</button>
    </div>

    <!-- The Modal -->
    <div id="pictureModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title">Update Profile Picture</h3>
                <span class="close">&times;</span>
            </div>

            <form id="pictureForm" action="${pageContext.request.contextPath}/user/profile"
                  method="post" enctype="multipart/form-data">
                <input type="hidden" name="action" value="update">

                <div class="form-group">
                    <label for="profilePicture">Select an image:</label>
                    <input type="file" id="profilePicture" name="profilePicture" accept="image/*" required>
                </div>

                <div class="preview-container">
                    <img id="imagePreview" src="#" alt="Preview">
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" id="cancelBtn">Cancel</button>
                    <button type="submit" class="btn btn-primary">Save Changes</button>
                </div>
            </form>
        </div>
    </div>


    <div class="user-info">
        <p><strong>Name:</strong> <span id="display-name"><%= user.getUserName() %></span></p>
        <p><strong>Email:</strong> <%= user.getUserMail() %></p>
        <p><strong>Phone:</strong> <span id="display-phone"><%= user.getUserPhone() %></span></p>
        <p><strong>Address:</strong> <span id="display-address"><%= user.getUserAddress() %></span></p>
    </div>

    <button id="edit-btn" class="btn btn-primary">Edit Profile</button>
    <a href="${pageContext.request.contextPath}/user/logout" class="btn btn-danger">Logout</a>

    <div id="edit-form" class="edit-form">
        <form action="${pageContext.request.contextPath}/user/profile" method="post">
            <input type="hidden" name="action" value="update">

            <div class="form-group">
                <label for="user_name">Name:</label>
                <input type="text" id="user_name" name="user_name" value="<%= user.getUserName() %>" required>
            </div>

            <div class="form-group">
                <label for="user_phone">Phone:</label>
                <input type="text" id="user_phone" name="user_phone" value="<%= user.getUserPhone() %>">
            </div>

            <div class="form-group">
                <label for="user_address">Address:</label>
                <textarea id="user_address" name="user_address"><%= user.getUserAddress() %></textarea>
            </div>

            <div class="form-group">
                <label for="user_passwd">New Password (leave blank to keep current):</label>
                <input type="password" id="user_passwd" name="user_passwd">
            </div>

            <button type="submit" class="btn btn-primary">Save Changes</button>
            <button type="button" id="cancel-btn" class="btn btn-danger">Cancel</button>
        </form>

        <hr>

        <form action="${pageContext.request.contextPath}/user/profile" method="post"
              onsubmit="return confirm('Are you sure you want to delete your account? This cannot be undone.');">
            <input type="hidden" name="action" value="delete">
            <button type="submit" class="btn btn-danger">Delete Account</button>
        </form>
    </div>

    <!-- Order History Section -->
    <div class="order-history">
        <h3>Order History</h3>
        <c:choose>
            <c:when test="${empty orderHistory}">
                <p>You don't have any completed orders yet.</p>
            </c:when>
            <c:otherwise>
                <table class="order-table">
                    <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Date</th>
                        <th>Total</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="order" items="${orderHistory}">
                        <tr class="order-row">
                            <td>${order.orderId}</td>
                            <td><fmt:formatDate value="${order.orderDate}" pattern="MMM dd, yyyy HH:mm" /></td>
                            <td>$<fmt:formatNumber value="${order.total}" pattern="#,##0.00" /></td>
                            <td>${order.status}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/order/details?orderId=${order.orderId}"
                                   class="btn btn-primary">View Details</a>
                                <form action="${pageContext.request.contextPath}/user/profile" method="post"
                                      style="display:inline;"
                                      onsubmit="return confirm('Are you sure you want to delete this order? This cannot be undone.');">
                                    <input type="hidden" name="action" value="deleteOrder">
                                    <input type="hidden" name="orderId" value="${order.orderId}">
                                    <button type="submit" class="btn btn-danger">Delete</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>

                </table>
            </c:otherwise>
        </c:choose>
    </div>

    <script>
        document.getElementById('edit-btn').addEventListener('click', function() {
            document.getElementById('edit-form').style.display = 'block';
            this.style.display = 'none';
        });

        document.getElementById('cancel-btn').addEventListener('click', function() {
            document.getElementById('edit-form').style.display = 'none';
            document.getElementById('edit-btn').style.display = 'inline-block';
        });
    </script>

    <script>
        // Modal functionality
        const modal = document.getElementById("pictureModal");
        const btn = document.getElementById("change-picture-btn");
        const span = document.getElementsByClassName("close")[0];
        const cancelBtn = document.getElementById("cancelBtn");

        // Open modal when button is clicked
        btn.onclick = function() {
            modal.style.display = "block";
        }

        // Close modal when X is clicked
        span.onclick = function() {
            modal.style.display = "none";
            resetForm();
        }

        // Close modal when cancel button is clicked
        cancelBtn.onclick = function() {
            modal.style.display = "none";
            resetForm();
        }

        // Close modal when clicking outside of it
        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = "none";
                resetForm();
            }
        }

        // Image preview functionality
        document.getElementById('profilePicture').addEventListener('change', function(e) {
            const preview = document.getElementById('imagePreview');
            const file = e.target.files[0];
            const reader = new FileReader();

            reader.onload = function(e) {
                preview.src = e.target.result;
                preview.style.display = 'block';
            }

            if (file) {
                reader.readAsDataURL(file);
            }
        });

        // Reset form when modal is closed
        function resetForm() {
            document.getElementById('pictureForm').reset();
            document.getElementById('imagePreview').style.display = 'none';
            document.getElementById('imagePreview').src = '#';
        }

        // Existing edit form scripts
        document.getElementById('edit-btn').addEventListener('click', function() {
            document.getElementById('edit-form').style.display = 'block';
            this.style.display = 'none';
        });

        document.getElementById('cancel-btn').addEventListener('click', function() {
            document.getElementById('edit-form').style.display = 'none';
            document.getElementById('edit-btn').style.display = 'inline-block';
        });
    </script>
    <% } %>
</div>
</body>
</html>