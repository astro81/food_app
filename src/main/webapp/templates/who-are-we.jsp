<!-- Who are we section -->
<section class="who-are-we">
  <div class="section-content">
    <div class="section-left">
      <h1>Who are we?</h1>
      <div class="navigation-buttons">
        <button class="nav-button">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </button>
        <button class="nav-button">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m9 18 6-6-6-6"/></svg>
        </button>
      </div>
    </div>
    <div class="section-right">
      <p class="description">
        Cibo gustoso is a family-owned Italian restaurant that serves authentic dishes made with locally sourced
        ingredients. Our chefs bring decades of experience and passion to create memorable dining experiences. We
        believe good food brings people together.
      </p>
    </div>
  </div>
  <div class="image-gallery">
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
      max-width: 80%;
      line-height: 1.6;
    }

    .navigation-buttons {
      display: flex;
      gap: 0.5rem;
      margin-top: 1rem;
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
</section>