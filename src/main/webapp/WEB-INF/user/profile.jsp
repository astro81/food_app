<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">

    <style>
        /* Profile Page Specific Styles */
        body {
            height: 100%;
            overflow-y: scroll;
        }

        .profile-container {
            max-width: 90dvw;
            margin: 2rem auto;
            background: var(--background);
            padding: 2rem;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        .page-title {
            color: var(--text);
            margin-bottom: 1.5rem;
            font-weight: 600;
            border-bottom: 2px solid var(--primary);
            padding-bottom: 0.75rem;
        }

        /* Profile Layout Styles */
        .profile-layout {
            display: flex;
            gap: 2rem;
            margin-bottom: 2rem;
        }

        .profile-sidebar {
            flex: 0 0 auto;
            width: 220px;
        }

        .profile-main {
            flex: 1;
        }

        /* Toggle Menu Styles */
        .toggle-menu {
            display: flex;
            margin-bottom: 2rem;
            background: white;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            overflow: hidden;
        }

        .toggle-btn {
            flex: 1;
            padding: 1rem;
            text-align: center;
            background: none;
            border: none;
            font-size: 1rem;
            font-weight: 500;
            color: var(--text);
            cursor: pointer;
            transition: all 0.3s ease;
            position: relative;
        }

        .toggle-btn::after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 0;
            width: 100%;
            height: 3px;
            background-color: var(--primary);
            transform: scaleX(0);
            transition: transform 0.3s ease;
        }

        .toggle-btn.active {
            color: var(--primary);
            background-color: rgba(49, 216, 110, 0.05);
        }

        .toggle-btn.active::after {
            transform: scaleX(1);
        }

        .toggle-btn:hover {
            background-color: rgba(49, 216, 110, 0.05);
        }

        .toggle-btn:first-child {
            border-right: 1px solid #f0f0f0;
        }

        .toggle-content {
            display: none;
        }

        .toggle-content.active {
            display: block;
            animation: fadeIn 0.5s ease;
        }

        /* Button styles */
        .btn {
            padding: 0.75rem 1.25rem;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            margin-right: 10px;
            font-size: 0.9rem;
            font-weight: 500;
            transition: all 0.2s ease;
        }

        .btn-primary {
            background-color: var(--secondary);
            color: var(--background);
        }

        .btn-primary:hover {
            background-color: var(--primary);
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .btn-danger {
            background-color: var(--accent);
            color: var(--background);
        }

        .btn-danger:hover {
            background-color: #c41818;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        /* Form styles */
        .form-group {
            margin-bottom: 1.5rem;
        }

        label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 500;
            color: var(--text);
        }

        input[type="text"],
        input[type="password"],
        input[type="email"],
        input[type="file"],
        textarea {
            width: 100%;
            padding: 0.75rem;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-sizing: border-box;
            font-family: inherit;
            font-size: 0.95rem;
            transition: border-color 0.2s ease;
        }

        input:focus, textarea:focus {
            outline: none;
            border-color: var(--primary);
            box-shadow: 0 0 0 2px rgba(49, 216, 110, 0.2);
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
            background-color: rgba(0,0,0,0.5);
            backdrop-filter: blur(2px);
        }

        .modal-content {
            background-color: var(--background);
            margin: 10% auto;
            padding: 2rem;
            border: 1px solid #e0e0e0;
            width: 400px;
            max-width: 90%;
            border-radius: 12px;
            box-shadow: 0 8px 16px rgba(0,0,0,0.15);
            transform: translateY(20px);
            animation: modalSlideIn 0.3s forwards;
        }

        @keyframes modalSlideIn {
            to {
                transform: translateY(0);
            }
        }

        .modal-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1.5rem;
            padding-bottom: 0.75rem;
            border-bottom: 1px solid #eee;
        }

        .modal-title {
            font-size: 1.333rem;
            font-weight: 600;
            margin: 0;
            color: var(--text);
        }

        .close {
            color: #aaa;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
            transition: color 0.2s ease;
        }

        .close:hover {
            color: var(--text);
        }

        .modal-footer {
            margin-top: 1.5rem;
            text-align: right;
            display: flex;
            justify-content: flex-end;
            gap: 0.75rem;
        }

        /* Image preview */
        .preview-container {
            text-align: center;
            margin: 1.5rem 0;
        }

        #imagePreview {
            max-width: 200px;
            max-height: 200px;
            display: none;
            margin: 0 auto;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        /* Animation for content elements */
        .fade-in {
            opacity: 0;
            animation: fadeIn 0.5s forwards;
        }

        @keyframes fadeIn {
            to {
                opacity: 1;
            }
        }

        @media (max-width: 768px) {
            .profile-container {
                padding: 1.5rem;
                margin: 1rem;
            }

            .btn {
                padding: 0.6rem 1rem;
                font-size: 0.85rem;
            }

            .profile-layout {
                flex-direction: column;
            }

            .profile-sidebar {
                width: 100%;
                margin-bottom: 1rem;
            }
        }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/components/navbar.jsp" %>

<div class="profile-container">

    <%-- Check if user is logged in --%>
    <% if(session.getAttribute("user") == null) { %>
    <div class="not-logged-in fade-in">
        <p>You are not logged in. <a href="${pageContext.request.contextPath}/user/login" class="btn btn-primary">Please login</a></p>
    </div>
    <% } else {
        model.UserModel user = (model.UserModel) session.getAttribute("user");
    %>

    <h2 class="page-title">Hello <%= user.getUserName() %>!</h2>

    <!-- Toggle Menu -->
    <div class="toggle-menu fade-in">
        <button class="toggle-btn active" data-target="profile-content">My Profile</button>
        <button class="toggle-btn" data-target="orders-content">Order History</button>
    </div>

    <!-- Profile Content Section -->
    <div id="profile-content" class="toggle-content active">
        <div class="profile-layout">
            <div class="profile-sidebar">
                <%@ include file="/WEB-INF/user/components/profile-picture.jsp" %>
            </div>
            <div class="profile-main">
                <%@ include file="/WEB-INF/user/components/profile-info.jsp" %>
            </div>
        </div>
    </div>

    <!-- Orders Content Section -->
    <div id="orders-content" class="toggle-content">
        <%@ include file="/WEB-INF/user/components/order-history.jsp" %>
    </div>

    <% } %>
</div>

<script>
    // Toggle Menu Functionality
    document.addEventListener('DOMContentLoaded', function() {
        const toggleBtns = document.querySelectorAll('.toggle-btn');
        const toggleContents = document.querySelectorAll('.toggle-content');

        toggleBtns.forEach(btn => {
            btn.addEventListener('click', function() {
                // Remove active class from all buttons
                toggleBtns.forEach(b => b.classList.remove('active'));

                // Add active class to clicked button
                this.classList.add('active');

                // Hide all content sections
                toggleContents.forEach(content => content.classList.remove('active'));

                // Show the selected content section
                const targetId = this.getAttribute('data-target');
                document.getElementById(targetId).classList.add('active');
            });
        });

        // Animation for elements
        const elements = document.querySelectorAll('.fade-in');
        elements.forEach((element, index) => {
            setTimeout(() => {
                element.style.animation = 'fadeIn 0.5s forwards';
            }, index * 100);
        });
    });
</script>
</body>
</html>