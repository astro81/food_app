<!-- Drinks section -->
<section class="drinks-section">
    <div class="drinks-content">
        <div class="drinks-image">
            <img src="${pageContext.request.contextPath}/images/menu-photo.png" alt="Cocktail being prepared" class="drink-img">
            <div class="drink-info">
                <h5>Midnight Martini</h5>
                <p class="price">$16</p>
                <p class="ingredients">Gin, lime, tequila, ice, simple syrup, salt for the rim</p>
            </div>
        </div>

        <div class="drinks-menu">
            <h2 class="drink-category">Wine</h2>
            <h2 class="drink-category active">Cocktails</h2>
            <h2 class="drink-category">Beer</h2>
        </div>
    </div>

    <div class="drinks-navigation">
        <button class="nav-button">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </button>
        <button class="nav-button">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m9 18 6-6-6-6"/></svg>
        </button>
    </div>

    <style>
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
            justify-content: center;
        }

        @media (max-width: 1024px) {
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
    </style>
</section>