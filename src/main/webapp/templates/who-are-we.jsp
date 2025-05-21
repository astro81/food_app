<style>
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
        max-width: 100%;
        line-height: 1.6;
    }

    .image-gallery {
        display: flex;
        gap: 1rem;
        width: fit-content;
        transition: transform 0.5s ease-in-out;
        animation-play-state: running;
    }

    /* Auto-scroll animation with pause on hover */
    @keyframes autoScroll {
        0% {
            transform: translateX(0);
        }
        100% {
            transform: translateX(calc(-400px * 4 - 1rem * 4)); /* Move by 4 images + gaps */
        }
    }

    .auto-scroll {
        animation: autoScroll 20s linear infinite;
    }

    .gallery-container:hover .auto-scroll {
        animation-play-state: paused;
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
    }

</style>
<!-- Who are we section -->
<section class="who-are-we">
    <div class="section-content">
        <div class="section-left">
            <h1>Who are we?</h1>
        </div>
        <div class="section-right">
            <p class="description">
                OrderNow is a place that serves authentic dishes made with locally sourced ingredients.
                Our chefs bring decades of experience and passion to create memorable dining experiences.
                We believe good food brings people together.
            </p>
        </div>
    </div>
    <div class="image-gallery auto-scroll">
        <div class="gallery-image">
            <img src="${pageContext.request.contextPath}/images/Photo-items.png" alt="Restaurant interior" class="gallery-img">
        </div>
        <div class="gallery-image">
            <img src="${pageContext.request.contextPath}/images/Photo-items%20(1).png" alt="Pizza in oven" class="gallery-img">
        </div>
        <div class="gallery-image">
            <img src="${pageContext.request.contextPath}/images/Photo-items%20(2).png" alt="Customers dining" class="gallery-img">
        </div>
        <div class="gallery-image">
            <img src="${pageContext.request.contextPath}/images/Photo-items%20(3).png" alt="Chef preparing food" class="gallery-img">
        </div>
        <div class="gallery-image">
            <img src="${pageContext.request.contextPath}/images/Photo-items.png" alt="Restaurant interior" class="gallery-img">
        </div>
        <div class="gallery-image">
            <img src="${pageContext.request.contextPath}/images/Photo-items%20(1).png" alt="Pizza in oven" class="gallery-img">
        </div>
        <div class="gallery-image">
            <img src="${pageContext.request.contextPath}/images/Photo-items%20(2).png" alt="Customers dining" class="gallery-img">
        </div>
        <div class="gallery-image">
            <img src="${pageContext.request.contextPath}/images/Photo-items%20(3).png" alt="Chef preparing food" class="gallery-img">
        </div>
    </div>
</section>