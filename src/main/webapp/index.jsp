<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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

    <title>OrderNow</title>

    <style>
        body {
            overflow-y: scroll;
        }

        .restaurant-container {
            max-width: 100%;
            overflow-x: hidden;
            position: relative;
            top: 0;
            left: 0;
        }

    </style>
</head>
<body>
<%@ include file="/WEB-INF/components/navbar.jsp" %>

<div class="restaurant-container">
    <jsp:include page="templates/hero.jsp" />

    <jsp:include page="templates/who-are-we.jsp" />

    <jsp:include page="templates/footer.jsp" />
</div>

<!-- Optional JavaScript for interactivity -->
<script>
    // Add event listeners for menu category buttons
    document.addEventListener('DOMContentLoaded', function() {
        const categoryButtons = document.querySelectorAll('.category-button');

        categoryButtons.forEach(button => {
            button.addEventListener('click', function() {
                // Remove active class from all buttons
                categoryButtons.forEach(btn => btn.classList.remove('active'));
                // Add active class to clicked button
                this.classList.add('active');
            });
        });

        // Similar functionality for drink categories
        const drinkCategories = document.querySelectorAll('.drink-category');

        drinkCategories.forEach(category => {
            category.addEventListener('click', function() {
                drinkCategories.forEach(cat => cat.classList.remove('active'));
                this.classList.add('active');
            });
        });
    });
</script>
</body>
</html>