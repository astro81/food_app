<style>
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

    .menu-navigation {
        display: flex;
        justify-content: center;
        gap: 0.5rem;
    }

    @media (max-width: 640px) {
        .menu-items {
            flex-direction: column;
            align-items: center;
        }
    }
</style>

<!-- Menu section -->
<section class="menu-section">
    <h2 class="section-title">Menu</h2>
    <p class="section-description">
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse mattis pellentesque elementum.
        Pellentesque finibus tincidunt dolor. Aliquam diam sapien sem.
    </p>

    <div class="menu-categories">
        <button class="category-button active">Appetizers</button>
        <button class="category-button">Pasta</button>
        <button class="category-button">Pizza</button>
        <button class="category-button">Salads</button>
        <button class="category-button">Soups</button>
        <button class="category-button">Desserts</button>
    </div>

    <div class="menu-items">
        <div class="menu-item">
            <img src="${pageContext.request.contextPath}/images/Menu-items.png" alt="Gnocchi with almonds" class="menu-img">
            <h5>Gnocchi with almonds</h5>
            <p class="price">$18</p>
        </div>

        <div class="menu-item">
            <img src="${pageContext.request.contextPath}/images/Menu-items.png" alt="Mini ravioli in sauce" class="menu-img">
            <h5>Mini ravioli in sauce</h5>
            <p class="price">$21</p>
        </div>

        <div class="menu-item">
            <img src="${pageContext.request.contextPath}/images/Menu-items.png" alt="Lasagna" class="menu-img">
            <h5>Lasagna</h5>
            <p class="price">$19</p>
        </div>

        <div class="menu-item">
            <img src="${pageContext.request.contextPath}/images/Menu-items.png" alt="Carbonara spaghetti" class="menu-img">
            <h5>Carbonara spaghetti</h5>
            <p class="price">$23</p>
        </div>
    </div>

    <div class="menu-navigation">
        <button class="nav-button">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </button>
        <button class="nav-button">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m9 18 6-6-6-6"/></svg>
        </button>
    </div>
</section>