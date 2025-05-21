<style>
    .hero-container section {
        height: 100dvh;
        width: 100dvw;
        position: absolute;
        top: 0;
        left: 0;
    }

    .hero-wrapper {
        height: 95dvh;
        width: 100%;
        position: relative;
    }

    .hero-container img {
        height: 100%;
        width: 100%;
        object-fit: cover;
    }

    .overlay {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.6);
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        text-align: center;
        color: white;
        padding: 2rem;
    }

    .brand-name {
        font-size: 5rem;
        font-weight: bold;
        margin-bottom: 1rem;
        text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
    }

    .description {
        font-size: 1.5rem;
        max-width: 600px;
        margin-bottom: 2rem;
    }

    .menu-button {
        padding: 1rem 2rem;
        font-size: 1.2rem;
        background-color: transparent;
        color: white;
        border: none;
        border-radius: 30px;
        cursor: pointer;
        font-weight: bold;
        transition: all 0.3s ease;
    }

    .menu-button:hover {
        background-color: #ff8c3f;
        transform: scale(1.05);
    }
</style>

<section class="hero-container">
    <div class="hero-wrapper">
        <img src="${pageContext.request.contextPath}/images/hero.avif" alt="hero">
        <div class="overlay">
            <h1 class="brand-name">OrderNow</h1>
            <p class="description">Delicious food delivered to your doorstep. Explore our wide variety of cuisines and enjoy a seamless ordering experience.</p>
            <a href="${pageContext.request.contextPath}/menu"><button class="menu-button">Explore Our Menu</button></a>
        </div>
    </div>
</section>