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

    <title>Cibo gustoso - Italian Restaurant</title>

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


        /* Responsive styles */
        @media (max-width: 1024px) {
            .section-content {
                flex-direction: column;
            }

            .description {
                max-width: 100%;
            }
        }

        @media (max-width: 768px) {
            h1 {
                font-size: 3rem;
            }

            h2 {
                font-size: 2.5rem;
            }
        }

        /* Who are we section */
        .who-are-we {
            padding: 2rem 2rem 4rem;
        }

        .section-content {
            display: flex;
            justify-content: space-between;
            margin-bottom: 2rem;
        }

        .section-left {
            flex: 1;
        }

        .section-right {
            flex: 1;
            padding-top: 1rem;
        }

        .description {
            max-width: 80%;
            line-height: 1.6;
        }

        .navigation-buttons {
            display: flex;
            gap: 0.5rem;
            margin-top: 1rem;
        }

        .nav-button {
            width: 40px;
            height: 40px;
            border: 1px solid var(--text);
            background: none;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
        }

        .image-gallery {
            display: flex;
            gap: 1rem;
            overflow-x: auto;
            padding-bottom: 1rem;
        }

        .gallery-image {
            flex: 0 0 auto;
        }

        .gallery-img {
            border-radius: 0.5rem;
            object-fit: cover;
            width: 400px;
            height: 300px;
        }

        /* Menu section */
        .menu-section {
            padding: 4rem 2rem;
            text-align: center;
        }

        .section-title {
            margin-bottom: 1rem;
        }

        .section-description {
            max-width: 600px;
            margin: 0 auto 2rem;
            line-height: 1.6;
        }

        .menu-categories {
            display: flex;
            justify-content: center;
            gap: 1.5rem;
            margin-bottom: 2rem;
            flex-wrap: wrap;
        }

        .category-button {
            background: none;
            border: none;
            padding: 0.5rem 1rem;
            cursor: pointer;
            font-size: 0.9rem;
            border-radius: 1rem;
        }

        .category-button.active {
            background-color: #e0e0d8;
        }

        .menu-items {
            display: flex;
            justify-content: center;
            gap: 2rem;
            margin-bottom: 2rem;
            flex-wrap: wrap;
        }

        .menu-item {
            text-align: center;
            max-width: 200px;
        }

        .menu-img {
            border-radius: 0.5rem;
            margin-bottom: 0.5rem;
            object-fit: cover;
            width: 200px;
            height: 200px;
        }

        .price {
            color: var(--text);
            margin-top: 0.25rem;
        }

        /* Drinks section */
        .drinks-section {
            padding: 4rem 2rem;
        }

        .drinks-content {
            display: flex;
            justify-content: space-between;
            margin-bottom: 2rem;
        }

        .drinks-image {
            flex: 1;
            position: relative;
        }

        .drink-img {
            border-radius: 0.5rem;
            object-fit: cover;
            width: 300px;
            height: 400px;
        }

        .drink-info {
            margin-top: 1rem;
        }

        .ingredients {
            font-size: 0.9rem;
            margin-top: 0.5rem;
            max-width: 300px;
        }

        .drinks-menu {
            flex: 1;
            display: flex;
            flex-direction: column;
            gap: 2rem;
            padding-left: 4rem;
        }

        .drink-category {
            cursor: pointer;
            opacity: 0.5;
            transition: opacity 0.3s;
        }

        .drink-category.active {
            opacity: 1;
        }

        .drinks-navigation {
            display: flex;
            gap: 0.5rem;
            margin-top: 1rem;
        }

        /* Team section */
        .team-section {
            padding: 4rem 2rem;
        }

        .team-members {
            display: flex;
            gap: 2rem;
            overflow-x: auto;
            padding-bottom: 1rem;
        }

        .team-member {
            flex: 0 0 auto;
            max-width: 250px;
        }

        .team-img {
            border-radius: 0.5rem;
            margin-bottom: 1rem;
            object-fit: cover;
            width: 250px;
            height: 300px;
        }

        .role {
            margin-top: 0.25rem;
            font-size: 0.9rem;
            color: var(--secondary);
        }

        .bio {
            margin-top: 0.5rem;
            font-size: 0.9rem;
            line-height: 1.6;
        }

        /* Events section */
        .events-section {
            padding: 4rem 2rem;
        }

        .events-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 1rem;
            margin-top: 2rem;
        }

        .event-card {
            position: relative;
            overflow: hidden;
            border-radius: 0.5rem;
        }

        .event-img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            transition: transform 0.3s;
        }

        .event-card:hover .event-img {
            transform: scale(1.05);
        }

        .event-overlay {
            position: absolute;
            bottom: 2rem;
            left: 2rem;
            color: white;
            text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.5);
        }

        /* Footer */
        .footer {
            padding: 4rem 2rem 2rem;
            background-color: #e9e8e0;
            border-top: 1px solid rgba(0, 0, 0, 0.1);
        }

        .footer-top {
            display: flex;
            justify-content: space-between;
            margin-bottom: 3rem;
        }

        .footer-links {
            display: flex;
            gap: 1.5rem;
        }

        .footer-link {
            text-decoration: none;
            color: var(--text);
            font-size: 0.9rem;
        }

        .footer-logo {
            font-size: 1.5rem;
        }

        .newsletter {
            margin-bottom: 0.5rem;
            font-size: 0.9rem;
        }

        .newsletter-form {
            display: flex;
        }

        .newsletter-input {
            padding: 0.5rem;
            border: 1px solid var(--text);
            border-right: none;
            background: none;
        }

        .newsletter-button {
            padding: 0.5rem 1rem;
            background: var(--text);
            color: white;
            border: none;
            cursor: pointer;
        }

        .footer-bottom {
            display: flex;
            justify-content: center;
            padding-top: 2rem;
            border-top: 1px solid rgba(0, 0, 0, 0.1);
        }

        .social-links {
            display: flex;
            gap: 1.5rem;
        }

        .social-link {
            text-decoration: none;
            color: var(--text);
            font-size: 0.9rem;
        }

        /* Responsive styles */
        @media (max-width: 1024px) {
            .section-content {
                flex-direction: column;
            }

            .section-right {
                margin-top: 2rem;
            }

            .description {
                max-width: 100%;
            }

            .drinks-content {
                flex-direction: column;
            }

            .drinks-menu {
                flex-direction: row;
                padding-left: 0;
                margin-top: 2rem;
                justify-content: space-between;
            }
        }

        @media (max-width: 768px) {
            h1 {
                font-size: 3rem;
            }

            h2 {
                font-size: 2.5rem;
            }

            .events-grid {
                grid-template-columns: 1fr;
            }

            .footer-top {
                flex-direction: column;
                gap: 2rem;
                align-items: center;
                text-align: center;
            }
        }

        @media (max-width: 640px) {
            .navbar {
                flex-direction: column;
                gap: 1rem;
            }

            .menu-items {
                flex-direction: column;
                align-items: center;
            }

            .team-members {
                flex-direction: column;
                align-items: center;
            }

            .team-member {
                max-width: 100%;
            }
        }

        /* Navbar styles */
        .navbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 1.5rem 2rem;
            background-color: #e9e8e0;
        }

        .navbar-left,
        .navbar-right {
            display: flex;
            align-items: center;
            gap: 1.5rem;
        }

        .menu-button {
            background: none;
            border: none;
            cursor: pointer;
        }

        .nav-links {
            display: flex;
            gap: 1.5rem;
        }

        .nav-link {
            text-decoration: none;
            color: var(--text);
            font-size: 0.9rem;
        }

        .logo h2 {
            font-size: 1.5rem;
            font-weight: 500;
        }

        .language-button {
            background: none;
            border: none;
            cursor: pointer;
            font-size: 0.9rem;
        }

        .restaurant-container {
            max-width: 100%;
            overflow-x: hidden;
        }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/components/navbar.jsp" %>

<jsp:include page="templates/hero.jsp" />

<div class="restaurant-container">

    <jsp:include page="templates/who-are-we.jsp" />

    <jsp:include page="templates/menu-section.jsp" />

    <jsp:include page="templates/drinks-section.jsp" />

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