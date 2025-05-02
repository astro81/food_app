<style>
    .hero-container section {
        height: 100dvh;
        width: 100dvw;
        position: absolute;
        top: 0;
        left: 0;
    }

    .hero-wrapper div {
        height: 100%;
        width: 100%;
    }

    .hero-container img {
        height: 100%;
        width: 100%;
        object-fit: cover;
    }
</style>

<section class="hero-container">
    <div class="hero-wrapper">
        <img src="${pageContext.request.contextPath}/images/Full-background.png" alt="hero">
    </div>
</section>