<style>
    header {
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        position: sticky;
        top: 0;
        left: 0;
        margin: 0;
        z-index: 1000;
        background: transparent;
    }

    .navbar{
        width: 100dvw;
        padding: 1rem 2rem;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .nav-logo {
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .nav-logo .nav-title {
        color: var(--text);
        text-decoration: none;
        font-weight: 500;
        font-size: 1.5rem;
        transition: color 0.3s ease;
    }

    .nav-logo .nav-title:hover {
        color: var(--primary);
    }

    .nav-links{
        display: flex;
        justify-content: space-between;
        align-items: center;
        gap: 2rem;
    }

    .nav-link {
        color: var(--text);
        text-decoration: none !important;
        font-size: 1rem;
        font-weight: 300 !important;
        position: relative;
        padding: 0.5rem 0;
        transition: opacity 0.3s ease;
    }

    .nav-link::after {
        content: '';
        width: 0;
        height: 1px;
        position: absolute;
        bottom: 8px;
        left: 0;
        background-color: var(--primary);
        transition: width 0.3s ease;
    }

    .nav-link:hover::after {
        width: 100%;
    }

    .nav-links:hover .nav-link:not(:hover) {
        opacity: 0.5;
    }


</style>

<header>
    <nav class="navbar">

        <div class="nav-logo">
            <a href="${pageContext.request.contextPath}/" class="nav-title">Food App</a>
        </div>


        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/" class="nav-link">Home</a>
            <a href="${pageContext.request.contextPath}/menu" class="nav-link">Menu</a>

            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <a href="${pageContext.request.contextPath}/user/profile" class="nav-link">
                        <span>${sessionScope.user.userName}</span>
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/user/login" class="nav-link">
                        Login
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </nav>
</header>
